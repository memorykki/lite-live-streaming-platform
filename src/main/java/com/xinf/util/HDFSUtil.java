package com.xinf.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HDFS 相关操作
 * @author xinf
 * @since 2021/9/11 17:21
 */
@Slf4j
public class HDFSUtil {

    private Configuration conf = null;

    /**
     * 默认的HDFS路径，比如：hdfs://192.168.197.130:9000
     */
    private String defaultHdfsUri;

    public HDFSUtil(Configuration conf, String defaultHdfsUri) {
        this.conf = conf;
        this.defaultHdfsUri = defaultHdfsUri;
    }

    /**
     * 获取HDFS文件系统
     * @return org.apache.hadoop.fs.FileSystem
     */
    private FileSystem getFileSystem() throws IOException {
        return FileSystem.get(conf);
    }

    /**
     * 创建HDFS目录
     * @param path HDFS的相对目录路径，比如：/testDir
     * @return boolean 是否创建成功
     */
    public boolean mkdir(String path){
        //如果目录已经存在，则直接返回
        if(checkExists(path)){
            return true;
        } else {
            FileSystem fileSystem = null;

            try {
                fileSystem = getFileSystem();

                //最终的HDFS文件目录
                String hdfsPath = generateHdfsPath(path);
                //创建目录
                return fileSystem.mkdirs(new Path(hdfsPath));
            } catch (IOException e) {
                log.error("创建HDFS目录失败，path:{}",path);
                return false;
            }finally {
                close(fileSystem);
            }
        }
    }

    /**
     * 上传文件至HDFS
     * @param srcFile 本地文件路径，比如：D:/test.txt
     * @param dstPath HDFS的相对目录路径，比如：/testDir
     */
    public void uploadFileToHdfs(String srcFile, String dstPath){
        this.uploadFileToHdfs(false, true, srcFile, dstPath);
    }

    /**
     * 上传文件至HDFS
     * @param inputStream 文件流
     * @param dstFile HDFS上传文件的路径，比如：/testDir/test.txt
     */
    public boolean uploadFileToHdfs(InputStream inputStream, String dstFile) {
        if (inputStream == null) {
            log.error("上传文件流为null");
            return false;
        }
        FileSystem fileSystem = null;
        OutputStream outputStream = null;
        try {
            fileSystem = getFileSystem();
            outputStream = fileSystem.create(new Path(dstFile));
            IOUtils.copy(inputStream, outputStream);
            return true;
        } catch (IOException e) {
            log.error("上传文件流至HDFS失败，dstFile: {}", dstFile);
            return false;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 上传文件至HDFS
     * @param delSrc 是否删除本地文件
     * @param overwrite 是否覆盖HDFS上面的文件
     * @param srcFile 本地文件路径，比如：D:/test.txt
     * @param dstPath HDFS的相对目录路径，比如：/testDir
     */
    public void uploadFileToHdfs(boolean delSrc, boolean overwrite, String srcFile, String dstPath){
        //源文件路径
        Path localSrcPath = new Path(srcFile);
        //目标文件路径
        Path hdfsDstPath = new Path(generateHdfsPath(dstPath));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            fileSystem.copyFromLocalFile(delSrc, overwrite, localSrcPath, hdfsDstPath);
        } catch (IOException e) {
            log.error("上传文件至HDFS失败，srcFile: {},dstPath: {}", srcFile, dstPath);
        }finally {
            close(fileSystem);
        }
    }

    /**
     * 判断文件或者目录是否在HDFS上面存在
     * @param path HDFS的相对目录路径，比如：/testDir、/testDir/a.txt
     * @return boolean
     */
    public boolean checkExists(String path){
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            //最终的HDFS文件目录
            String hdfsPath = generateHdfsPath(path);

            //创建目录
            return fileSystem.exists(new Path(hdfsPath));
        } catch (IOException e) {
            log.error("判断文件或者目录是否在HDFS上面存在失败，path:{}", path);
            return false;
        }finally {
            close(fileSystem);
        }
    }

    /**
     * 获取HDFS上面的某个路径下面的所有文件或目录（不包含子目录）信息
     * @param path HDFS的相对目录路径，比如：/testDir
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public List<Map<String, Object>> listFiles(String path, PathFilter pathFilter){
        //返回数据
        List<Map<String,Object>> result = new ArrayList<>();

        if (!checkExists(path)) {
            return result;
        }

        //如果目录已经存在，则继续操作
        FileSystem fileSystem = null;

        try {
            fileSystem = getFileSystem();

            //最终的HDFS文件目录
            String hdfsPath = generateHdfsPath(path);

            FileStatus[] statuses;
            //根据Path过滤器查询
            if(pathFilter != null) {
                statuses = fileSystem.listStatus(new Path(hdfsPath), pathFilter);
            } else {
                statuses = fileSystem.listStatus(new Path(hdfsPath));
            }

            if(statuses != null) {
                for(FileStatus status : statuses){
                    //每个文件的属性
                    Map<String,Object> fileMap = new HashMap<>(2);
                    fileMap.put("path", status.getPath().toString());
                    fileMap.put("isDir", status.isDirectory());
                    result.add(fileMap);
                }
            }
        } catch (IOException e) {
            log.error("获取HDFS上面的某个路径下面的所有文件失败，path: {}",path);
        }finally {
            close(fileSystem);
        }
        return result;
    }


    /**
     * 从HDFS下载文件至本地
     * @param srcFile HDFS的相对目录路径，比如：/testDir/a.txt
     * @param dstFile 下载之后本地文件路径（如果本地文件目录不存在，则会自动创建），比如：D:/test.txt
     */
    public void downloadFileFromHdfs(String srcFile, String dstFile){
        //HDFS文件路径
        Path hdfsSrcPath = new Path(generateHdfsPath(srcFile));
        //下载之后本地文件路径
        Path localDstPath = new Path(dstFile);

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            fileSystem.copyToLocalFile(hdfsSrcPath, localDstPath);
        } catch (IOException e) {
            log.error("从HDFS下载文件至本地失败，srcFile: {},dstFile: {}", srcFile, dstFile);
        }finally {
            close(fileSystem);
        }
    }

    /**
     *  将HDFS上面的文件转发到流上
     */
    public void downloadFileFromHdfs(String path, OutputStream out) {
        FSDataInputStream in = open(path);
        if (in == null || out == null) {
            log.error("HDFS上文件为null或out流为null, path: {}", path);
            return;
        }
        try {
            IOUtils.copy(in, out);
        } catch (IOException e) {
            log.error("从HDFS下载文件至本地失败，srcFile: {}", path);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
            try {
                out.flush();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 打开HDFS上面的文件并返回 InputStream
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return FSDataInputStream
     */
    public FSDataInputStream open(String path) {
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.open(hdfsPath);
        } catch (IOException e) {
            log.error("打开HDFS上面的文件失败，path:{}",path);
        }

        return null;
    }

    /**
     * 打开HDFS上面的文件并返回byte数组，方便Web端下载文件
     * <p>new ResponseEntity<byte[]>(byte数组, headers, HttpStatus.CREATED);</p>
     * <p>或者：new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(templateFile), headers, HttpStatus.CREATED);</p>
     * @param path HDFS的相对目录路径，比如：/testDir/b.txt
     * @return FSDataInputStream
     */
    public byte[] openWithBytes(String path){
        byte[] ans = new byte[0];
        FSDataInputStream in = open(path);
        if (in == null) {
            return ans;
        }
        try {
            ans = IOUtils.toByteArray(in);
        } catch (IOException e) {
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
            return ans;
        }
    }

    /**
     * 重命名
     * @param srcFile 重命名之前的HDFS的相对目录路径，比如：/testDir/b.txt
     * @param dstFile 重命名之后的HDFS的相对目录路径，比如：/testDir/b_new.txt
     */
    public boolean rename(String srcFile, String dstFile) {

        Path srcFilePath = new Path(generateHdfsPath(srcFile));
        Path dstFilePath = new Path(generateHdfsPath(dstFile));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.rename(srcFilePath, dstFilePath);
        } catch (IOException e) {
            log.error("重命名失败，srcFile:{}, dstFile:{}", srcFile, dstFile);
        }finally {
            close(fileSystem);
        }
        return false;
    }

    /**
     * 删除HDFS文件或目录
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return boolean
     */
    public boolean delete(String path) {
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();

            return fileSystem.delete(hdfsPath, true);
        } catch (IOException e) {
            log.error("删除HDFS文件或目录失败，path:{}", path);
        }finally {
            close(fileSystem);
        }

        return false;
    }

    /**
     * 获取某个文件在HDFS集群的位置
     * @param path HDFS的相对目录路径，比如：/testDir/a.txt
     * @return org.apache.hadoop.fs.BlockLocation[]
     */
    public BlockLocation[] getFileBlockLocations(String path) {
        //HDFS文件路径
        Path hdfsPath = new Path(generateHdfsPath(path));

        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();
            FileStatus fileStatus = fileSystem.getFileStatus(hdfsPath);

            return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } catch (IOException e) {
            log.error("获取某个文件在HDFS集群的位置失败，path:{}",path);
        }finally {
            close(fileSystem);
        }

        return null;
    }


    /**
     * 将相对路径转化为HDFS文件路径
     * @param dstPath 相对路径，比如：/data
     * @return java.lang.String
     */
    private String generateHdfsPath(String dstPath){
        String hdfsPath = defaultHdfsUri;
        if(dstPath.startsWith("/")) {
            hdfsPath += dstPath;
        } else {
            hdfsPath = hdfsPath + "/" + dstPath;
        }

        return hdfsPath;
    }

    /**
     * close方法
     */
    private void close(FileSystem fileSystem){
        if(fileSystem != null){
            try {
                fileSystem.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
