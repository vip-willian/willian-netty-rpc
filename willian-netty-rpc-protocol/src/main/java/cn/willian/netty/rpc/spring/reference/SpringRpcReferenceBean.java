package cn.willian.netty.rpc.spring.reference;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 11:17:34
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object bean;
    private String serviceAddress;
    private Integer servicePort;
    private Class<?> serviceInterface;

    public void init() {
        this.bean = Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[] {serviceInterface},
            new RpcClientInvokeHandler(serviceAddress, servicePort));
    }

    @Override
    public Object getObject() throws Exception {
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
