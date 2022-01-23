package com.github.shaylau.geekCourse.gateway;


import com.github.shaylau.geekCourse.gateway.handle.HttpGateWayRequestChanelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 网关服务端处理器（NettyService）
 */
public class GatewayNettyService {

    private static int GATE_WAY_PORT = 9090;

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(10);

        try {
            serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup);
            //配置选项
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            serverBootstrap.channel(NioServerSocketChannel.class);

            //boss 处理器（处理连接请求）
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));


            //worker 处理器(处理具体业务，这里处理网关请求)
            serverBootstrap.childHandler(new HttpGateWayRequestChanelInitializer());

            Channel channel = serverBootstrap.bind(GATE_WAY_PORT).sync().channel();
            System.out.println("开启网关请求服务，监听地址和端口为 http://127.0.0.1:" + GATE_WAY_PORT + "/");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }

    }
}
