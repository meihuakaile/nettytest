package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import pojo.UnixTime;

import java.util.List;

/**
 * TimeDecoder3
 *
 * @author chenliclchen
 * @date 17-10-25 下午3:53
 */
@Slf4j
public class TimeDecoder3 extends ByteToMessageDecoder{

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){
            return;
        }

        out.add(new UnixTime(in.readInt()));
    }
}
