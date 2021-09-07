package com.xinf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.xinf.constant.SecurityProperties;
import com.xinf.dao.UserDao;
import com.xinf.dto.UserInfo;
import com.xinf.entity.User;
import com.xinf.service.RoleService;
import com.xinf.service.UserService;
import com.xinf.util.EmailUtil;
import com.xinf.util.RedisUtil;
import com.xinf.util.SmsUtil;
import com.xinf.util.error.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserInfo getUserInfo(long userId) {
        UserInfo userInfo = new UserInfo();
        User u = getById(userId);
        userInfo.setUser(u);
        userInfo.setRole(roleService.getById(u.getRoleId()));
        return userInfo;
    }

    @Override
    public boolean registerUser(User user, int code) {
        if (code == -1) {
            return false;
        }

        // 检测code
        String key;
        if (!Strings.isNullOrEmpty(user.getUserEmail())) {
            key = user.getUserEmail();
        } else if (!Strings.isNullOrEmpty(user.getUserPhone())) {
            key = user.getUserPhone();
        } else {
            return false;
        }

        log.info("register key : {}", key);
        String v = redisUtil.get(key);
        if (Strings.isNullOrEmpty(v) || Integer.valueOf(v) != code) {
            return false;
        }
        redisUtil.delete(key);
        /*
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户名
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         * */
        String newPs = new SimpleHash("MD5", user.getUserPasswd(), securityProperties.salt, 2).toHex();
        user.setUserPasswd(newPs);
        return save(user);
    }

    @Override
    public UserInfo login(String auth, String password) {
        // 获取Subject实例对象，用户实例
        Subject currentUser = SecurityUtils.getSubject();

        // 将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(auth, password);

        UserInfo userInfo;

        // 4、认证
        try {
            // 传到 MyShiroRealm 类中的方法进行认证
            currentUser.login(token);
            // 构建缓存用户信息返回给前端
            userInfo = (UserInfo) currentUser.getPrincipals().getPrimaryPrincipal();
            userInfo.getUser().setUserPasswd("");
            userInfo.setToken(currentUser.getSession().getId().toString());
            log.info("用户登录成功，用户名: {}, token: {}", userInfo.getUser().getUserName(), userInfo.getToken());
        } catch (UnknownAccountException e) {
            log.error("账户不存在异常：", e);
            throw new LoginException("账号不存在!", e);
        } catch (IncorrectCredentialsException e) {
            log.error("凭据错误（密码错误）异常：", e);
            throw new LoginException("密码不正确!", e);
        } catch (AuthenticationException e) {
            log.error("身份验证异常:", e);
            throw new LoginException("用户验证失败!", e);
        }
        return userInfo;
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.info("用户登出成功，用户名: {}", ((UserInfo)subject.getPreviousPrincipals().getPrimaryPrincipal()).getUser().getUserName());
    }

    @Override
    public int sendEmail(String emailAddress) {
        return emailUtil.send(emailAddress);
    }

    @Override
    public int sendSms(String phone) {
        return smsUtil.send(phone);
    }
}
