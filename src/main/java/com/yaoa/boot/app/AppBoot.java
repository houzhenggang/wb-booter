package com.yaoa.boot.app;

import com.yaoa.boot.app.config.DatabaseConfigure;
import com.yaoa.boot.app.config.RedisConfigure;
import com.yaoa.boot.app.config.ServletConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Description 应用启动类
 * @author cjh
 * @version 1.0
 * @date：2017年2月15日 下午6:22:28
 */
@SpringBootApplication
@Import({DatabaseConfigure.class, ServletConfigure.class, RedisConfigure.class})
public class AppBoot {

	public static void main(String[] args) {
		SpringApplication.run(AppBoot.class, args);
	}

}
