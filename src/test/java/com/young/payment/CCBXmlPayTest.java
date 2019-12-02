package com.young.payment;

import com.alibaba.fastjson.JSONObject;
import com.young.payment.config.CCBPayConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class CCBXmlPayTest {

    @Test
    public void payByXml() throws Exception {
        String orderId = "1574349649351";
        String price = "0.01";
        StringBuffer requestXml = new StringBuffer();
        requestXml.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>")
                .append("<TX>")
                .append("<REQUEST_SN>").append(System.currentTimeMillis()).append("</REQUEST_SN>")
                .append("<CUST_ID>").append(CCBPayConfig.MERCHANTID).append("</CUST_ID>")
                .append("<USER_ID>002</USER_ID>")
                .append("<PASSWORD>lmy19950714</PASSWORD>")
                .append("<TX_CODE>5W1004</TX_CODE>")
                .append("<LANGUAGE>CN</LANGUAGE>")
                .append("<TX_INFO>")
                .append("<MONEY>").append(price).append("</MONEY>")
                .append("<ORDER>").append(orderId).append("</ORDER>")
                .append("<REFUND_CODE>").append(System.currentTimeMillis()).append("</REFUND_CODE>")
                .append("</TX_INFO>")
                .append("<SIGN_INFO></SIGN_INFO>")
                .append("<SIGNCERT></SIGNCERT>")
                .append("</TX>");

        System.err.println(requestXml);
        String result = null;

        result = this.httpRequest("http://127.0.0.1:12345", String.valueOf(requestXml));
        System.err.println(result);
        System.err.println(JSONObject.parse(result));

    }


    private HttpClient client;

    /**
     * http 请求
     *
     * @param data
     * @return
     * @throws Exception
     */
    private static String httpRequest(String url, String data) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create()
                .build();

        HttpPost httpPost = new HttpPost(url);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "application/xml; charset=UTF-8");
        // 设置请求头消息User-Agent
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
            ((CloseableHttpClient) httpClient).close();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    @Test
    public String sockteCCBPay() {
        String so = "";
        try {
            //建立客户端socket连接，指定服务器位置及端口
            Socket socket = new Socket("127.0.0.1", 12345);
            //得到socket读写流
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            //输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //对socket进行读写操作
            String info = getXmlInfo();
            pw.write(info);
            pw.flush();
            socket.shutdownOutput();
            //接收服务器的相应
            String reply = null;
            //System.out.println("结果集"+br);
            while (!((reply = br.readLine()) == null)) {
                so = so + reply + "\n";
            }
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return so;
    }


    public String getXmlInfo() {
        String orderId = "1574349649351";
        String price = "0.01";
        StringBuffer requestXml = new StringBuffer();
        requestXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>")
                .append("<TX>")
                .append("<REQUEST_SN>").append(System.currentTimeMillis()).append("</REQUEST_SN>")
                .append("<CUST_ID>").append(CCBPayConfig.MERCHANTID).append("</CUST_ID>")
                .append("<USER_ID>002</USER_ID>")
                .append("<PASSWORD>lmy19950714</PASSWORD>")
                .append("<TX_CODE>5W1004</TX_CODE>")
                .append("<LANGUAGE>CN</LANGUAGE>")
                .append("<TX_INFO>")
                .append("<MONEY>").append(price).append("</MONEY>")
                .append("<ORDER>").append(orderId).append("</ORDER>")
                .append("<REFUND_CODE>").append(System.currentTimeMillis()).append("</REFUND_CODE>")
                .append("</TX_INFO>")
                .append("<SIGN_INFO></SIGN_INFO>")
                .append("<SIGNCERT></SIGNCERT>")
                .append("</TX>");

        return String.valueOf(requestXml);
    }

}
