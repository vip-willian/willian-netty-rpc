package cn.willian.netty.rpc.spring.reference;

import java.util.Objects;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:43:59
 */
@Configuration
@EnableConfigurationProperties(SpringRpcClientProperties.class)
public class SpringRpcReferenceAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor springRpcReferencePostProcessor() {

        SpringRpcClientProperties properties = new SpringRpcClientProperties();
        properties.setServiceAddress(environment.getProperty("willian.rpc.client.service-address"));
        properties.setServicePort(
            Integer.parseInt(Objects.requireNonNull(environment.getProperty("willian.rpc.client.service-port"))));
        return new SpringRpcReferencePostProcessor(properties);
    }
}
