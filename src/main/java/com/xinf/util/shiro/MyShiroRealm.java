package com.xinf.util.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinf.constant.Permission;
import com.xinf.constant.SecurityProperties;
import com.xinf.dto.UserInfo;
import com.xinf.entity.BanPermission;
import com.xinf.entity.Role;
import com.xinf.entity.User;
import com.xinf.service.BanPermissionService;
import com.xinf.service.RoleService;
import com.xinf.service.UserService;
import com.xinf.util.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

/**
 * @author xinf
 * @since 2021/9/1 15:58
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    SecurityProperties securityProperties;

    UserService userService;

    RoleService roleService;

    BanPermissionService banPermissionService;

    public MyShiroRealm(SecurityProperties securityProperties, UserService userService, RoleService roleService, BanPermissionService banPermissionService) {
        this.securityProperties = securityProperties;
        this.userService = userService;
        this.roleService = roleService;
        this.banPermissionService = banPermissionService;
    }

    /**
     * @return 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //也就是SimpleAuthenticationInfo构造的时候第一个参数传递需要User对象
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        if (userInfo.getRole().getLivingPermission() == 1) {
            authorizationInfo.addStringPermission(Permission.livingPermission);
        }
        if (userInfo.getRole().getChatPermission() == 1) {
            authorizationInfo.addStringPermission(Permission.chatPermission);
        }
        if (userInfo.getRole().getSendGiftPermission() == 1) {
            authorizationInfo.addStringPermission(Permission.sendGiftPermission);
        }
        return authorizationInfo;
    }

    /**
     *   @return 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String auth = (String) token.getPrincipal();

        //通过username从数据库中查找 User对象.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (Strings.isConstitutedByDigit(auth)) {
            wrapper.eq("user_phone", auth);
        } else if (Strings.isEmail(auth)) {
            wrapper.eq("user_email", auth);
        } else {
            wrapper.eq("user_name", auth);
        }
        User user = userService.getOne(wrapper);
        if (Objects.isNull(user)) {
            return null;
        }
        Role role = roleService.getById(user.getRoleId());
        BanPermission banPermission = banPermissionService.getById(user.getUserId());
        if (banPermission != null) {
            if (banPermission.getSendGiftPermission() == 1) {
                role.setSendGiftPermission(0);
            }
            if (banPermission.getChatPermission() == 1) {
                role.setChatPermission(0);
            }
            if (banPermission.getLivingPermission() == 1) {
                role.setLivingPermission(0);
            }
        }
        log.debug("user login info, user : {}, role : {}, salt: {}", user, role, securityProperties.salt);
        ByteSource salt = ByteSource.Util.bytes(securityProperties.salt);
        return new SimpleAuthenticationInfo(new UserInfo(user, role), user.getUserPasswd(), salt, getName());
    }
}
