package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.constant.FilePathConstant;
import com.xinf.entity.BanPermission;
import com.xinf.entity.BanRecord;
import com.xinf.service.BanPermissionService;
import com.xinf.service.BanRecordService;
import com.xinf.util.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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
@Slf4j
@Api(value = "举报记录controller", tags = { "举报记录访问接口" })
public class BanRecordController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private BanRecordService banRecordService;

    @Resource
    private FilePathConstant filePathConstant;

    @Resource
    private BanPermissionService banPermissionService;

    /**
     * 分页查询所有数据
     * @param banRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(BanRecord banRecord,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
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
    @ApiOperation(value = "添加一条举报信息", notes = "这条信息应该是待审核的")
    public R insert(@RequestBody BanRecord banRecord,
                    @RequestParam(required = false) MultipartFile file) throws IOException {
        if (banRecord.getType() == 0) {
            if (file == null || file.isEmpty()) {
                return failed("直播图片不存在");
            }
            String uuid = UUIDUtil.getUUID();
            String fileUrl = filePathConstant.banFileUrl + uuid;
            // 创建文件实例
            File filePath = new File(filePathConstant.dynamicFilePath, uuid);
            // 如果文件目录不存在，创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
                log.info("创建目录 : {}", filePath.getParentFile().getName());
            }
            // 写入文件
            file.transferTo(filePath);
            banRecord.setEvidence(fileUrl);
        }
        return success(this.banRecordService.save(banRecord));
    }

    /**
     * 修改数据
     *
     * @param banRecord 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改ban记录信息，主要是status修改")
    public R update(@RequestBody BanRecord banRecord) {

        if (banRecord.getStatus() == 2) {
            // 封禁
            BanPermission banPermission = new BanPermission();
            banPermission.setUserId(banRecord.getUserId());
            if (banRecord.getBanId() != null) {
                banPermission.setBanId(banRecord.getBanId());
            }
            if (banRecord.getType() == 1) {
                banPermission.setLivingPermission(1);
            } else {
                banPermission.setChatPermission(1);
                banPermission.setSendGiftPermission(1);
            }
            banPermissionService.saveOrUpdate(banPermission);
        } else if (banRecord.getStatus() == 4) {
            // 解封
            banPermissionService.removeById(banRecord.getUserId());
        }
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
