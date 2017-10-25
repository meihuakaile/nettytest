package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import pojo.UnixTime;

/**
 * TimeServerHandler
 *
 * @author chenliclchen
 * @date 17-10-25 下午2:37
 */
@Slf4j
public class TimeServerHandler extends ChannelHandlerAdapter{
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
//        final ByteBuf time = ctx.alloc().buffer(4);
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//
//        final ChannelFuture future = ctx.writeAndFlush(time);

        final ChannelFuture future = ctx.writeAndFlush(new UnixTime());
        log.info("ten");
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                ctx.close();
                log.info("two");
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("one");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("时间响应服务器端出错： ", cause);
    }
}
