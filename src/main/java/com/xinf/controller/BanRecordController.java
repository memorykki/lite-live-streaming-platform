package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.BanRecord;
import com.xinf.service.BanRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BanRecord)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:16
 */

@RestController
@RequestMapping("banRecord")
public class BanRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private BanRecordService banRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page      分页对象
     * @param banRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<BanRecord> page, BanRecord banRecord) {
        return success(this.banRecordService.page(page, new QueryWrapper<>(banRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.banRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param banRecord 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody BanRecord banRecord) {
        return success(this.banRecordService.save(banRecord));
    }

    /**
     * 修改数据
     *
     * @param banRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody BanRecord banRecord) {
        return success(this.banRecordService.updateById(banRecord));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.banRecordService.removeByIds(idList));
    }
}
