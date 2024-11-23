package cn.willian.netty.rpc.spring.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:43:59
 */
@Configuration
@EnableConfigurationProperties(SpringRpcServerProperties.class)
public class SpringRpcServerAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(SpringRpcServerProperties properties) {

        return new SpringRpcProviderBean(properties);
    }
}
