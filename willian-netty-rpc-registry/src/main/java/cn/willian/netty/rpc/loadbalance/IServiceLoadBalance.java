package cn.willian.netty.rpc.loadbalance;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 13:02:01
 */
public interface IServiceLoadBalance<T> {

    /**
     * 负载均衡选择服务
     * @param serviceList 服务列表
     * @return 某一个服务
     */
    T select(List<T> serviceList);
}
