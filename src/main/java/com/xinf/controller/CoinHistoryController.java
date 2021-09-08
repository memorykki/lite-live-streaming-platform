package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.CoinHistory;
import com.xinf.service.CoinHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (CoinHistory)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:15
 */
@RestController
@RequestMapping("coinHistory")
@Api(value = "投币历史controller", tags = { "投币历史访问接口" })
public class CoinHistoryController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CoinHistoryService coinHistoryService;



    /**
     * 分页查询所有数据
     * @param coinHistory 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("获取投币历史数据")
    public R selectAll(CoinHistory coinHistory,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.coinHistoryService.page(page, new QueryWrapper<>(coinHistory)));
    }

    /**
     * 通过用户id获取数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.coinHistoryService.page(page, new QueryWrapper<CoinHistory>().eq("user_id", id)));
    }

    /**
     * 新增数据
     *
     * @param coinHistory 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "新增投币记录", notes = "其关系到总货币数量，用户等级提升等")
    public R insert(@RequestBody CoinHistory coinHistory) {
        return success(this.coinHistoryService.save(coinHistory));
    }

    /**
     * 修改数据
     *
     * @param coinHistory 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody CoinHistory coinHistory) {
        return success(this.coinHistoryService.updateById(coinHistory));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.coinHistoryService.removeByIds(idList));
    }
}
