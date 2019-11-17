package com.young.payment.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CCBPayEntity implements Serializable {
    private static final long serialVersionUID = -9162527965998473508L;

    //商户类型 1 线上
    private String merFlag;
    //订单编号
    private String orderId;
    //金额
    private String amount;
    //付款码信息
    private String qrCode;

    //轮训需要的字段
    //轮训次数，第一次为 1 ，再次轮训需要 +1
    private String qry_time;
    //付款码类型 1：龙支付 2：微信 3：支付宝 4：银联
    private String qrCodeType;
}
