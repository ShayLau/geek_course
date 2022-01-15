package com.github.shaylau.geekCourse.httpclient;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

/**
 * Http Client Example
 *
 * @author ShayLau
 * @date 2022/1/14 20:346 PM
 */
public class HttpClientExample {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpUrl = "http://localhost:8081";
        HttpGet httpGet = new HttpGet(httpUrl);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("http 请求响应码：" + httpResponse.getCode());
            HttpEntity httpEntity = httpResponse.getEntity();
            long contentLength = httpEntity.getContentLength();
            byte[] responseBody = new byte[(int) contentLength];
            httpEntity.getContent().read(responseBody);
            System.out.println("响应内容长度：" + contentLength + ",响应内容：" + new String(responseBody));
            EntityUtils.consume(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
