package cn.willian.netty.rpc.netty;

import cn.willian.netty.rpc.codec.RpcProtocolDecoder;
import cn.willian.netty.rpc.codec.RpcProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:11:11
 */
public class RpcNettyClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 添加消息内容解码器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0));
        pipeline.addLast(new RpcProtocolDecoder());
        pipeline.addLast(new RpcProtocolEncoder());
        // 解析请求返回数据
        pipeline.addLast(new RpcClientHandler());
    }
}
