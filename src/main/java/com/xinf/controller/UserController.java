package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.User;
import com.xinf.handler.WebSocketServer;
import com.xinf.service.UserService;
import com.xinf.util.EmailUtil;
import com.xinf.util.RedisUtil;
import com.xinf.util.SmsUtil;
import com.xinf.util.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@RestController
@RequestMapping("user")
@Api(value = "用户Controller", tags = { "用户访问接口" })
@Slf4j
public class UserController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Resource
    private SmsUtil smsUtil;

    @Resource
    private EmailUtil emailUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 分页查询所有数据
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation(value = "查询所有用户")
    public R selectAll(User user, HttpServletRequest request,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        log.info("selectUser request info, Remotehost : {}", request.getRemoteHost());
        return success(this.userService.page(page, new QueryWrapper<>(user)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "查询单个用户")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "添加一个用户")
    public R insert(@RequestBody User user) {
        return success(this.userService.save(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation(value = "修改用户数据")
    public R update(@RequestBody User user) {
        return success(this.userService.updateById(user));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation(value = "删除用户")
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userService.removeByIds(idList));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户注册信息"),
            @ApiImplicitParam(name = "code", value = "验证码")})
    public R register(@RequestBody User user, @RequestParam(defaultValue = "-1") int code,
                      @RequestParam String auth) {
        if (userService.registerUser(user, code, auth)) {
            return success(null);
        } else {
            return failed("注册错误");
        }
    }

    /**
     * 用户登录
     */
    @GetMapping("/login")
    @ApiOperation(value = "用户登录")
    public R login(String auth, String passwd) {

        if (StringUtils.isBlank(auth)) {
            return failed("用户名为空！");
        }

        if (StringUtils.isBlank(passwd)) {
            return failed("密码为空！");
        }

        log.debug("auth : {}, passwd : {}", auth, passwd);

        Map<String, Object> loginUser = userService.login(auth, passwd);

        // 登录成功返回用户信息
        return success(loginUser);
    }



    /**
     * description: 登出
     */
    @GetMapping("/logout")
    @ApiOperation(value = "用户登出")
    public R logOut() {
        userService.logout();
        return success("登出成功！");
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     */
    @RequestMapping("/un_auth")
    public ResponseEntity<?> unAuth() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 未授权，无权限，此处返回未授权状态信息由前端控制跳转页面
     */
    @RequestMapping("/unauthorized")
    public ResponseEntity<?> unauthorized() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping("/sendVerifiableCode")
    @ApiOperation(value = "发送验证码")
    public R<Object> sendVerifiableCode(String distAddress){
        log.info("注册用户地址：{}", distAddress);
        int code = 0;
        if (Strings.isConstitutedByDigit(distAddress)) {
            if (userService.count(new QueryWrapper<User>().eq("user_phone", distAddress)) > 0) {
                return failed("电话已注册");
            }
            code = smsUtil.send(distAddress);
        } else if (Strings.isEmail(distAddress)) {
            if (userService.count(new QueryWrapper<User>().eq("user_email", distAddress)) > 0) {
                return failed("邮箱已注册");
            }
            code = emailUtil.send(distAddress);
        }

        if (code > 0) {
            redisUtil.set(distAddress, String.valueOf(code));
            redisUtil.expire(distAddress, 600000, TimeUnit.MILLISECONDS);
            return success(null);
        }
        return failed("注册信息错误");
    }

    @ApiOperation("获取在线总人数")
    @GetMapping("/online")
    public R getOnlineCount() {
        return success(webSocketServer.getOnlineCount());
    }
}
