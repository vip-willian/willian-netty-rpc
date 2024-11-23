package cn.willian.netty.rpc.bean;

import lombok.Data;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 12:37:19
 */
@Data
public class ServiceInfo {

    private String serviceName;
    private String serviceAddress;
    private Integer servicePort;
}
