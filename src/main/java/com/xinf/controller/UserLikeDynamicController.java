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
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
        Page<UserLikeDynamic> userLikes = userLikeDynamicService.page(page, new QueryWrapper<UserLikeDynamic>().eq("user_id", id));
        return success(BeanUtil.transPage(userLikes,
                (e) -> new UserLikeDynamicInfo(userDynamicService.getById(e.getDynamicId()), e.getCreateTime()),
                UserLikeDynamicInfo[]::new));
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

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userLikeDynamicService.removeByIds(idList));
    }
}
