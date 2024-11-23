package cn.willian.netty.rpc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.willian.netty.rpc.api.IUserService;
import cn.willian.netty.rpc.spring.annotation.RpcReference;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 11:14:30
 */
@RestController
@RequestMapping("/rpc")
public class HelloRpcController {

    @RpcReference
    private IUserService userService;

    @GetMapping("/save")
    public String save() {

        return userService.saveUser("willian");
    }
}
