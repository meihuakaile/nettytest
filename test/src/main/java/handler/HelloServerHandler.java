package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * HelloServerHandler
 *
 * @author chenliclchen
 * @date 17-10-24 下午5:17
 */
@Slf4j
public class HelloServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        String name = "hello, world! \n";
        ByteBuf time = ctx.alloc().buffer(4);
        /*
         * NIO发送消息，需要调用java.nio.ByteBuffer.flip()。
         * java.nio.ByteBuffer.flip()是将缓存字节数组的指针设置为数组的开始序列即数组下标0。
         * 这样就可以从buffer开头，对该buffer进行遍历（读取）了。
         *
         * ByteBuf之所以没有这个方法因为有两个指针，
         * 一个对应读操作一个对应写操作。
         * 当你向ByteBuf里写入数据的时候写指针的索引就会增加，
         * 同时读指针的索引没有变化。
         * 读指针索引和写指针索引分别代表了消息的开始和结束。
         */
        time.writeBytes(name.getBytes());
        /*
         * ChannelHandlerContext.write()(和writeAndFlush())方法会返回一个ChannelFuture对象，
         * 一个ChannelFuture代表了一个还没有发生的I/O操作。
         * 这意味着任何一个请求操作都不会马上被执行，
         * 因为在Netty里所有的操作都是异步的。
         * 因此你需要在write()方法返回的ChannelFuture完成后调用close()方法，
         * 然后当他的写操作已经完成他会通知他的监听者。
         */
        ChannelFuture future = ctx.writeAndFlush(time);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                ctx.close();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("received value: {}", ((ByteBuf)msg).toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("time server error", cause);
    }
}
