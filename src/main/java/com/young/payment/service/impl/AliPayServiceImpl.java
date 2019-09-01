package com.young.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.young.payment.config.AliPayConfig;
import com.young.payment.entity.AliPayEntity;
import com.young.payment.service.AliPayService;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {

    private static final DefaultAlipayClient alipayClient;

    static {
        alipayClient = new DefaultAlipayClient(AliPayConfig.GATEWAY,
                AliPayConfig.APP_ID,
                AliPayConfig.APP_PRIVATE_KEY,
                "json",
                "UTF-8",
                AliPayConfig.ALIPAY_PUBLIC_KEY,
                "RSA2"); //获得初始化的AlipayClient
    }

    @Override
    public Object aliPayGetCode(AliPayEntity aliPayEntity) {

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); //创建API对应的request类

        request.setBizModel(aliPayEntity);
        AlipayTradePrecreateResponse response = null; //通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.err.println(request.toString());
        return response;
    }

    @Override
    public Object aliPayOrder(AliPayEntity aliPayEntity) {
        AlipayTradePayRequest request = new AlipayTradePayRequest(); //创建API对应的request类
        aliPayEntity.setScene("bar_code");
        aliPayEntity.setTimeout_express("2m");
        request.setBizModel(aliPayEntity);
//        request.setBizContent("{" +
//                "    \"out_trade_no\":\"20150320010101001\"," +
//                "    \"scene\":\"bar_code\"," +
//                "    \"auth_code\":\"28763443825664394\"," +//即用户在支付宝客户端内出示的付款码，使用一次即失效，需要刷新后再去付款
//                "    \"subject\":\"Iphone6 16G\"," +
//                "    \"store_id\":\"NJ_001\"," +
//                "    \"timeout_express\":\"2m\"," +
//                "    \"total_amount\":\"88.88\"" +
//                "  }"); //设置业务参数
        AlipayTradePayResponse response = null; //通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.err.println(request.toString());
        return response;
    }
}
