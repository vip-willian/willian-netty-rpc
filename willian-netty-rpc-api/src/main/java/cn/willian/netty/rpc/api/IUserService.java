package cn.willian.netty.rpc.api;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:10:00
 */
public interface IUserService {

    /**
     * 保存用户信息
     *
     * @param userName 用户名
     * @return 保存响应结果
     */
    String saveUser(String userName);
}
