package com.xinf.controller;
import com.xinf.entity.Playback;
import com.xinf.entity.Room;
import com.xinf.service.PlaybackService;
import com.xinf.service.RoomService;
import com.xinf.util.RedisUtil;
import io.swagger.annotations.Api;
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

    /**
     * app: live
     * flashver: FMLE/3.0 (compatible; FMSc/1.0)
     * swfurl: rtmp://ts.memorykk.cn:1935/live
     * tcurl: rtmp://ts.memorykk.cn:1935/live
     * pageurl:
     * addr: 113.140.84.104
     * clientid: 18
     * call: publish_done
     * name: 6
     * @param request
     * @return
     */
    @RequestMapping("/publish_done")
    public boolean publish_done(HttpServletRequest request){
        String roomId = request.getParameter("name");
        Room room = roomService.getById(roomId);
        if(room != null) {
            room.setRoomStatus(2);
            roomService.updateById(room);
            log.info("房间号: {} 关闭推流", roomId);
            return true;
        }else{
            return false;
        }
    }

    /**
     * app: live
     * flashver: LNX 9,0,124,2
     * swfurl:
     * tcurl: rtmp://ts.memorykk.cn:1935/live
     * pageurl:
     * addr: 113.140.84.104
     * clientid: 25
     * call: play
     * name: 6
     * start: 4294965296
     * duration: 0
     * reset: 0
     * @param request
     * @return
     */
    @RequestMapping("/play")
    public boolean play(HttpServletRequest request){
        //if(redisUtil.zScore("recommand", roomName) == null){
        //    redisUtil.zAdd("recommand", roomName,1);
        //}else{
        //    redisUtil.zIncrementScore("recommand", roomName,1);
        //}
        String roomId = request.getParameter("name");
        redisUtil.zIncrementScore("recommand", roomId,1);
        log.info("来自IP: {} 开始观看房间: {} ", request.getParameter("addr"),roomId);
        return true;
    }

    /**
     * app: live
     * flashver: LNX 9,0,124,2
     * swfurl:
     * tcurl: rtmp://ts.memorykk.cn:1935/live
     * pageurl:
     * addr: 113.140.84.104
     * clientid: 25
     * call: play_done
     * name: 6
     * @param request
     * @return
     */
    @RequestMapping("/play_done")
    public boolean play_done(HttpServletRequest request){
        String roomId = request.getParameter("name");
        if(redisUtil.zScore("recommand", roomId).isPresent()){
            if (redisUtil.zIncrementScore("recommand", roomId, -1).intValue() < 1 ) {
                redisUtil.zRemove("recommand", roomId);
                log.info("来自IP: {} 结束观看房间: {} ", request.getParameter("addr"),roomId);
            }
        }
        return true;
    }

    /**
     * app: live
     * flashver: FMLE/3.0 (compatible; FMSc/1.0)
     * swfurl: rtmp://ts.memorykk.cn:1935/live
     * tcurl: rtmp://ts.memorykk.cn:1935/live
     * pageurl:
     * addr: 113.140.84.104
     * clientid: 18
     * call: record_done
     * recorder:
     * name: 6
     * path: /usr/local/nginx/html/record/6-2021-09-16-10_58_43.flv
     * @param request
     * @return
     */
    @RequestMapping("/record_done")
    public boolean record_done(HttpServletRequest request){
        String roomId = request.getParameter("name");
        String path = "http://ts.memorykk.cn:1936/" + request.getParameter("path").substring(22);
        Playback playback = new Playback();
        playback.setRoomId(Long.parseLong(roomId));
        playback.setPlaybackPath(path);
        playbackService.save(playback);
        log.info("直播间: {} 保存回放， 地址为 {}", roomId, path);
        return true;
    }
}
