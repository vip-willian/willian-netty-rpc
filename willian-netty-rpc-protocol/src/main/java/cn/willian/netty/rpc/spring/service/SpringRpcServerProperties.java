package cn.willian.netty.rpc.spring.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import cn.willian.netty.rpc.exception.RpcException;
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
    private String registryAddress;
    private byte registryType;

    public String getServerAddress() {

        if (StringUtils.isBlank(serverAddress)) {
            InetAddress localHost;
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new RpcException("get local host error");
            }
            this.serverAddress = localHost.getHostAddress();
        }
        return serverAddress;
    }
}
