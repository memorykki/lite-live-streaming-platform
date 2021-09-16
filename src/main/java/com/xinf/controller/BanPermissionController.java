package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.dto.BanInfo;
import com.xinf.entity.BanPermission;
import com.xinf.service.BanPermissionService;
import com.xinf.service.BanRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (BanPermission)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:14
 */
@RestController
@RequestMapping("banPermission")
@Api(value = "用户ban controller", tags = { "用户ban访问接口" })
public class BanPermissionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private BanPermissionService banPermissionService;

    @Resource
    private BanRecordService banRecordService;

    /**
     * 分页查询所有数据
     * @param banPermission 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation(value = "选择所有")
    @ApiImplicitParams({@ApiImplicitParam(name = "BanPermission", value = "封禁权限"),
            @ApiImplicitParam(name ="pageCurrent", value = "当前界面"),
            @ApiImplicitParam(name ="pageSize", value = "界面尺寸"),
    })
    public R selectAll(BanPermission banPermission,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.banPermissionService.page(page, new QueryWrapper<>(banPermission)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 用户id
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("获取用户ban信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户名称"),
            @ApiImplicitParam(name ="BanPermission", value = "封禁权限")})
    public R selectOne(@PathVariable Serializable id) {
        BanInfo banInfo = new BanInfo();
        BanPermission banPermission = banPermissionService.getById(id);
        banInfo.setBanPermission(banPermission);
        banInfo.setBanRecord(banRecordService.getById(banPermission.getBanId()));
        return success(banInfo);
    }

    /**
     * 新增数据
     *
     * @param banPermission 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("插入数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="BanPermission", value = "封禁权限")})
    public R insert(@RequestBody BanPermission banPermission) {
        return success(this.banPermissionService.save(banPermission));
    }

    /**
     * 修改数据
     *
     * @param banPermission 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="banPermission", value = "封禁权限")})
    public R update(@RequestBody BanPermission banPermission) {
        return success(this.banPermissionService.updateById(banPermission));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="banPermission", value = "封禁权限")})
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.banPermissionService.removeByIds(idList));
    }
}
