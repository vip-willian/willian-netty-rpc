package cn.willian.netty.rpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 13:08:01
 */
public class RandomServiceLoadBalance<T> extends AbstractServiceLoadBalance<T> {

    @Override
    protected T doSelect(List<T> serviceList) {

        int size = serviceList.size();
        int index = new Random().nextInt(size);
        return serviceList.get(index);
    }
}
