package cn.willian.netty.rpc.netty;

import cn.willian.netty.rpc.protocol.RequestHolder;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.protocol.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:15:57
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {

        Long requestId = msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> rpcFuture = RequestHolder.REQUEST_MAP.remove(requestId);
        rpcFuture.getPromise().setSuccess(msg.getBody());
    }
}
