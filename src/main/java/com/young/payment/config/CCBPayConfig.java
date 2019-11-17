package com.young.payment.config;

public class CCBPayConfig {
    //银行接口
    public static final String HOST = "https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?";
    //商戶ID
    public static final String MERCHANTID = "xxxxxx";
    //柜台号
    public static final String POSID = "xxxxx";
    //分行号
    public static final String BRANCHID = "xxxxx";
    //pubkey
    public static final String PUBKEY = "xxxxx";
    //扫码支付
    public static final String PAY100 = "PAY100";
    //订单轮训
    public static final String PAY101 = "PAY101";
    public static final String MERFLAG = "1";
}
