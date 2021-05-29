package com.yr.net.app.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Description 向指定 URL 发送POST方法的请求
 * @return 远程资源的响应结果
 * @Author dengbp
 * @Date 14:02 2019-12-18
 **/
@Slf4j
public class HttpUtil {

    private static int TIMEOUT_MILLIS = 5000;
    /**
     * Description todo
     * @param url
     * @param operateType
     * @param param
     * @return String
     * @Author dengbp
     * @Date 18:14 2020-11-05
     **/
    public static String sendPost(String url, String operateType, String param) throws Exception{
        log.info("http请求url[{}],操作类型[{}],参数[{}]",url,operateType,param);
        StringBuilder result = new StringBuilder();
        OutputStreamWriter out = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        int response_code = 500;
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            /** 5秒 */
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(operateType.toUpperCase());
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0  ( compatible )");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.connect();
            if (StringUtils.isNotBlank(param)) {
                out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                out.write(param);
                out.flush();
            }
            is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str=new String(str.getBytes(),"UTF-8");
                result.append(str);
            }
            response_code = conn.getResponseCode();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        log.info("连接器服务响应内容:{}",result.toString());
        return result.toString();
    }



    private final static String UTF8 = StandardCharsets.UTF_8.displayName();

    private static CloseableHttpClient httpClient;

    static {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000).setSocketTimeout(4000).setExpectContinueEnabled(true).build();
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(300);
        pool.setDefaultMaxPerRoute(50);
        HttpRequestRetryHandler retryHandler = (IOException exception, int executionCount, HttpContext context) -> {
            if (executionCount > 1) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                log.info("[NoHttpResponseException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
                return true;
            } else if (exception instanceof SocketException) {
                log.info("[SocketException has retry request:" + context.toString() + "][executionCount:" + executionCount + "]");
                return true;
            }
            return false;
        };
        httpClient = HttpClients.custom().setConnectionManager(pool).setDefaultRequestConfig(requestConfig).setRetryHandler(retryHandler).build();
    }
    /**
     * 设置请求头信息
     *
     * @param headers
     * @param request
     * @return
     */
    private static void setHeaders(Map<String, Object> headers, HttpRequest request) {
        if (null != headers && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }
    }
    /**
     * (微信支付专用)发送post请求请求体为xml
     *
     * @param url
     * @param xml
     * @param headers
     * @return
     */
    public static String sendPostXml(String url, String xml, Map<String, Object> headers) {
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            setHeaders(headers, httpPost);
            StringEntity entity = new StringEntity(xml, StandardCharsets.UTF_8);
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseData = response.getEntity();
            result = EntityUtils.toString(responseData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
