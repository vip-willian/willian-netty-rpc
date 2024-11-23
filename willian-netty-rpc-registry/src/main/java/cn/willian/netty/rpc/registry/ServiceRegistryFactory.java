package cn.willian.netty.rpc.registry;

import cn.willian.netty.rpc.IRegistryService;
import cn.willian.netty.rpc.constants.RegistryTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 13:03:04
 */
@Slf4j
public class ServiceRegistryFactory {

    public static IRegistryService get(byte registryType, String registryAddress) {

        IRegistryService result = null;
        try {
            switch (RegistryTypeEnum.getRegistryType(registryType)) {
                case ZOOKEEPER:
                    result = new ZookeeperRegistryService(registryAddress);
                    break;
                case NACOS:
                    // TODO: 沐风 2024-11-23 13:10:11
                    break;
                default:
                    result = new ZookeeperRegistryService(registryAddress);
                    break;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("服务注册中心启动失败");
        }
    }
}
