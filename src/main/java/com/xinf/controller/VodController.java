package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.Vod;
import com.xinf.service.VodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Vod)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:20
 */
@RestController
@RequestMapping("vod")
public class VodController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private VodService vodService;

    /**
     * 分页查询所有数据
     *
     * @param vod  查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Vod vod,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.vodService.page(page, new QueryWrapper<>(vod)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.vodService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param vod 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Vod vod) {
        return success(this.vodService.save(vod));
    }

    /**
     * 修改数据
     *
     * @param vod 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Vod vod) {
        return success(this.vodService.updateById(vod));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.vodService.removeByIds(idList));
    }
}
