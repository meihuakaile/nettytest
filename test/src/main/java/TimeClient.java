import handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * TimeClient
 *
 * @author chenliclchen
 * @date 17-10-24 下午8:25
 */
public class TimeClient {
    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8080;
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
//            BootStrap和ServerBootstrap类似,不过他是对非服务端的channel而言，
//            比如客户端或者无连接传输模式的channel。
            Bootstrap server = new Bootstrap();
//            只指定了一个EventLoopGroup，那他就会即作为一个‘boss’线程，
//            也会作为一个‘worker’线程，尽管客户端不需要使用到‘boss’线程。
            server = server.group(workerGroup);
//            代替NioServerSocketChannel的是NioSocketChannel,这个类在客户端channel被创建时使用
            server = server.channel(NioSocketChannel.class);
//            不像在使用ServerBootstrap时需要用childOption()方法，因为客户端的SocketChannel没有父channel的概念。
            server.option(ChannelOption.SO_KEEPALIVE, true);
            server.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new HelloClientHandler());////
                    ch.pipeline().addLast(new TimeDecoder3(), new TimeClientHandler3());
                }
            });
//            用connect()方法代替了bind()方法。
            ChannelFuture future = server.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
}
