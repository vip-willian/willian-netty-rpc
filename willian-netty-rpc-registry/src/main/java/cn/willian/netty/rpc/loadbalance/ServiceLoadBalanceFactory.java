package cn.willian.netty.rpc.loadbalance;

import cn.willian.netty.rpc.constants.LoadBalanceTypeEnum;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 13:03:04
 */
public class ServiceLoadBalanceFactory {

    public static  <T> IServiceLoadBalance<T> getServiceInstance(LoadBalanceTypeEnum balanceType) {

        IServiceLoadBalance<T> result = null;
        switch (balanceType) {
            case RANDOM:
                result = new RandomServiceLoadBalance<>();
                break;
            case POLLING:
                // TODO: 沐风 2024-11-23 13:10:11
                break;
            default:
                result = new RandomServiceLoadBalance<>();
                break;
        }
        return result;
    }
}
