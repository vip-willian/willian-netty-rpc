package cn.willian.netty.rpc.codec;

import cn.willian.netty.rpc.protocol.RpcHeader;
import cn.willian.netty.rpc.protocol.RpcProtocol;
import cn.willian.netty.rpc.serialization.ISerializer;
import cn.willian.netty.rpc.serialization.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC协议编码器
 *
 * @author <a href="mailto:willian.wyann@gmail.com">willian</a>
 * @since 2024-11-22 21:42:44
 */
public class RpcProtocolEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> data, ByteBuf byteBuf) throws Exception {

        RpcHeader header = data.getHeader();
        // 写入2个字节的魔数
        byteBuf.writeShort(header.getMagic());
        // 写入1个字节的序列化类型
        byteBuf.writeByte(header.getSerialType());
        // 写入1个字节的请求类型
        byteBuf.writeByte(header.getRequestType());
        // 写入8个字节的请求ID
        byteBuf.writeLong(header.getRequestId());

        ISerializer<Object> serializer = SerializerFactory.getSerializer(header.getSerialType());
        byte[] content = serializer.serialize(data.getBody());
        // 写入长度
        byteBuf.writeInt(content.length);
        // 写入具体请求内容
        byteBuf.writeBytes(content);
    }
}
