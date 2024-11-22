package cn.willian.netty.rpc.serialization;

/**
 * 序列化接口
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:21:05
 */
public interface ISerializer<T> {

    /**
     * 序列化
     *
     * @param t 原始对象
     * @return 序列化的字节数组
     */
    byte[] serialize(T t);

    /**
     * 序列化
     *
     * @param bytes 字节数组
     * @param clazz 反序列化的对象类型
     * @return 原始对象
     */
    T deserialize(byte[] bytes, Class<T> clazz);
}
