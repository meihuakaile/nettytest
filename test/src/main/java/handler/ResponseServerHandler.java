package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * ResponseServerHandler
 *
 * @author chenliclchen
 * @date 17-10-24 上午10:20
 */
@Slf4j
public class ResponseServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf received = (ByteBuf) msg;
        log.info("received value: {}", received.toString(CharsetUtil.UTF_8));
        ctx.write(msg);
//        请注意，这里我并不需要显式的释放，因为在进入的时候netty已经自动释放
    }

    /*
     * ctx.write(Object)方法不会使消息写入到通道上，
     * 他被缓冲在了内部，你需要调用ctx.flush()方法来把缓冲区中数据强行输出。
     * 或者你可以在channelRead方法中用更简洁的cxt.writeAndFlush(msg)以达到同样的目的
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("接受数据时出错 ");
        ctx.close();
    }
}
