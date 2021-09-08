package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.dto.UserFocusInfo;
import com.xinf.entity.UserFocus;
import com.xinf.service.UserFocusService;
import com.xinf.service.UserService;
import com.xinf.util.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserFocus)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:18
 */
@RestController
@RequestMapping("userFocus")
@Api(value = "用户关注controller", tags = { "用户关注访问接口" })
public class UserFocusController extends ApiController {

    /**
     * 服务对象
     */
    @Resource
    private UserFocusService userFocusService;

    @Resource
    private UserService userService;
    /**
     *  获取用户关注列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户关注列表")
    @ApiImplicitParam(name = "userId", value = "本人id")
    public R getUserFocusList(long userId,
                                @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        Page<UserFocus> list = userFocusService.page(page, new QueryWrapper<UserFocus>().eq("focus_user_id", userId));
        return success(BeanUtil.transPage(list,
                (e) -> new UserFocusInfo(userService.getUserInfo(e.getFocusedUserId()), e.getUpdateTime()),
                UserFocusInfo[]::new));
    }


    /**
     * 分页查询所有数据
     *
     * @param userFocus 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(UserFocus userFocus,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.userFocusService.page(page, new QueryWrapper<>(userFocus)));
    }

    @PostMapping
    @ApiOperation("点击关注")
    public R insert(@RequestBody UserFocus userFocus) {
        this.userFocusService.add(userFocus);
        return success(null);
    }

    @DeleteMapping
    @ApiOperation("取消关注")
    public R delete(@RequestBody UserFocus userFocus) {
        this.userFocusService.remove(userFocus);
        return success(null);
    }

    /**
     * 修改数据
     *
     * @param userFocus 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UserFocus userFocus) {
        return success(this.userFocusService.updateById(userFocus));
    }

}
