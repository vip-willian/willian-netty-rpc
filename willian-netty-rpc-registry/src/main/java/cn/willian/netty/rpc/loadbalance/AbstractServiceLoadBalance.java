package cn.willian.netty.rpc.loadbalance;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 13:06:03
 */
public abstract class AbstractServiceLoadBalance<T> implements IServiceLoadBalance<T> {

    @Override
    public T select(List<T> serviceList) {

        if (Objects.isNull(serviceList) || serviceList.isEmpty()) {
            return null;
        }
        if (serviceList.size() == 1) {
            return serviceList.get(0);
        }
        return doSelect(serviceList);
    }

    protected abstract T doSelect(List<T> serviceList);
}
