package cn.willian.netty.rpc.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty远程调用服务
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:05:50
 */
@Slf4j
public class RpcNettyServer {

    private final String address;
    private final int port;

    public RpcNettyServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class).childHandler(new RpcNettyServerChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(address, port)).sync();
            log.info("rpc netty server started at {}:{}", address, port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("远程调用服务器连接被打断", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
