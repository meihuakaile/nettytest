package handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import pojo.User;

/**
 * HelloServerHandler
 *
 * @author chenliclchen
 * @date 17-10-25 下午6:31
 */
@Slf4j
public class HelloServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server error",cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("server");
        ctx.writeAndFlush(new User("ll", 12));
    }
}
