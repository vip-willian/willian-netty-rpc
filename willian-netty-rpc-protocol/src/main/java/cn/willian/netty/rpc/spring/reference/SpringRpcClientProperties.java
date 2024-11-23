package cn.willian.netty.rpc.spring.reference;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:39:08
 */
@Data
@ConfigurationProperties(prefix = "willian.rpc.client")
public class SpringRpcClientProperties {

    private String registryAddress;
    private byte registryType;
}
