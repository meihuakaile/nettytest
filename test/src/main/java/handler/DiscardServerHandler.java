package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * DiscardServerHandler
 * 服务端处理通道.这里只是打印一下请求的内容，并不对请求进行任何的响应
 * DiscardServerHandler 继承自 ChannelHandlerAdapter，
 * 这个类实现了ChannelHandler接口，
 * ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。
 * 现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 * @author chenliclchen
 * @date 17-10-23 下午4:31
 */

@Slf4j
public class DiscardServerHandler extends ChannelHandlerAdapter{
    /*
     * 每当从客户端收到新的数据时，
     * 这个方法会在收到消息时被调用，
     * 这个例子中，收到的消息的类型是ByteBuf
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf received = (ByteBuf) msg;
            log.info("value: {}", received.toString(CharsetUtil.UTF_8));
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }

    //发生异常时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        关闭连接
        log.error("丢弃服务器发现异常", cause);
        ctx.flush();
    }
}
