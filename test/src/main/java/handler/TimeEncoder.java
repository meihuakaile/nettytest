package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import pojo.UnixTime;

/**
 * TimeEncoder
 *
 * @author chenliclchen
 * @date 17-10-25 下午4:17
 */
@Slf4j
public class TimeEncoder extends MessageToByteEncoder<UnixTime>{
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        log.info("eight");
        out.writeInt(msg.getValue());
        log.info("nine");
    }
}
