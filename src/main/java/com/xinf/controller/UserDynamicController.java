package com.xinf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.constant.FilePathConstant;
import com.xinf.entity.UserDynamic;
import com.xinf.entity.UserLikeDynamic;
import com.xinf.service.UserDynamicService;
import com.xinf.service.UserLikeDynamicService;
import com.xinf.util.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * (UserDynamic)表控制层
 *
 * @author makejava
 * @since 2021-08-31 19:25:19
 */
@Slf4j
@RestController
@RequestMapping("userDynamic")
@Api(value = "用户动态controller", tags = { "用户动态访问接口" })
public class UserDynamicController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UserDynamicService userDynamicService;

    @Resource
    private UserLikeDynamicService userLikeDynamicService;

    @Resource
    private FilePathConstant filePathConstant;

    /**
     *  获取用户动态
     */
    @GetMapping("/list")
    @ApiOperation("获取用户动态")
    @ApiImplicitParam(name = "userId", value = "需要获取动态所属用户的id")
    public R getUserDynamicList(long userId,
         @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        Page<UserDynamic> list = userDynamicService.page(page, new QueryWrapper<UserDynamic>().eq("user_id", userId));
        return success(list);
    }

    /**
     * 点赞动态
     * @param userId 点赞者id
     * @param dynamicId 动态id
     * @return
     */
    @PutMapping("/like")
    @Transactional
    @ApiOperation("点赞某个动态")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "点赞者的用户标识id"),
            @ApiImplicitParam(name = "dynamicId", value = "动态id")})
    public R like(long userId, long dynamicId) {
        userDynamicService.update(new UpdateWrapper<UserDynamic>().eq("dynamicId", dynamicId)
                .setSql("dynamic_like = dynamic_like + 1"));
        userLikeDynamicService.save(new UserLikeDynamic(userId, dynamicId));
        return success(null);
    }

    /**
     * 发布动态
     */
    @PostMapping("/publish")
    @ApiOperation("发布动态")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "发布用户id"),
            @ApiImplicitParam(name = "file", value = "上传视频或图片", required = false),
            @ApiImplicitParam(name = "message", value = "动态信息，与file冲突，只选一个", required = false)})
    public R publish(int type, @RequestParam long userId,
          @RequestParam(required = false) MultipartFile file, @RequestParam(required = false) String message) throws IOException {
        UserDynamic userDynamic = new UserDynamic();
        userDynamic.setUserId(userId);
        userDynamic.setDynamicType(type);
        if (file != null && !file.isEmpty()) {
            String uuid = UUIDUtil.getUUID();
            String fileUrl = filePathConstant.dynamicFileUrl + uuid;
            // 创建文件实例
            File filePath = new File(filePathConstant.dynamicFilePath, uuid);
            // 如果文件目录不存在，创建目录
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
                log.info("创建目录 : {}", filePath.getParentFile().getName());
            }
            // 写入文件
            file.transferTo(filePath);
            userDynamic.setDynamicContent(fileUrl);
        } else if (message != null && !message.isEmpty()){
            userDynamic.setDynamicContent(message);
        } else {
            return failed("动态发布格式错误");
        }
        userDynamicService.save(userDynamic);
        return success(null);
    }

    /**
     * 分页查询所有数据
     *
     * @param userDynamic 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(UserDynamic userDynamic,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(this.userDynamicService.page(page, new QueryWrapper<>(userDynamic)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userDynamicService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param userDynamic 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody UserDynamic userDynamic) {
        return success(this.userDynamicService.save(userDynamic));
    }

    /**
     * 修改数据
     *
     * @param userDynamic 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody UserDynamic userDynamic) {
        return success(this.userDynamicService.updateById(userDynamic));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.userDynamicService.removeByIds(idList));
    }
}
