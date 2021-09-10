package com.xinf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinf.entity.LiveTogether;
import com.xinf.service.LiveTogetherService;
import com.zh.CommandManager;
import com.zh.CommandManagerImpl;
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
public class LiveTogetherController extends ApiController{

    @Autowired
    LiveTogetherService liveTogetherService;

    @GetMapping("start")
    public R start(@RequestParam String id){
        /**
         * ffmpeg -re -i /var/live_together/kxwc.mp4 -vcodec copy -acodec copy -f flv -y rtmp://localhost:1935/live_together/kxwc
         */
        LiveTogether liveTogether = liveTogetherService.getById(id);
        CommandManager manager = new CommandManagerImpl();
        String res = manager.start(id,"/usr/bin/ffmpeg -re -i /var/live_together/"+liveTogether.getName()+" -vcodec copy -acodec copy " +
                "-f flv -y rtmp://localhost:1935/live_together/"+liveTogether.getLiveTogetherId(),true);
        if(res.equals(id)){
            liveTogether.setFlag(1);
            liveTogetherService.updateById(liveTogether);
            return success(null);
        }
        return failed("推流失败");
    }

    @RequestMapping("stop")
    public void stop(@RequestParam String id){
        LiveTogether liveTogether = liveTogetherService.getById(id);
        liveTogether.setFlag(0);
        new CommandManagerImpl().stop(id);
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
    public R update(@RequestBody LiveTogether liveTogether) {
        return success(this.liveTogetherService.updateById(liveTogether));
    }
}
