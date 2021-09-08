package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.dto.UserFocusInfo;
import com.xinf.entity.UserFocus;
import com.xinf.service.UserFocusService;
import com.xinf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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
        UserFocusInfo[] res = list.getRecords().parallelStream().map(e ->
                new UserFocusInfo(userService.getUserInfo(e.getFocusedUserId()), e.getUpdateTime())).toArray(UserFocusInfo[]::new);
        Page<UserFocusInfo> ans = new Page<>();
        ans.setTotal(page.getTotal());
        ans.setCurrent(page.getCurrent());
        ans.setSize(page.getSize());
        ans.setRecords(Arrays.asList(res));
        return success(ans);
    }

    @PostMapping("/focus")


    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param userFocus 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UserFocus> page, UserFocus userFocus) {
        return success(this.userFocusService.page(page, new QueryWrapper<>(userFocus)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userFocusService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userFocus 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UserFocus userFocus) {
        return success(this.userFocusService.save(userFocus));
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

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userFocusService.removeByIds(idList));
    }
}
