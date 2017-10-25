package handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import pojo.User;

/**
 * HelloClientHandler
 *
 * @author chenliclchen
 * @date 17-10-25 下午8:11
 */
@Slf4j
public class HelloClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client");
        User user = (User) msg;
        log.info(user.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端出错", cause);
    }
}
