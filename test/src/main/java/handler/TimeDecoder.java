package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TimeDecoder
 *
 * ByteToMessageDecoder是ChannelHandler的一个实现类，他可以在处理数据拆分的问题上变得很简单。
 * 每当有新数据接收的时候，ByteToMessageDecoder都会调用decode()方法来处理内部的那个累积缓冲。
 *
 * @author chenliclchen
 * @date 17-10-25 下午3:30
 */
@Slf4j
public class TimeDecoder extends ByteToMessageDecoder{
    // Decode()方法可以决定当累积缓冲里没有足够数据时不可以往out对象里放任意数据。
    // 当有更多的数据被接收了，ByteToMessageDecoder会再一次调用decode()方法。
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
      log.info("six");
        if(in.readableBytes() < 4){
          return;
      }
      out.add(in.readBytes(4));
        log.info("seven");
    }
}
