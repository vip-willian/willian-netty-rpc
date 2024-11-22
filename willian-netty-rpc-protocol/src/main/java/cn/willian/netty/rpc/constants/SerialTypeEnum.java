package cn.willian.netty.rpc.constants;

import cn.willian.netty.rpc.exception.RpcException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 序列化方式枚举
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:19:15
 */
@Getter
@AllArgsConstructor
public enum SerialTypeEnum {

    JAVA((byte) 0),
    JSON((byte) 1),
    ;

    private final byte code;

    public static SerialTypeEnum getSerialType(byte code) {

        for (SerialTypeEnum serialTypeEnum : SerialTypeEnum.values()) {
            if (serialTypeEnum.getCode() == code) {
                return serialTypeEnum;
            }
        }
        throw new RpcException("暂不支持的序列化方式");
    }
}
