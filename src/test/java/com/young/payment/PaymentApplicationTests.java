package com.young.payment;

import com.young.payment.config.CCBPayConfig;
import com.young.payment.entity.AliPayEntity;
import com.young.payment.service.AliPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    AliPayService aliPayService;

    @Test
    public void ccbcBackTest() {
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

        URI uri = URI.create("http://127.0.0.1:12345");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //设置类型
        MediaType type = MediaType.parseMediaType("application/xml; charset=UTF-8");
        headers.setContentType(type);

        HttpEntity<String> formEntity = new HttpEntity<String>(requestXml.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, formEntity, String.class);
        System.err.println(result);
    }

    /**
     * 生成二维码接口
     * 商家生成二维码，用户使用支付宝扫一扫此二维码即可支付
     * <p>
     * 必传参数
     * 订单编号: out_trade_no （说明：此编号每次生成二维码连接时，必须唯一，前端生成，后续拿这个订单编号，进行轮训）
     * out_trade_no:123456789100022
     * 订单金额: total_amount （订单金额，用户扫描二维码时需要付款的金额）
     * total_amount:9899
     * 订单标题：subject (用户扫描二维码时，展示的商品名称)
     * subject:iPhoneXS
     **/
    @Test
    public void aliPayGetCode() {
        AliPayEntity aliPayEntity = new AliPayEntity();
        aliPayEntity.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        aliPayEntity.setTotal_amount("8999");
        aliPayEntity.setSubject("这是一个测试商品");
        aliPayService.aliPayGetCode(aliPayEntity);

    }

    /**
     * 商家扫描用户二维码
     * 必传字段：auth_code （扫描用户付款码获得的数据：示例 28763443825664394 ）
     * 必传字段：subject  订单标题
     * 必传字段：out_trade_no  订单编号（由前端生成，扫描后前端轮训，查看是否交易成功）
     *
     * @return
     */
    @Test
    public void aliPayOrder() {
        AliPayEntity aliPayEntity = new AliPayEntity();
        //这里输入扫码获取的付款码
        aliPayEntity.setAuth_code("284511624552751451");
        aliPayEntity.setSubject("这是一个测试商品");
        aliPayEntity.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        aliPayEntity.setTotal_amount("8999");
        aliPayService.aliPayOrder(aliPayEntity);
    }

}
