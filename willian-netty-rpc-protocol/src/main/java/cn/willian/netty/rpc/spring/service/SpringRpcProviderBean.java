package cn.willian.netty.rpc.spring.service;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import cn.willian.netty.rpc.exception.RpcException;
import cn.willian.netty.rpc.netty.RpcNettyServer;
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

    private final String serverAddress;
    private final int serverPort;

    public SpringRpcProviderBean(String serverAddress, int serverPort) {

        if (StringUtils.isBlank(serverAddress)) {
            InetAddress localHost;
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                log.error("get local host error", e);
                throw new RpcException("get local host error");
            }
            this.serverAddress = localHost.getHostAddress();
        } else {
            this.serverAddress = serverAddress;
        }
        this.serverPort = serverPort;
    }

    // 当这个bean初始化之后，启动netty服务
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin start rpc netty server port:{}", this.serverPort);
        new Thread(() -> new RpcNettyServer(serverAddress, serverPort).start()).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取注解了RpcService的全部bean对象
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(RpcService.class)) {
            // 将对象和方法统一保存在一个map集合中
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                String key = beanClass.getInterfaces()[0].getName() + "." + method.getName();
                RpcMediator.getInstance().RPC_METHOD.put(key, RpcBeanMethod.of(bean, method));
            }
        }
        return bean;
    }
}
