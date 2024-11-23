package cn.willian.netty.rpc.spring.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.Maps;

import cn.willian.netty.rpc.protocol.RpcRequest;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:28:47
 */
public class RpcMediator {

    private static volatile RpcMediator mediator = null;
    public final Map<String, RpcBeanMethod> RPC_METHOD = Maps.newConcurrentMap();

    private RpcMediator() {}

    public static RpcMediator getInstance() {

        if (mediator == null) {
            synchronized (RpcMediator.class) {
                if (mediator == null) {
                    mediator = new RpcMediator();
                }
            }
        }
        return mediator;
    }

    public Object invoke(RpcRequest rpcRequest) {

        String key = rpcRequest.getClassName() + "." + rpcRequest.getMethodName();
        RpcBeanMethod rpcBeanMethod = RPC_METHOD.get(key);
        if (Objects.isNull(rpcBeanMethod)) {
            return null;
        }
        try {
            Method targetMethod = rpcBeanMethod.getMethod();
            return targetMethod.invoke(rpcBeanMethod.getBean(), rpcRequest.getMethodArgs());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
