package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.Gift;
import com.xinf.service.GiftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Gift)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:17
 */
@Slf4j
@RestController
@RequestMapping("gift")
@Api(value = "GiftController", tags = { "礼物接口" })
public class GiftController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private GiftService giftService;

    /**
     * 分页查询所有数据
     *
     * @param gift 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("选择所有礼物")
    @ApiImplicitParams({@ApiImplicitParam(name ="gift", value = "礼物"),
            @ApiImplicitParam(name ="pageCurrent", value = "当前页面"),
            @ApiImplicitParam(name ="pageSize", value = "页面尺寸")
    })
    public R selectAll(Gift gift,
             @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.giftService.page(page, new QueryWrapper<>(gift)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("选择单个礼物")
    @ApiImplicitParams({@ApiImplicitParam(name ="id", value = "id")
    })
    public R selectOne(@PathVariable Serializable id) {
        return success(this.giftService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param gift 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("插入礼物")
    @ApiImplicitParams({@ApiImplicitParam(name ="gift", value = "礼物")
    })
    public R insert(@RequestBody Gift gift) {
        return success(this.giftService.save(gift));
    }

    /**
     * 修改数据
     *
     * @param gift 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("更新数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="gift", value = "礼物")
    })
    public R update(@RequestBody Gift gift) {
        return success(this.giftService.updateById(gift));
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
        return success(this.giftService.removeByIds(idList));
    }
}
