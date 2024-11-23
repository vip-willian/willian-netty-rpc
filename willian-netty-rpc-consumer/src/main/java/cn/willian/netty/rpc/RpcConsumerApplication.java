package cn.willian.netty.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:36:07
 */
@ComponentScan(basePackages = {"cn.willian.netty.rpc.controller", "cn.willian.netty.rpc.spring.annotation",
    "cn.willian.netty.rpc.spring.reference"})
@SpringBootApplication
public class RpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args);
    }
}
