package com.xinf.controller;
import com.xinf.entity.Playback;
import com.xinf.entity.Room;
import com.xinf.service.PlaybackService;
import com.xinf.service.RoomService;
import com.xinf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月03日 14:52
 *
 * 回调函数：
 * on_publish http://127.0.0.1:8081/lite-live-streaming-platform/notify/publish;
 * on_publish_done http://127.0.0.1:8081/lite-live-streaming-platform/notify/publish_done;
 * on_play http://127.0.0.1:8081/lite-live-streaming-platform/notify/play;
 * on_play_done http://127.0.0.1:8081/lite-live-streaming-platform/notify/play_done;
 * on_done http://127.0.0.1:8081/lite-live-streaming-platform/notify/done;
 * on_record_done http://127.0.0.1:8081/lite-live-streaming-platform/notify/record_done;
 */
@Slf4j
@RestController
@RequestMapping("notify")
public class NotifyController {

    @Autowired
    RoomService roomService;
    @Autowired
    PlaybackService playbackService;
    @Autowired
    RedisUtil redisUtil;

    /**
     * app: live
     * flashver: FMLE/3.0 (compatible; FMSc/1.0)
     * swfurl: rtmp://ts.memorykk.cn:1935/live
     * tcurl: rtmp://ts.memorykk.cn:1935/live
     * pageurl:
     * addr: 113.140.84.104
     * clientid: 12
     * call: publish
     * name: 6
     * type: live
     * @param request
     * @return
     */
    @RequestMapping("/publish")
    public boolean publish(HttpServletRequest request){
        System.out.println("-----------publish----------");
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }

        String roomId = request.getParameter("name");
        Room room = roomService.getById(roomId);
        if(room != null) {
            room.setRoomStatus(1);
            roomService.updateById(room);
            log.info("房间号: {}开始推流", roomId);
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/publish_done")
    public boolean publish_done(HttpServletRequest request){
        System.out.println("-----------publish_done----------");
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }

        String roomId = request.getParameter("name");
        Room room = roomService.getById(roomId);
        if(room != null) {
            room.setRoomStatus(0);
            roomService.updateById(room);
            log.info("房间号: {} 关闭推流", roomId);
            return true;
        }else{
            return false;
        }
    }

    // roomId : 房间id
    @RequestMapping("/play")
    public boolean play(HttpServletRequest request){
        System.out.println("-----------play----------");
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }

        //if(redisUtil.zScore("recommand", roomName) == null){
        //    redisUtil.zAdd("recommand", roomName,1);
        //}else{
        //    redisUtil.zIncrementScore("recommand", roomName,1);
        //}
        String roomId = request.getParameter("name");
        redisUtil.zIncrementScore("recommand", roomId,1);
        return true;
    }

    @RequestMapping("/play_done")
    public boolean play_done(HttpServletRequest request){
        System.out.println("-----------play_done----------");
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }

        String roomId = request.getParameter("name");
        if(redisUtil.zScore("recommand", roomId).isPresent()){
            if (redisUtil.zIncrementScore("recommand", roomId, -1).intValue() < 1 ) {
                redisUtil.zRemove("recommand", roomId);
            }
        }
        return true;
    }

    @RequestMapping("/record_done")
    public boolean record_done(HttpServletRequest request){

        System.out.println("-----------play----------");
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }

//        Playback playback = new Playback();
//        playback.setRoomId(roomId);
//        playback.setPlaybackPath(path);
//        playbackService.save(playback);
//        log.info("直播间: {} 保存回放， 地址为 {}", roomId, path);
        return true;
    }
}
