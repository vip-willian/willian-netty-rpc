package cn.willian.netty.rpc.netty;

import cn.willian.netty.rpc.constants.RequestTypeEnum;
import cn.willian.netty.rpc.protocol.RpcHeader;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.protocol.RpcRequest;
import cn.willian.netty.rpc.protocol.RpcResponse;
import cn.willian.netty.rpc.spring.bean.RpcMediator;
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
        Object result = RpcMediator.getInstance().invoke(msg.getBody());
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setResult(result);
        rpcResponse.setMessage("success");
        response.setBody(rpcResponse);
        ctx.writeAndFlush(response);
    }
}
