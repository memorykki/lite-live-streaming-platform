package com.zh.controller;

import com.zh.CommandManager;
import com.zh.CommandManagerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description: TODO
 * @author: zh
 * @date: 2021年09月04日 17:51
 */
@RestController
@RequestMapping("vod")
public class VodController {
    @RequestMapping("start")
    public void start(){
        /**
         * ffmpeg -re -i /var/live_together/kxwc.mp4 -vcodec copy -acodec copy
         * -f flv -y rtmp://localhost:1935/live_together/kxwc
         */
        CommandManager manager = new CommandManagerImpl();
        String res = manager.start("test1","/usr/bin/ffmpeg -re -i /var/live_together/kxwc.mp4 -vcodec copy -acodec copy " +
                "-f flv -y rtmp://localhost:1935/live_together/kxwc",true);
        System.out.println("res:"+res);
    }

    @RequestMapping("stop")
    public void stop(){
        new CommandManagerImpl().stop("test1");
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
}
