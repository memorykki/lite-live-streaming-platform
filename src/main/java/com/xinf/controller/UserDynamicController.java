package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.UserDynamic;
import com.xinf.service.UserDynamicService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserDynamic)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@RestController
@RequestMapping("userDynamic")
public class UserDynamicController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserDynamicService userDynamicService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param userDynamic 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UserDynamic> page, UserDynamic userDynamic) {
        return success(this.userDynamicService.page(page, new QueryWrapper<>(userDynamic)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userDynamicService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userDynamic 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UserDynamic userDynamic) {
        return success(this.userDynamicService.save(userDynamic));
    }

    /**
     * 修改数据
     *
     * @param userDynamic 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UserDynamic userDynamic) {
        return success(this.userDynamicService.updateById(userDynamic));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userDynamicService.removeByIds(idList));
    }
}
