package com.xinf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.LiveTogether;
import com.xinf.service.LiveTogetherService;
import com.zh.CommandManager;
import com.zh.CommandManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;

/**
 * (LiveTogether)表控制层
 *
 * @author makejava
 * @since 2021-09-06 08:40:45
 */
@RestController
@RequestMapping("liveTogether")
@Api(value = "LiveTogetherController", tags = { "一起看接口" })
public class LiveTogetherController extends ApiController{

    @Autowired
    LiveTogetherService liveTogetherService;

    @GetMapping("start/{id}")
    @ApiOperation("一起看启动")

    public R start(@PathVariable long id){
        /**
         * ffmpeg -re -i /var/live_together/kxwc.mp4 -vcodec copy -acodec copy -f flv -y rtmp://localhost:1935/live_together/kxwc
         */
        LiveTogether liveTogether = liveTogetherService.getById(id);
        CommandManager manager = new CommandManagerImpl();
        String res = manager.start(String.valueOf(id), "/usr/bin/ffmpeg -re -i /var/live_together/"+liveTogether.getLiveTogetherId()+" -vcodec copy -acodec copy " +
                "-f flv -y rtmp://localhost:1935/live_together/"+liveTogether.getLiveTogetherId(),true);
        if(res.equals(id)){
            liveTogether.setFlag(1);
            liveTogetherService.updateById(liveTogether);
            return success(null);
        }
        return failed("推流失败");
    }

    @RequestMapping("stop/{id}")
    @ApiOperation("一起看停止")

    public void stop(@PathVariable long id){
        LiveTogether liveTogether = liveTogetherService.getById(id);
        liveTogether.setFlag(0);
        new CommandManagerImpl().stop(String.valueOf(id));
        liveTogetherService.updateById(liveTogether);
    }

    @RequestMapping("notifyInfo")

    public void notifyInfo(HttpServletRequest request){
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String k = parameterNames.nextElement();
            String v = request.getParameter(k);
            System.out.println(k+" : "+v);
        }
    }

    /**
     * 分页查询所有数据
     * @param liveTogether 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("选择所有一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="liveTogether", value = "一起看"),
            @ApiImplicitParam(name ="pageCurrent", value = "当前页面"),
            @ApiImplicitParam(name ="pageSize", value = "页面尺寸")
    })
    public R selectAll(LiveTogether liveTogether,
                       @RequestParam(defaultValue = "10") long pageSize, @RequestParam(defaultValue = "1") long pageCurrent) {
        Page page = new Page(pageCurrent, pageSize, true);
        return success(liveTogetherService.page(page, new QueryWrapper<>(liveTogether)));
    }
    /**
     * 分页查询所有有效数据
     *
     * @param page 分页对象
     * @param liveTogether 查询实体
     * @return 所有数据
     */
    @GetMapping("selectAllEfficient")
    @ApiOperation("选择所有有效")
    @ApiImplicitParams({@ApiImplicitParam(name ="queryWrapper", value = "查询包装"),
            @ApiImplicitParam(name ="page", value = "页面")
    })
    public R selectAllEfficient(Page<LiveTogether> page, LiveTogether liveTogether) {
        QueryWrapper<LiveTogether> queryWrapper = new QueryWrapper();
        queryWrapper.eq("flag",1);
        return success(liveTogetherService.page(page, queryWrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation("选择单个一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="id ", value = "id")
    })
    public R selectOne(@PathVariable Serializable id) {
        return success(this.liveTogetherService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param liveTogether 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation("插入一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="liveTogether", value = "一起看")
    })
    public R insert(@RequestBody LiveTogether liveTogether) {
        return success(this.liveTogetherService.save(liveTogether));
    }

    /**
     * 修改数据
     *
     * @param liveTogether 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("更新一起看")
    @ApiImplicitParams({@ApiImplicitParam(name ="liveTogether", value = "一起看")
    })
    public R update(@RequestBody LiveTogether liveTogether) {
        return success(this.liveTogetherService.updateById(liveTogether));
    }
}
