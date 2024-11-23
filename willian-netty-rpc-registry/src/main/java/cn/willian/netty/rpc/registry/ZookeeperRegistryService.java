package cn.willian.netty.rpc.registry;

import java.util.Collection;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import com.google.common.collect.Lists;

import cn.willian.netty.rpc.IRegistryService;
import cn.willian.netty.rpc.bean.ServiceInfo;
import cn.willian.netty.rpc.constants.LoadBalanceTypeEnum;
import cn.willian.netty.rpc.loadbalance.IServiceLoadBalance;
import cn.willian.netty.rpc.loadbalance.ServiceLoadBalanceFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 12:39:33
 */
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    private static final String PATH = "/rpc/registry";

    ServiceDiscovery<ServiceInfo> serviceDiscovery = null;

    public ZookeeperRegistryService(String registryAddress) throws Exception {

        CuratorFramework client =
            CuratorFrameworkFactory.newClient(registryAddress, new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.blockUntilConnected();
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class).client(client)
            .serializer(new JsonInstanceSerializer<>(ServiceInfo.class)).basePath(PATH).build();
        serviceDiscovery.start();
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
            .name(serviceInfo.getServiceName()).address(serviceInfo.getServiceAddress())
            .port(serviceInfo.getServicePort()).payload(serviceInfo).build();
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {

        Collection<ServiceInstance<ServiceInfo>> serviceInstances = serviceDiscovery.queryForInstances(serviceName);
        IServiceLoadBalance<ServiceInstance<ServiceInfo>> serviceInstance =
            ServiceLoadBalanceFactory.getServiceInstance(LoadBalanceTypeEnum.RANDOM);
        // 负载均衡，找到对应服务
        ServiceInstance<ServiceInfo> select = serviceInstance.select(Lists.newArrayList(serviceInstances));
        return select.getPayload();
    }
}
