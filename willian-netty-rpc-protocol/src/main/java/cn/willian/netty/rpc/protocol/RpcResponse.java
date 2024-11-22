package cn.willian.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:36:56
 */
@Data
public class RpcResponse implements Serializable {

    /**
     * 请求结果
     */
    private Object result;
    /**
     * 请求结果CODE
     */
    private Integer code;
    /**
     * 请求结果描述
     */
    private String message;
}
