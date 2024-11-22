package cn.willian.netty.rpc.netty;

import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.protocol.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty远程调用服务
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:05:50
 */
@Slf4j
public class RpcNettyClient {

    private final NioEventLoopGroup group;
    private final Bootstrap bootstrap;
    private final String host;
    private final int port;

    public RpcNettyClient(String host, int port) {

        this.host = host;
        this.port = port;
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new RpcNettyClientChannelInitializer());
    }

    public void sendRequest(RpcProtocol<RpcRequest> rpcRequest) {

        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.addListener((ChannelFutureListener)future -> {
                if (future.isSuccess()) {
                    log.info("rpc netty server connect success at {}:{}", host, port);
                } else {
                    log.info("rpc netty server connect failed at {}:{}", host, port);
                    future.cause().printStackTrace();
                    group.shutdownGracefully();
                }
            });
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(rpcRequest);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
