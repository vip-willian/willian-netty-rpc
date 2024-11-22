package cn.willian.netty.rpc.codec;

import cn.willian.netty.rpc.constants.RpcConstants;
import cn.willian.netty.rpc.protocol.RpcHeader;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.serialization.ISerializer;
import cn.willian.netty.rpc.serialization.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * RPC协议解码器
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:42:44
 */
@Slf4j
public class RpcProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < RpcConstants.LENGTH) {
            log.error("rpc request param invalid");
            return;
        }
        // 先读取2个字节的魔数
        short magic = byteBuf.readShort();
        if (RpcConstants.RPC_MAGIC != magic) {
            log.error("rpc invalid magic");
            return;
        }
        byte serialType = byteBuf.readByte();
        byte requestType = byteBuf.readByte();
        long requestId = byteBuf.readLong();
        int length = byteBuf.readInt();

        RpcHeader rpcHeader = new RpcHeader(magic, serialType, requestType, requestId, length);
        RpcProtocol<Object> rpcProtocol = new RpcProtocol<>();
        rpcProtocol.setHeader(rpcHeader);

        if (length > 0) {
            // 将消息体内容读取到byte中
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);

            // 将byte数组进行反序列化
            ISerializer<Object> serializer = SerializerFactory.getSerializer(serialType);
            Object result = serializer.deserialize(bytes, Object.class);
            rpcProtocol.setBody(result);
        }
        list.add(rpcProtocol);
    }
}
