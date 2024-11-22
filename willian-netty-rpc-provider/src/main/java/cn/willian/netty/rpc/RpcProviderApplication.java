package cn.willian.netty.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import cn.willian.netty.rpc.netty.RpcNettyServer;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:03:23
 */
@ComponentScan(basePackages = {"cn.willian.netty.rpc.service", "cn.willian.netty.rpc.spring"})
@SpringBootApplication
public class RpcProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(RpcProviderApplication.class, args);
        new RpcNettyServer("127.0.0.1", 8989).start();
    }
}
