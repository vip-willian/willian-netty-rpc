package cn.willian.netty.rpc.netty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.willian.netty.rpc.constants.RequestTypeEnum;
import cn.willian.netty.rpc.protocol.RpcHeader;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.protocol.RpcRequest;
import cn.willian.netty.rpc.protocol.RpcResponse;
import cn.willian.netty.rpc.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:15:57
 */
public class RpcInvokeHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {

        RpcHeader header = msg.getHeader();
        header.setRequestType(RequestTypeEnum.RESPONSE.getCode());

        RpcProtocol<RpcResponse> response = new RpcProtocol<>();
        response.setHeader(header);
        // 执行调用
        Object result = this.invoke(msg.getBody());
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setResult(result);
        rpcResponse.setMessage("success");
        response.setBody(rpcResponse);
        ctx.writeAndFlush(response);
    }

    private Object invoke(RpcRequest body) {

        // 加载这个类
        String className = body.getClassName();
        try {
            Class<?> targetClazz = Class.forName(className);
            Method targetMethod = targetClazz.getDeclaredMethod(body.getMethodName(), body.getMethodTypes());
            Object service = SpringBeanManager.getBean(targetClazz);
            return targetMethod.invoke(service, body.getMethodArgs());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
