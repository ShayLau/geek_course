package com.github.shaylau.geekCourse.gateway.handle.inbound;

import com.github.shaylau.geekCourse.gateway.filter.DefaultHttpGateWayFilterImpl;
import com.github.shaylau.geekCourse.gateway.filter.HttpGateWayFilter;
import com.github.shaylau.geekCourse.gateway.route.DefaultHttpGateWayRouteImpl;
import com.github.shaylau.geekCourse.gateway.route.HttpGateWayRoute;
import com.github.shaylau.geekCourse.httpclient.HttpClientHandle;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * http GateWay 网关入站处理器
 */
public class HttpGateWayInboundHandle extends ChannelInboundHandlerAdapter {

    /**
     * 路由
     */
    private HttpGateWayRoute httpGateWayRoute = new DefaultHttpGateWayRouteImpl();

    /**
     * 路由
     */
    private HttpGateWayFilter httpGateWayFilter = new DefaultHttpGateWayFilterImpl();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        super.channelRead(ctx, msg);

        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        System.out.println("读取到Http网关消息" + fullHttpRequest.uri());
        String uri = fullHttpRequest.uri();

        //网关路由（找到服务地址）
        String routeUrl = httpGateWayRoute.route(uri);

        //网关过滤
        httpGateWayFilter.filter(fullHttpRequest);


        //HTTP 请求
        HttpClientHandle httpClientHandle = new HttpClientHandle();
        if (routeUrl.startsWith("http")) {
            //发起Http Client调用
            httpClientHandle.fetchHttpRequest(fullHttpRequest, ctx, routeUrl);
        } else {
            httpClientHandle.wrapperResponse(fullHttpRequest, routeUrl, ctx);
        }
        System.out.println("处理结束");
        ctx.close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
