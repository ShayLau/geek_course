package com.github.shaylau.geekCourse.gateway.handle;

import com.github.shaylau.geekCourse.gateway.handle.inbound.HttpGateWayInboundHandle;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 处理网关请求链接
 */
public class HttpGateWayRequestChanelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //channel 管道增加处理器

        channelPipeline.addLast(new HttpServerCodec());
        //p.addLast(new HttpServerExpectContinueHandler());
        channelPipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        channelPipeline.addLast(new HttpGateWayInboundHandle());
    }
}
