import handler.HelloClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * HelloClient
 *
 * @author chenliclchen
 * @date 17-10-25 下午6:08
 */
public class HelloClient {
    private String host;
    private int port;

    public HelloClient(String host, int port){
        this.host = host;
        this.port = port;
    }
    public void run() throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())), new ObjectEncoder(), new HelloClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = client.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloClient("127.0.0.1", 8080).run();
    }
}
