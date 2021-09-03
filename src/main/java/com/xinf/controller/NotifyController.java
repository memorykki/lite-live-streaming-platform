package com.xinf.controller;
import com.xinf.entity.Playback;
import com.xinf.entity.Room;
import com.xinf.entity.UserWatchHistory;
import com.xinf.service.PlaybackService;
import com.xinf.service.RoomService;
import com.xinf.service.UserService;
import com.xinf.service.UserWatchHistoryService;
import com.xinf.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    PlaybackService playbackService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/publish")
    public boolean publish(@RequestParam String name){
        Room room = roomService.getById(name);
        if(room != null) {
            room.setRoomStatus(1);
            roomService.updateById(room);
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/publish_done")
    public boolean publish_done(@RequestParam String name){
        Room room = roomService.getById(name);
        if(room != null) {
            room.setRoomStatus(0);
            roomService.updateById(room);
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/play")
    public boolean play(@RequestParam String name,
                        @RequestParam String addr){
        if(!redisUtil.sIsMember(name,addr)){
            redisUtil.sAdd(name, addr);
            if (!redisUtil.setIfAbsent(name, "1")) {
                redisUtil.incrBy(name, 1);
            }
        }
        return true;
    }

    @RequestMapping("/play_done")
    public boolean play_done(@RequestParam String name,
                             @RequestParam String addr){
        redisUtil.sRemove(name,addr);
        redisUtil.incrBy(name,-1);
        return true;
    }

    @RequestMapping("/record_done")
    public boolean record_done(@RequestParam String name,
                               @RequestParam String path){
        Playback playback = new Playback();
        playback.setRoomId(Long.parseLong(name));
        playback.setPlaybackPath(path);
        log.info("直播间:"+name+"  保存回放；"+path);
        return true;
    }
}
