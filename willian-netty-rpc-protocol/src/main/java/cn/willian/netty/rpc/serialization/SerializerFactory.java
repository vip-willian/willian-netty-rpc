package cn.willian.netty.rpc.serialization;

import cn.willian.netty.rpc.constants.SerialTypeEnum;
import cn.willian.netty.rpc.exception.RpcException;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:30:08
 */
public class SerializerFactory {

    public static <T> ISerializer<T> getSerializer(byte serializerType) {

        switch (SerialTypeEnum.getSerialType(serializerType)) {
            case JSON:
                return new JsonSerializer<>();
            case JAVA:
                return new JavaSerializer<>();
        }
        throw new RpcException("暂不支持的序列化方式");
    }
}
