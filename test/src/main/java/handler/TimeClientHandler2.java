package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * TimeClientHandler2
 * 对TimeClientHandler 升级
 * @author chenliclchen
 * @date 17-10-25 下午2:49
 */
@Slf4j
public class TimeClientHandler2 extends ChannelHandlerAdapter {
    private ByteBuf buf;

    //完成任意初始化任务
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("four");
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("five");
        buf.release();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf time = (ByteBuf) msg;
        buf.writeBytes(time);
        time.release();

        if(buf.readableBytes() >= 4){
            long currentTime = (time.readUnsignedInt() - 2208988800L) * 1000L;
            log.info("received value: {}", currentTime);
            //关闭客户端
            ctx.close();
            log.info("three");
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("时间响应客户端出错 ", cause);
    }
}
