package com.young.payment.service.impl;

import ccb.pay.api.util.CCBPayUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.young.payment.config.CCBPayConfig;
import com.young.payment.entity.CCBPayEntity;
import com.young.payment.entity.R;
import com.young.payment.service.ICCBPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class CCBPayServiceImpl implements ICCBPayService {

    /**
     * 建行扫码支付
     *
     * @param ccbPayEntity
     * @return
     */
    @Override
    public R ccbPayOrder(CCBPayEntity ccbPayEntity) {
        if (StringUtils.isEmpty(ccbPayEntity.getAmount()) || StringUtils.isEmpty(ccbPayEntity.getOrderId())
                || StringUtils.isEmpty(ccbPayEntity.getQrCode())) {
            return R.ERROR("必传参数不能为空");
        }
        //银行接口url
        String host = CCBPayConfig.HOST;
        //商户信息
        String merInfo = "MERCHANTID=" + CCBPayConfig.MERCHANTID + "&POSID=" + CCBPayConfig.POSID + "&BRANCHID=" + CCBPayConfig.BRANCHID;
        //加密原串【PAY100接口定义的请求参数】
        StringBuffer param = new StringBuffer();
        param.append(merInfo)
                .append("&TXCODE=").append(CCBPayConfig.PAY100)
                .append("&MERFLAG=").append("1")
                .append("&ORDERID=").append(ccbPayEntity.getOrderId())
                .append("&AMOUNT=").append(ccbPayEntity.getAmount())
                .append("&QRCODE=").append(ccbPayEntity.getQrCode());

        //执行加密操作
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        String url = null;
        try {
            url = ccbPayUtil.makeCCBParam(param.toString(), CCBPayConfig.PUBKEY);
        } catch (UnsupportedEncodingException e) {
            log.info("ccb pay error :{}", e.getMessage());
            return R.ERROR("ccb pay error :" + e.getMessage());
        }

        //请求的URL如下所示：
		/*
		https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?MERCHANTID=105910100190000&POSID=000000000&BRANCHID=610000000
		&ccbParam=加密结果...
		 */
        //拼接请求串
        url = host + merInfo + "&ccbParam=" + url;
        log.info("ccb pay url : {}", url);
        //向建行网关发送请求交易...
        String result = HttpUtil.post(url, "");
        return R.SUCCESS(JSONObject.parse(result));
    }

    @Override
    public R ccbPayRotation(CCBPayEntity ccbPayEntity) {
        if (StringUtils.isEmpty(ccbPayEntity.getOrderId()) || StringUtils.isEmpty(ccbPayEntity.getQrCodeType())
                || StringUtils.isEmpty(ccbPayEntity.getQry_time())) {
            return R.ERROR("必传参数不能为空");
        }
        //银行接口url
        String host = CCBPayConfig.HOST;
        //商户信息
        String merInfo = "MERCHANTID=" + CCBPayConfig.MERCHANTID + "&POSID=" + CCBPayConfig.POSID + "&BRANCHID=" + CCBPayConfig.BRANCHID;
        //加密原串【PAY100接口定义的请求参数】
        StringBuffer param = new StringBuffer();
        param.append(merInfo)
                .append("&TXCODE=").append(CCBPayConfig.PAY101)
                .append("&MERFLAG=").append(CCBPayConfig.MERFLAG)
                .append("&ORDERID=").append(ccbPayEntity.getOrderId())
                .append("&QRCODETYPE=").append(ccbPayEntity.getQrCodeType())
                .append("&QRYTIME=").append(ccbPayEntity.getQry_time());

        //执行加密操作
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        String url = null;
        try {
            url = ccbPayUtil.makeCCBParam(param.toString(), CCBPayConfig.PUBKEY);
        } catch (UnsupportedEncodingException e) {
            log.info("ccb pay rotation error :{}", e.getMessage());
            return R.ERROR("ccb pay rotation error :" + e.getMessage());
        }
        //拼接请求串
        url = host + merInfo + "&ccbParam=" + url;
        log.info("ccb pay url : {}", url);
        //向建行网关发送请求交易...
        String result = HttpUtil.post(url, "");
        return R.SUCCESS(JSONObject.parse(result));
    }
}
