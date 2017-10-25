import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import handler.HelloServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * HelloServer
 *
 * @author chenliclchen
 * @date 17-10-25 下午5:50
 */
public class HelloServer {
    private int port;
    public HelloServer(int port){
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())), new ObjectEncoder(), new HelloServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port ;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new HelloServer(port).run();
    }
}
