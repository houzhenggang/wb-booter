package com.fxsd.framwork.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @Description WebSocket Configure
 * @author cjh
 * @version 1.0
 * @date：2016年4月6日 下午2:42:04
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = { "com.yaoa.app.controller" })
public class WebSocketConfigure extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/webserver").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//表示在topic和user这两个域上可以向客户端发消息
		registry.enableSimpleBroker("/user/", "/topic/");
		//表示客户端向服务端发送时的主题上面需要加"/app"作为前缀
		registry.setApplicationDestinationPrefixes("/app");
		//表示给指定用户发送（一对一）的主题前缀
		registry.setUserDestinationPrefix("/user/");
	}
	
	/**
	 * 消息传输参数配置
	 */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(8192) // 设置消息字节数大小
				.setSendBufferSizeLimit(8192)// 设置消息缓存大小
				.setSendTimeLimit(10000); // 设置消息发送时间限制毫秒
    }
    
    /**
     * 输入通道参数设置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数
        .maxPoolSize(8)//最大线程数
        .keepAliveSeconds(60);//线程活动时间
    }
    
    /**
     * 输出通道参数设置
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }


}
