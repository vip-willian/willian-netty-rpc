package cn.willian.netty.rpc.spring.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:39:08
 */
@Data
@ConfigurationProperties(prefix = "willian.rpc.service")
public class SpringRpcServerProperties {

    private String serverAddress;
    private Integer serverPort;
}
