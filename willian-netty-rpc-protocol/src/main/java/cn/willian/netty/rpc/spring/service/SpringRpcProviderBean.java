package cn.willian.netty.rpc.spring.service;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import cn.willian.netty.rpc.IRegistryService;
import cn.willian.netty.rpc.bean.ServiceInfo;
import cn.willian.netty.rpc.netty.RpcNettyServer;
import cn.willian.netty.rpc.registry.ServiceRegistryFactory;
import cn.willian.netty.rpc.spring.annotation.RpcService;
import cn.willian.netty.rpc.spring.bean.RpcBeanMethod;
import cn.willian.netty.rpc.spring.bean.RpcMediator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:12:00
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private final SpringRpcServerProperties serverProperties;
    private final IRegistryService registryService;

    public SpringRpcProviderBean(SpringRpcServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        registryService =
            ServiceRegistryFactory.get(serverProperties.getRegistryType(), serverProperties.getRegistryAddress());
    }

    // 当这个bean初始化之后，启动netty服务
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin start rpc netty server port:{}", this.serverProperties.getServerPort());
        new Thread(
            () -> new RpcNettyServer(serverProperties.getServerAddress(), serverProperties.getServerPort()).start())
            .start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取注解了RpcService的全部bean对象
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(RpcService.class)) {
            // 将对象和方法统一保存在一个map集合中
            Method[] methods = beanClass.getDeclaredMethods();

            String serviceName = beanClass.getInterfaces()[0].getName();
            for (Method method : methods) {
                String key = serviceName + "." + method.getName();
                RpcMediator.getInstance().RPC_METHOD.put(key, RpcBeanMethod.of(bean, method));
            }
            // 将服务注册到注册中心
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setServiceAddress(serverProperties.getServerAddress());
            serviceInfo.setServicePort(serverProperties.getServerPort());
            serviceInfo.setServiceName(serviceName);
            try {
                registryService.register(serviceInfo);
            } catch (Exception e) {
                log.error("服务注册失败", e);
            }
        }
        return bean;
    }
}
