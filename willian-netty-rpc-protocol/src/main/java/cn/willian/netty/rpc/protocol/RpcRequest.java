package cn.willian.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:36:56
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 请求类名
     */
    private String className;
    /**
     * 请求方法名
     */
    private String methodName;
    /**
     * 请求方法类型
     */
    private Class<?>[] methodTypes;
    /**
     * 请求参数
     */
    private Object[] methodArgs;
}
