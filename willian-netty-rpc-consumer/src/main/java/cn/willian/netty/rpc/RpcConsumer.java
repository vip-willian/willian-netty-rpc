package cn.willian.netty.rpc;

import cn.willian.netty.rpc.api.IUserService;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:36:07
 */
public class RpcConsumer {

    public static void main(String[] args) {

        IUserService userService = RpcClientProxy.getInstance(IUserService.class, "127.0.0.1", 8989);
        String result = userService.saveUser("willian");
        System.out.println(result);
    }
}
