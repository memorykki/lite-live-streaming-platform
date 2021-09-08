package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.UserWatchHistory;
import com.xinf.service.UserWatchHistoryService;
import io.swagger.annotations.ApiOperation;
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


    @GetMapping("{id}")
    @ApiOperation("获取本人观看历史")
    public R select(@PathVariable Serializable id,
            @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.userWatchHistoryService.page(page, new QueryWrapper<UserWatchHistory>().eq("user_id", id)));
    }

    /**
     * 分页查询所有数据
     *
     * @param userWatchHistory 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("获取观看历史数据")
    public R selectAll(UserWatchHistory userWatchHistory,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.userWatchHistoryService.page(page, new QueryWrapper<>(userWatchHistory)));
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
