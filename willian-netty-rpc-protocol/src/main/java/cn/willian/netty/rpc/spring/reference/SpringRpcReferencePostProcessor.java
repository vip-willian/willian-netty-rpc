package cn.willian.netty.rpc.spring.reference;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Maps;

import cn.willian.netty.rpc.spring.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 11:22:27
 */
@Slf4j
public class SpringRpcReferencePostProcessor
    implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private final SpringRpcClientProperties properties;
    private final Map<String, BeanDefinition> rpcServiceBeanDefinitionMap = Maps.newConcurrentMap();
    private ClassLoader classLoader;
    private ApplicationContext applicationContext;

    public SpringRpcReferencePostProcessor(SpringRpcClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        // 解析完全部字段之后
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (StringUtils.isNotBlank(beanClassName)) {
                Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, classLoader);
                ReflectionUtils.doWithFields(beanClass, this::parseRpcReference);
            }
        }
        // 将bean定义信息注入到spring容器中
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
        rpcServiceBeanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (applicationContext.containsBean(beanName)) {
                log.warn("rpc bean already exists, beanName={}", beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
        });
    }

    private void parseRpcReference(Field field) {

        RpcReference rpcService = AnnotationUtils.getAnnotation(field, RpcReference.class);
        if (rpcService != null) {
            BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
            beanDefinitionBuilder.setInitMethodName("init");
            beanDefinitionBuilder.addPropertyValue("registryAddress", properties.getRegistryAddress());
            beanDefinitionBuilder.addPropertyValue("registryType", properties.getRegistryType());
            beanDefinitionBuilder.addPropertyValue("serviceInterface", field.getType());
            rpcServiceBeanDefinitionMap.put(field.getName(), beanDefinitionBuilder.getBeanDefinition());
        }
    }
}
