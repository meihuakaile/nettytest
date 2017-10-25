package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * TimeClientHandler
 *
 * @author chenliclchen
 * @date 17-10-25 下午2:49
 */
@Slf4j
public class TimeClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf time = (ByteBuf) msg;
        try {
            long currentTime = (time.readUnsignedInt() - 2208988800L) * 1000L;
            log.info("received value: {}", currentTime);
            //关闭连接
            ctx.close();
            log.info("three");
        }finally {
            time.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("时间响应客户端出错 ", cause);
    }
}
