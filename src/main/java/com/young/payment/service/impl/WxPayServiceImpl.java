package com.young.payment.service.impl;

import com.young.payment.config.WXConfig;
import com.young.payment.config.WXPay;
import com.young.payment.entity.R;
import com.young.payment.entity.WxPayEntity;
import com.young.payment.service.WxPayService;
import com.young.payment.util.IpUtil;
import com.young.payment.util.MD5Util;
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
            log.info("调用微信支付失败：{}", result);
            return R.ERROR(result);
        }
        return R.SUCCESS(result);
    }

    @Override
    public R refund(WxPayEntity wxPayEntity) {
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
        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        data.put("out_refund_no", UUID.randomUUID().toString().replace("-", ""));
        data.put("out_trade_no", wxPayEntity.getOut_trade_no());
        data.put("refund_fee", wxPayEntity.getRefund_fee());
        data.put("total_fee", wxPayEntity.getTotal_fee());
        //签名参数整合，退款支付需要验签
        String singMd5 = "appid=" + config.getAppID()
                + "&mch_id=" + config.getMchID()
                + "&nonce_str" + data.get("nonce_str")
                + "&out_trade_no=" + data.get("out_trade_no")
                + "&out_refund_no=" + data.get("out_refund_no")
                + "&refund_fee=" + data.get("refund_fee")
                + "&total_fee=" + data.get("total_fee")
                + "&key=" + config.getKey();
        //使用微信默认的MD5签名
        String md5Params = MD5Util.stringMD5(singMd5);
        data.put("sign", md5Params.toUpperCase());
        Map<String, String> result = null;
        try {
            result = wxpay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("微信退款接口调用失败：{}", result);
            return R.ERROR(result);
        }
        return R.SUCCESS(result);
    }
}
