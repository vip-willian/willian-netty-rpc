package cn.willian.netty.rpc.spring.reference;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.willian.netty.rpc.constants.RequestTypeEnum;
import cn.willian.netty.rpc.constants.RpcConstants;
import cn.willian.netty.rpc.constants.SerialTypeEnum;
import cn.willian.netty.rpc.netty.RpcFuture;
import cn.willian.netty.rpc.netty.RpcNettyClient;
import cn.willian.netty.rpc.protocol.RequestHolder;
import cn.willian.netty.rpc.protocol.RpcHeader;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.protocol.RpcRequest;
import cn.willian.netty.rpc.protocol.RpcResponse;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:38:52
 */
public class RpcClientInvokeHandler implements InvocationHandler {

    private final String serviceAddress;
    private final Integer servicePort;

    public RpcClientInvokeHandler(String serviceAddress, Integer servicePort) {
        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        RpcHeader rpcHeader = new RpcHeader(RpcConstants.RPC_MAGIC, SerialTypeEnum.JAVA.getCode(),
            RequestTypeEnum.REQUEST.getCode(), requestId, 0);

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setMethodTypes(method.getParameterTypes());
        rpcRequest.setMethodArgs(args);

        RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
        rpcProtocol.setHeader(rpcHeader);
        rpcProtocol.setBody(rpcRequest);

        RpcNettyClient rpcNettyClient = new RpcNettyClient(serviceAddress, servicePort);
        rpcNettyClient.sendRequest(rpcProtocol);

        RpcFuture<RpcResponse> rpcFuture = new RpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId, rpcFuture);

        return rpcFuture.getPromise().get().getResult();
    }
}
