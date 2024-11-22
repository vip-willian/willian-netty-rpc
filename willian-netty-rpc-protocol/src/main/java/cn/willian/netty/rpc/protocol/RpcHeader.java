package cn.willian.netty.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RPC请求头
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:34:01
 */
@Data
@AllArgsConstructor
public class RpcHeader {

    /**
     * 2个字节魔术
     */
    private Short magic;
    /**
     * 1个字节的序列化类型
     */
    private Byte serialType;
    /**
     * 1个字节的序列化类型
     */
    private Byte requestType;
    /**
     * 8个字节的请求ID
     */
    private Long requestId;
    /**
     * 4个字节的内容长度
     */
    private Integer length;


}
