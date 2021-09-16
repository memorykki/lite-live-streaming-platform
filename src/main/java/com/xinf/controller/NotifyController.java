package com.xinf.controller;
import com.xinf.entity.Playback;
import com.xinf.entity.Room;
import com.xinf.service.PlaybackService;
import com.xinf.service.RoomService;
import com.xinf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/publish")
    public boolean publish(HttpServletRequest request){
//        Room room = roomService.getById(id);
//        if(room != null) {
//            room.setRoomStatus(1);
//            roomService.updateById(room);
//            log.info("房间号: {}开始推流", id);
//            return true;
//        }else{
//            return false;
//        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String paraName=(String)parameterNames.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        return true;
    }

    @RequestMapping("/publish_done")
    public boolean publish_done(@RequestParam long id){
        Room room = roomService.getById(id);
        if(room != null) {
            room.setRoomStatus(0);
            roomService.updateById(room);
            log.info("房间号: {} 关闭推流", id);
            return true;
        }else{
            return false;
        }
    }

    // roomName : 房间id
    @RequestMapping("/play")
    public boolean play(@RequestParam String roomName){
        //if(redisUtil.zScore("recommand", roomName) == null){
        //    redisUtil.zAdd("recommand", roomName,1);
        //}else{
        //    redisUtil.zIncrementScore("recommand", roomName,1);
        //}
        redisUtil.zIncrementScore("recommand", roomName,1);
        return true;
    }

    @RequestMapping("/play_done")
    public boolean play_done(@RequestParam String roomName){
        if(redisUtil.zScore("recommand", roomName).isPresent()){
            if (redisUtil.zIncrementScore("recommand", roomName, -1).intValue() < 1 ) {
                redisUtil.zRemove("recommand", roomName);
            }
        }
        return true;
    }

    @RequestMapping("/record_done")
    public boolean record_done(@RequestParam long roomId,
                               @RequestParam String path){
        Playback playback = new Playback();
        playback.setRoomId(roomId);
        playback.setPlaybackPath(path);
        playbackService.save(playback);
        log.info("直播间: {} 保存回放， 地址为 {}", roomId, path);
        return true;
    }
}
