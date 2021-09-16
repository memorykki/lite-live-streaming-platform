package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.Playback;
import com.xinf.service.PlaybackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Playback)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@RestController
@RequestMapping("playback")
@Api(value = "PlaybackController", tags = { "回放接口" })
public class PlaybackController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private PlaybackService playbackService;

    /**
     * 分页查询所有数据
     *
     * @param playback 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("选择所有回放")
    @ApiImplicitParams({@ApiImplicitParam(name ="playback", value = "回放"),
            @ApiImplicitParam(name ="pageCurrent", value = "当前页面"),
            @ApiImplicitParam(name ="pageSize", value = "页面尺寸")
    })
    public R selectAll(Playback playback,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.playbackService.page(page, new QueryWrapper<>(playback)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("选择单个回放")
    @ApiImplicitParams({@ApiImplicitParam(name ="id ", value = "id")
    })
    public R selectOne(@PathVariable Serializable id) {
        return success(this.playbackService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param playback 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("插入一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="playback ", value = "回放")
    })
    public R insert(@RequestBody Playback playback) {
        return success(this.playbackService.save(playback));
    }

    /**
     * 修改数据
     *
     * @param playback 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("更新一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="playback ", value = "回放")
    })
    public R update(@RequestBody Playback playback) {
        return success(this.playbackService.updateById(playback));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除")
    @ApiImplicitParams({@ApiImplicitParam(name ="idlist", value = "id列表")
    })
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.playbackService.removeByIds(idList));
    }
}
