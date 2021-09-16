package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.GiftRecord;
import com.xinf.service.GiftRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 礼物流水(GiftRecord)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:16
 */
@RestController
@RequestMapping("giftRecord")
@Api(value = "GiftRecordController", tags = { "礼物记录接口" })
public class GiftRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private GiftRecordService giftRecordService;

    /**
     * 分页查询所有数据
     * @param giftRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("选择所有礼物记录")
    @ApiImplicitParams({@ApiImplicitParam(name ="giftRecord", value = "礼物记录"),
            @ApiImplicitParam(name ="pageCurrent", value = "当前页面"),
            @ApiImplicitParam(name ="pageSize", value = "页面尺寸")
    })
    public R selectAll(GiftRecord giftRecord,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.giftRecordService.page(page, new QueryWrapper<>(giftRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("选择单个礼物记录")
    @ApiImplicitParams({@ApiImplicitParam(name ="id", value = "id")
    })
    public R selectOne(@PathVariable Serializable id) {
        return success(this.giftRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param giftRecord 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("插入礼物记录")
    @ApiImplicitParams({@ApiImplicitParam(name ="giftRecord", value = "礼物记录")
    })
    public R insert(@RequestBody GiftRecord giftRecord, @RequestParam String reason) {
        return success(this.giftRecordService.save(giftRecord, reason));
    }

    /**
     * 修改数据
     *
     * @param giftRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("更新礼物记录数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="giftRecord", value = "礼物记录")
    })
    public R update(@RequestBody GiftRecord giftRecord) {
        return success(this.giftRecordService.updateById(giftRecord));
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
        return success(this.giftRecordService.removeByIds(idList));
    }
}
