package cn.willian.netty.rpc;

import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:36:33
 */
public class RpcClientProxy {

    public static <T> T getInstance(Class<T> clazz, String host, int port) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz},
            new RpcClientInvokeHandler(host, port));
    }
}
