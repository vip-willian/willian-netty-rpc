package cn.willian.netty.rpc.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import cn.willian.netty.rpc.netty.RpcFuture;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:51:39
 */
public class RequestHolder {

    public static AtomicLong REQUEST_ID = new AtomicLong();
    public static Map<Long, RpcFuture<RpcResponse>> REQUEST_MAP = new ConcurrentHashMap<>();
}
