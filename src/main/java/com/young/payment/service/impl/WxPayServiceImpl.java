package com.young.payment.service.impl;

import com.young.payment.config.WXConfig;
import com.young.payment.config.WXPay;
import com.young.payment.entity.R;
import com.young.payment.entity.WxPayEntity;
import com.young.payment.service.WxPayService;
import com.young.payment.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    HttpServletRequest request;

    @Override
    public R micropay(WxPayEntity wxPayEntity) {
        WXConfig config = null;
        WXPay wxpay = null;
        try {
            config = new WXConfig();
            wxpay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("wxpay init error");
            return R.ERROR(e.getMessage());
        }
        String ip = IpUtil.getIpAddress();
        Map<String, String> data = new HashMap<String, String>();
        data.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        data.put("body", wxPayEntity.getBody());
        data.put("out_trade_no", UUID.randomUUID().toString().replace("-", ""));
        data.put("total_fee", wxPayEntity.getTotal_fee());
        data.put("spbill_create_ip", ip);
        data.put("auth_code", wxPayEntity.getAuth_code());
        Map<String, String> result = null;
        try {
            result = wxpay.microPay(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("调用微信支付失败：{}", e.getMessage());
        }
        return R.SUCCESS(result);
    }
}
