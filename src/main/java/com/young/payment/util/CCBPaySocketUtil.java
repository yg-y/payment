package com.young.payment.util;

import com.young.payment.config.CCBPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
public class CCBPaySocketUtil {

    public static String sendCCBPaySocket(String xml) {
        if (StringUtils.isEmpty(xml)) {
            log.info("xml info is null");
            return null;
        }
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
            pw.write(xml);
            pw.flush();
            socket.shutdownOutput();
            //接收服务器的相应
            String reply = null;
            //System.out.println("结果集"+br);
            while (!((reply = br.readLine()) == null)) {
                so = so + reply;
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

    /**
     * 获取退款xml
     *
     * @param requestSn
     * @param orderId
     * @param price
     * @return
     */
    public static String getXmlInfo(String requestSn, String orderId, String price) {
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(price)) {
            log.info("订单号和价格不能为空");
            return null;
        }
        StringBuffer requestXml = new StringBuffer();
        requestXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>")
                .append("<TX>")
                .append("<REQUEST_SN>").append(requestSn).append("</REQUEST_SN>")
                .append("<CUST_ID>").append(CCBPayConfig.MERCHANTID).append("</CUST_ID>")
                .append("<USER_ID>002</USER_ID>")
                .append("<PASSWORD>lmy19950714</PASSWORD>")
                .append("<TX_CODE>5W1004</TX_CODE>")
                .append("<LANGUAGE>CN</LANGUAGE>")
                .append("<TX_INFO>")
                .append("<MONEY>").append(price).append("</MONEY>")
                .append("<ORDER>").append(orderId).append("</ORDER>")
                .append("</TX_INFO>")
                .append("<SIGN_INFO></SIGN_INFO>")
                .append("<SIGNCERT></SIGNCERT>")
                .append("</TX>");

        return String.valueOf(requestXml);
    }
}
