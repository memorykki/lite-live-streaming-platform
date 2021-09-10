package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.dto.UserLikeDynamicInfo;
import com.xinf.entity.UserLikeDynamic;
import com.xinf.service.UserDynamicService;
import com.xinf.service.UserLikeDynamicService;
import com.xinf.util.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 用户点赞记录(UserLikeDynamic)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@RestController
@RequestMapping("userLikeDynamic")
@Api(value = "用户动态点赞controller", tags = { "用户动态点赞访问接口" })
public class UserLikeDynamicController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserLikeDynamicService userLikeDynamicService;

    @Resource
    private UserDynamicService userDynamicService;

    /**
     * 分页查询所有数据

     * @param userLikeDynamic 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(UserLikeDynamic userLikeDynamic,
            @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.userLikeDynamicService.page(page, new QueryWrapper<>(userLikeDynamic)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 用户id
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("获取用户点赞的所有动态")
    public R selectOne(@PathVariable Serializable id,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        Page<UserLikeDynamic> userLikes = userLikeDynamicService.page(page, new QueryWrapper<UserLikeDynamic>().eq("user_id", id).orderByDesc("create_time"));
        return success(BeanUtil.transPage(userLikes,
                (e) -> new UserLikeDynamicInfo(userDynamicService.getById(e.getDynamicId()), e.getCreateTime(), true),
                UserLikeDynamicInfo[]::new));
    }

    /**
     * 点赞动态
     * @param userId 点赞者id
     * @param dynamicId 动态id
     * @return
     */
    @PutMapping("/like")
    @ApiOperation("点赞某个动态")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "点赞者的用户标识id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dynamicId", value = "动态id", dataType = "long", paramType = "query")})
    public R like(long userId, long dynamicId) {
        userLikeDynamicService.save(new UserLikeDynamic(userId, dynamicId));
        return success(null);
    }

    /**
     *  取消点赞
     */
    @DeleteMapping("/cancel")
    @ApiOperation("取消点赞某个动态")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "点赞者的用户标识id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "dynamicId", value = "动态id", dataType = "long", paramType = "query")})
    public R cancel(long userId, long dynamicId) {
        return success(userLikeDynamicService.cancel(userId, dynamicId));
    }

    /**
     * 新增数据
     *
     * @param userLikeDynamic 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UserLikeDynamic userLikeDynamic) {
        return success(this.userLikeDynamicService.save(userLikeDynamic));
    }

    /**
     * 修改数据
     *
     * @param userLikeDynamic 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UserLikeDynamic userLikeDynamic) {
        return success(this.userLikeDynamicService.updateById(userLikeDynamic));
    }
}
