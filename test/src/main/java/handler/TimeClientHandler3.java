package handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import pojo.UnixTime;

/**
 * TimeClientHandler3
 *
 * @author chenliclchen
 * @date 17-10-25 下午3:53
 */
@Slf4j
public class TimeClientHandler3 extends ChannelHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime time = (UnixTime) msg;
        log.info("received value: {}", time.toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
