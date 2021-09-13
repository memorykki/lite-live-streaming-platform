package com.xinf.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.xinf.constant.FilePathConstant;
import com.xinf.util.Strings;
import com.xinf.util.UUIDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author xinf
 * @since 2021/9/9 14:49
 */
@Slf4j
@RestController
@RequestMapping("fileUp")
@Api(value = "文件上传controller", tags = { "文件上传访问接口" })
public class FileController extends ApiController {

    @Resource
    private FilePathConstant filePathConstant;
    //@Resource
    //private HDFSUtil hdfsUtil;

    @ApiOperation(value = "文件上传", notes = "返回文件url链接")
    @PostMapping
    public R fileUp(@RequestParam MultipartFile file) throws IOException {
        if (checkFileEmpty(file)) {
            return failed("文件不存在");
        }
        String uuid = UUIDUtil.getUUID() + Strings.getFileType(file);
        String fileUrl = filePathConstant.commonFileUrl + uuid;
        // 创建文件实例
        File filePath = new File(filePathConstant.commonFilePath, uuid);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            log.info("创建目录 : {}", filePath.getParentFile().getName());
        }
        // 写入文件
        file.transferTo(filePath);
        return success(fileUrl);
    }

    //@ApiOperation(value = "hdfs文件上传")
    //@PostMapping("/hdfs")
    //public R fileUpHdfs(@RequestParam MultipartFile file) throws IOException {
    //    if (checkFileEmpty(file)) {
    //        return failed("文件不存在");
    //    }
    //    String uuid = UUIDUtil.getUUID() + Strings.getFileType(file);
    //    if (hdfsUtil.uploadFileToHdfs(file.getInputStream(), filePathConstant.hdfsFilePath + uuid)) {
    //        return success(null);
    //    }
    //    return failed("上传hdfs失败");
    //}
    //
    //@ApiOperation(value = "hdfs文件下载")
    //@GetMapping("/hdfsDown/{fileName}")
    //public void fileDownHdfs(@PathParam("fileName") String fileName,
    //           HttpServletRequest request, HttpServletResponse response) throws IOException {
    //    response.setContentType("application/octet-stream; charset=utf-8");
    //    response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
    //    String path = filePathConstant.hdfsFilePath + fileName;
    //    hdfsUtil.downloadFileFromHdfs(path, response.getOutputStream());
    //}

    private boolean checkFileEmpty (MultipartFile file) {
        return file == null || file.isEmpty();
    }
}
