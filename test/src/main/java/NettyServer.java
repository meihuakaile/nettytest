import handler.HelloServerHandler;
import handler.TimeEncoder;
import handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * NettyServer
 * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
 * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。
 * 在这个例子中我们实现了一个服务端的应用，
 * 因此会有2个NioEventLoopGroup会被使用。
 * 第一个经常被叫做‘boss’，用来接收进来的连接。
 * 第二个经常被叫做‘worker’，用来处理已经被接收的连接，
 * 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
 * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
 * 并且可以通过构造函数来配置他们的关系。
 *
 * @author chenliclchen
 * @date 17-10-23 下午4:07
 */
@Slf4j
public class NettyServer {
    private int port;

    public NettyServer(int port){
        this.port = port;
    }
    public void run(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        log.info("{} server in ready start...", port);
        try {
//            ServerBootstrap 是一个启动NIO服务的辅助启动类
            ServerBootstrap server = new ServerBootstrap();
            server = server.group(bossGroup, workerGroup);
//            我们指定使用NioServerSocketChannel类来举例说明一个新的Channel如何接收进来的连接。
            server = server.channel(NioServerSocketChannel.class);
//            用来处理一个最近的已经接收的Channel。
//            ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel.
            server = server.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                    socketChannel.pipeline().addLast(new DiscardServerHandler());
//                    socketChannel.pipeline().addLast(new ResponseServerHandler());
//                    socketChannel.pipeline().addLast(new HelloServerHandler());
                    socketChannel.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
                }
            });
//            设置这里指定的通道实现的配置参数。  设置tcp缓冲区
            server = server.option(ChannelOption.SO_BACKLOG, 128);
//            option()是提供给NioServerSocketChannel用来接收进来的连接。
//            childOption()是提供给由父管道ServerChannel接收到的连接
            server = server.childOption(ChannelOption.SO_KEEPALIVE, true);
//            绑定端口并启动去接收进来的连接
            ChannelFuture future = server.bind(port).sync();
//            这里会一直等待，直到socket被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("服务器开启出错", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new NettyServer(port).run();
    }
//    http://blog.csdn.net/tjbsl/article/details/51038947
//    http://ifeve.com/netty5-user-guide/
//    http://netty.io/community.html
//    http://blog.csdn.net/kobejayandy/article/details/11493717
}
