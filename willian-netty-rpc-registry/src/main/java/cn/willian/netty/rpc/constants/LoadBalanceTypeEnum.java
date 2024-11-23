package cn.willian.netty.rpc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 12:40:15
 */
@Getter
@AllArgsConstructor
public enum LoadBalanceTypeEnum {

    RANDOM((byte)0), POLLING((byte)1),;

    private final byte code;

    public static LoadBalanceTypeEnum getLoadBalance(byte code) {

        for (LoadBalanceTypeEnum loadBalanceType : LoadBalanceTypeEnum.values()) {
            if (loadBalanceType.getCode() == code) {
                return loadBalanceType;
            }
        }
        throw new RuntimeException("暂不支持的负载方式");
    }
}
