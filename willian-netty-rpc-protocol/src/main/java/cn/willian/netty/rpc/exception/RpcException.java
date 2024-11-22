package cn.willian.netty.rpc.exception;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:32:52
 */
public class RpcException extends RuntimeException {

    public RpcException(String message) {
        super(message);
    }
}
