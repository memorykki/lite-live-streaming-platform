package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.UserWatchHistory;
import com.xinf.service.UserWatchHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (UserWatchHistory)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:20
 */
@RestController
@RequestMapping("userWatchHistory")
public class UserWatchHistoryController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserWatchHistoryService userWatchHistoryService;

    /**
     * 分页查询所有数据
     *
     * @param page             分页对象
     * @param userWatchHistory 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<UserWatchHistory> page, UserWatchHistory userWatchHistory) {
        return success(this.userWatchHistoryService.page(page, new QueryWrapper<>(userWatchHistory)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userWatchHistoryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userWatchHistory 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UserWatchHistory userWatchHistory) {
        return success(this.userWatchHistoryService.save(userWatchHistory));
    }

    /**
     * 修改数据
     *
     * @param userWatchHistory 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UserWatchHistory userWatchHistory) {
        return success(this.userWatchHistoryService.updateById(userWatchHistory));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userWatchHistoryService.removeByIds(idList));
    }
}
