package com.xinf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author xinf
 * @since 2021/8/31 20:12
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    /**
     *  弹幕实时，不缓存
     *  文件上传时只能针对特定的主机，所以无法分布式
     *  不需要借助redis pub、sub转发/(ㄒoㄒ)/~~
     */

    /**
     * 创建连接工厂
     * @param connectionFactory 
     * @param listenerAdapter
     * @return
     */
    //@Bean
    //public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
    //                                               MessageListenerAdapter listenerAdapter){
    //    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    //    container.setConnectionFactory(connectionFactory);
    //    //接受消息的key
    //    container.addMessageListener(listenerAdapter,new PatternTopic("demo"));
    //    return container;
    //}

    /**
     * 绑定消息监听者和接收监听的方法
     * @param receiver
     * @return
     */
    //@Bean
    //public MessageListenerAdapter listenerAdapter(ReceiverRedisMessage receiver){
    //    return new MessageListenerAdapter(receiver,"receiveMessage");
    //}

    /**
     * 注册订阅者
     * @return
     */
    //@Bean
    //ReceiverRedisMessage receiver() {
    //    return new ReceiverRedisMessage();
    //}

    // hdfs
    //@Bean
    //public HDFSUtil getHdfsService() {
    //    final String defaultHdfsUri = "hdfs://xinf.memorykk.cn:9091";
    //    org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
    //    conf.set("fs.defaultFS", defaultHdfsUri);
    //    return new HDFSUtil(conf, defaultHdfsUri);
    //}

}
