package cn.willian.netty.rpc.service;

import cn.willian.netty.rpc.api.IUserService;
import cn.willian.netty.rpc.spring.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 22:01:59
 */
@Slf4j
@RpcService
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(String userName) {

        log.info("begin save user info");
        return "save user success : " + userName;
    }
}
