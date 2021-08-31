package cn.memorykk.ts.litelivestreamingplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.memorykk.ts.litelivestreamingplatform.entity.GiftRecord;
import cn.memorykk.ts.litelivestreamingplatform.service.GiftRecordService;
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
public class GiftRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private GiftRecordService giftRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param giftRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<GiftRecord> page, GiftRecord giftRecord) {
        return success(this.giftRecordService.page(page, new QueryWrapper<>(giftRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
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
    public R insert(@RequestBody GiftRecord giftRecord) {
        return success(this.giftRecordService.save(giftRecord));
    }

    /**
     * 修改数据
     *
     * @param giftRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
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
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.giftRecordService.removeByIds(idList));
    }
}
