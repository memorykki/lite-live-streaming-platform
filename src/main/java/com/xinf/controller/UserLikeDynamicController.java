package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.UserLikeDynamic;
import com.xinf.service.UserLikeDynamicService;
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
public class UserLikeDynamicController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserLikeDynamicService userLikeDynamicService;

    /**
     * 分页查询所有数据
     *
     * @param page            分页对象
     * @param userLikeDynamic 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UserLikeDynamic> page, UserLikeDynamic userLikeDynamic) {
        return success(this.userLikeDynamicService.page(page, new QueryWrapper<>(userLikeDynamic)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userLikeDynamicService.getById(id));
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
