package com.young.payment.config;

public class CCBPayConfig {
    //银行接口
    public static final String HOST = "https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?";
    //商戶ID
    public static final String MERCHANTID = "105000159690954";
    //柜台号
    public static final String POSID = "041633214";
    //分行号
    public static final String BRANCHID = "211000000";
    //pubkey
    public static final String PUBKEY = "30819c300d06092a864886f70d010101050003818a0030818602818079ae769b6bdf03c31f57bc2152cc636ced2f47580990ae716450a1d7198e21cd94c9655a5a092c4b64b4a5da5fc35428d2f86853b6a140710c87134e6d2fc8d1935c591e051be6ac0fc70d3b9533a3525fdc12e9c6e87f649586dee8e84705b4e973094f543fc821d1688b602dcd105f349af56dd32022314562bf47c934c5f5020111";
    //扫码支付
    public static final String PAY100 = "PAY100";
    //订单轮训
    public static final String PAY101 = "PAY101";
    public static final String MERFLAG = "1";
}
