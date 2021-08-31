package cn.memorykk.ts.litelivestreamingplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.memorykk.ts.litelivestreamingplatform.entity.BanPermission;
import cn.memorykk.ts.litelivestreamingplatform.service.BanPermissionService;
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
public class BanPermissionController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private BanPermissionService banPermissionService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param banPermission 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<BanPermission> page, BanPermission banPermission) {
        return success(this.banPermissionService.page(page, new QueryWrapper<>(banPermission)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.banPermissionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param banPermission 实体对象
     * @return 新增结果
     */
    @PostMapping
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
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.banPermissionService.removeByIds(idList));
    }
}
