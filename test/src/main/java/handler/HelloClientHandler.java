package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * HelloClientHandler
 *
 * @author chenliclchen
 * @date 17-10-24 下午8:49
 */
@Slf4j
public class HelloClientHandler extends ChannelHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf received = (ByteBuf)msg;
        try {
            log.info("received value: {}", received.toString(CharsetUtil.UTF_8));
            ctx.close();
        }finally {
            received.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端出错", cause);
    }
}
