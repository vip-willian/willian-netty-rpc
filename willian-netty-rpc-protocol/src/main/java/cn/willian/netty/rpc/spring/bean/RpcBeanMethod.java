package cn.willian.netty.rpc.spring.bean;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 10:27:11
 */
@Data
@AllArgsConstructor(staticName = "of")
public class RpcBeanMethod {

    private Object bean;
    private Method method;
}
