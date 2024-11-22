package cn.willian.netty.rpc.serialization;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:22:46
 */
@Slf4j
public class JavaSerializer<T> implements ISerializer<T> {

    @Override
    public byte[] serialize(T t) {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(bos)) {
            outputStream.writeObject(t);
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("序列化失败，请检查数据！！！", e);
        }
        return null;
    }

    @Override
    public T deserialize(byte[] bytes, Class<T> clazz) {

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream inputStream = new ObjectInputStream(bis)) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("反序列化失败，请检查数据！！！", e);
        }
        return null;
    }
}
