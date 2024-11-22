package cn.willian.netty.rpc.serialization;

import com.alibaba.fastjson2.JSON;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:22:46
 */
public class JsonSerializer<T> implements ISerializer<T> {

    @Override
    public byte[] serialize(T t) {

        return JSON.toJSONString(t).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
