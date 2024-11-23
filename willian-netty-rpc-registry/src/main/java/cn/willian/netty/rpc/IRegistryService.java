package cn.willian.netty.rpc;

import cn.willian.netty.rpc.bean.ServiceInfo;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 12:36:58
 */
public interface IRegistryService {

    /**
     * 服务注册
     * 
     * @param serviceInfo 服务信息
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 服务发现
     * 
     * @param serviceName 服务名称
     * @return 服务信息
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}
