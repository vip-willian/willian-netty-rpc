package cn.willian.netty.rpc.spring.reference;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import cn.willian.netty.rpc.IRegistryService;
import cn.willian.netty.rpc.bean.ServiceInfo;
import cn.willian.netty.rpc.registry.ServiceRegistryFactory;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 11:17:34
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object bean;
    private Class<?> serviceInterface;
    private String registryAddress;
    private byte registryType;

    public void init() throws Exception {

        IRegistryService registryService = ServiceRegistryFactory.get(registryType, registryAddress);
        // 服务发现
        ServiceInfo serviceInfo = registryService.discovery(serviceInterface.getName());
        this.bean = Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[] {serviceInterface},
            new RpcClientInvokeHandler(serviceInfo.getServiceAddress(), serviceInfo.getServicePort()));
    }

    @Override
    public Object getObject() throws Exception {
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
