package cn.willian.netty.rpc.constants;

import cn.willian.netty.rpc.exception.RpcException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求类型枚举
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:19:15
 */
@Getter
@AllArgsConstructor
public enum RequestTypeEnum {

    REQUEST((byte)0), RESPONSE((byte)1),;

    private final byte code;

    public static RequestTypeEnum getRequestType(byte code) {

        for (RequestTypeEnum requestType : RequestTypeEnum.values()) {
            if (requestType.getCode() == code) {
                return requestType;
            }
        }
        throw new RpcException("暂不支持的请求方式");
    }
}
