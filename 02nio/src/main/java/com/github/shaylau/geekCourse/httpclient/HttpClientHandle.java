package com.github.shaylau.geekCourse.httpclient;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Http Client Example
 *
 * @author ShayLau
 * @date 2022/1/22 20:36 PM
 */
public class HttpClientHandle {


    /**
     * 发起Http 请求处理
     *
     * @param fullHttpRequest
     * @param ctx
     * @param routeUrl
     */
    public void fetchHttpRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String routeUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpMethod httpMethod = fullHttpRequest.method();
            if (httpMethod.equals(HttpMethod.GET)) {
                HttpGet httpGet = new HttpGet(routeUrl);
                httpResponse = httpClient.execute(httpGet);
            } else if (httpMethod.equals(HttpMethod.POST)) {
                HttpPost httpPost = new HttpPost(routeUrl);
                httpResponse = httpClient.execute(httpPost);
            }
            System.out.println("http 请求响应码：" + httpResponse.getCode());

            HttpEntity httpEntity = httpResponse.getEntity();
            long contentLength = httpEntity.getContentLength();
            byte[] responseBody = new byte[(int) contentLength];
            httpEntity.getContent().read(responseBody);
            String response = new String(responseBody);
            System.out.println("响应内容长度：" + contentLength + ",响应内容：" + response);
            EntityUtils.consume(httpEntity);
            //包装返回结果
            wrapperResponse(fullHttpRequest, response, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 包装结果并响应
     *
     * @param fullHttpRequest
     * @param response
     * @param ctx
     */
    public void wrapperResponse(FullHttpRequest fullHttpRequest, String response, ChannelHandlerContext ctx) {
        FullHttpResponse httpResponse = null;
        try {
            ByteBuf responseByteBuf = Unpooled.wrappedBuffer(response.getBytes("UTF-8"));
            httpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, responseByteBuf);
            httpResponse.headers().set("Content-Type", "application/json");
            httpResponse.headers().setInt("Content-Length", response.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fullHttpRequest != null) {
                if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }

}
