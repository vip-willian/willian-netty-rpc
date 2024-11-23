package cn.willian.netty.rpc.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-23 12:40:15
 */
@Getter
@AllArgsConstructor
public enum RegistryTypeEnum {

    ZOOKEEPER((byte)0),
    NACOS((byte)1),;

    private final byte code;

    public static RegistryTypeEnum getRegistryType(byte code) {

        for (RegistryTypeEnum registryType : RegistryTypeEnum.values()) {
            if (registryType.getCode() == code) {
                return registryType;
            }
        }
        throw new RuntimeException("暂不支持的注册方式");
    }
}
