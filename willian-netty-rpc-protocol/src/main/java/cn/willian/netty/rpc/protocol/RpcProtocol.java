package cn.willian.netty.rpc.protocol;

import lombok.Data;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:40:45
 */
@Data
public class RpcProtocol<T> {

    /**
     * 请求头内容
     */
    private RpcHeader header;
    /**
     * 请求体内容
     */
    private T body;
}
