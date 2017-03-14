package com.yaoa.boot.app.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.session.config.EnableRedissonHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description web应用配置
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午6:29:49
 */
@Configuration
public class RedisConfigure extends WebMvcConfigurerAdapter {

	@Value("${redis.nodes}")
	private String nodes;

	/**
	 * 单例模式
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("#{'singlton'.equals('${redis.mode}')}")
	public RedissonClient singltonRedisson() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(nodes);
		return Redisson.create(config);
	}

	/**
	 * 集群模式
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("#{'cluster'.equals('${redis.mode}')}")
	public RedissonClient clusterRedisson() {
		Config config = new Config();
		config.useClusterServers()
				.setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
				.addNodeAddress(nodes.split(","));
		return Redisson.create(config);
	}

	/**
	 * 哨兵模式
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("#{'sentinel'.equals('${redis.mode}')}")
	public RedissonClient sentinelRedisson() {
		Config config = new Config();
		config.useSentinelServers().setMasterName("master").addSentinelAddress(nodes);
		return Redisson.create(config);
	}

	/**
	 * 哨兵模式
	 * @return
	 */
	@Bean
	@ConditionalOnExpression("#{'master-slave'.equals('${redis.mode}')}")
	public RedissonClient masterSlaveRedisson() {
		Config config = new Config();
		config.useSingleServer().setAddress(nodes);
		return Redisson.create(config);
	}




}
