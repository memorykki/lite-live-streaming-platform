package com.zh.controller;
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

@RestController
@RequestMapping("notify")
public class NotifyController {

    @RequestMapping("/publish")
    public boolean publish(@RequestParam String name){
        System.out.println(name+"开始推流");
        return true;
    }

    @RequestMapping("/publish_done")
    public boolean publish_done(@RequestParam String name){
        System.out.println(name+"停止推流");
        return true;
    }

    @RequestMapping("/play")
    public boolean play(@RequestParam String name,
                        @RequestParam String addr){
        System.out.println(name+" "+addr+"开始拉流");
        return true;
    }


    @RequestMapping("/play_done")
    public boolean play_done(@RequestParam String name,
                             @RequestParam String addr){
        System.out.println(name+" "+addr+"停止拉流");
        return true;
    }

    @RequestMapping("/record_done")
    public boolean record_done(@RequestParam String name,
                               @RequestParam String path,
                               @RequestParam String recorder){
        System.out.println("直播间"+name+"保存回放；路径"+path+"recorder："+recorder);
        return true;
    }
}
