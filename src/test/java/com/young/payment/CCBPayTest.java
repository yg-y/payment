package com.young.payment;

import ccb.pay.api.util.CCBPayUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.young.payment.config.CCBPayConfig;

public class CCBPayTest {

    //商戶ID
    private static final String MERCHANTID = CCBPayConfig.MERCHANTID;
    //柜台号
    private static final String POSID = CCBPayConfig.POSID;
    //分行号
    private static final String BRANCHID = CCBPayConfig.BRANCHID;
    //pubkey
    private static final String PUBKEY = CCBPayConfig.PUBKEY;

    /**
     * 该示例以互联网银企直连被扫的PAY100接口为例
     *
     * @throws Exception
     */
    public static String testCCBPayUtil(String code) throws Exception {
        //银行接口url
        String host = "https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?";
        //商户信息
        String merInfo = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID=" + BRANCHID;
        //生成订单号 30位以内
        String orderId = String.valueOf(System.currentTimeMillis());
        System.err.println(orderId);
        //加密原串【PAY100接口定义的请求参数】
        StringBuffer param = new StringBuffer();
        param.append(merInfo)
                .append("&TXCODE=").append("PAY100")
                .append("&MERFLAG=").append("1")
                .append("&ORDERID=").append(orderId)
                .append("&AMOUNT=").append("0.01")
                .append("&QRCODE=").append(code);

        //执行加密操作
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        String url = ccbPayUtil.makeCCBParam(param.toString(), PUBKEY);
        //拼接请求串
        url = host + merInfo + "&ccbParam=" + url;
        System.out.println(url);

        //请求的URL如下所示：
		/*
		https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?MERCHANTID=105910100190000&POSID=000000000&BRANCHID=610000000
		&ccbParam=加密结果...
		 */

        //向建行网关发送请求交易...
//        HttpUtils.doSend(url);
        String result = HttpUtil.post(url, "");
        System.err.println(result);
        return result;
    }

    /**
     * 轮训订单
     *
     * @throws Exception
     */
    public static String testCCBPayRotationUtil(String orderId, int count) throws Exception {
        //银行接口url
        String host = "https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?";
        //商户信息
        String merInfo = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID=" + BRANCHID;
        //加密原串【PAY100接口定义的请求参数】
        StringBuffer param = new StringBuffer();
        param.append(merInfo)
                .append("&TXCODE=").append("PAY101")
                .append("&MERFLAG=").append("1")
                .append("&ORDERID=").append(orderId)
                .append("&QRCODETYPE=").append("2")
                .append("&QRYTIME=").append(count);

        //执行加密操作
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        String url = ccbPayUtil.makeCCBParam(param.toString(), PUBKEY);
        //拼接请求串
        url = host + merInfo + "&ccbParam=" + url;
        System.out.println(url);

        //请求的URL如下所示：
		/*
		https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?MERCHANTID=105910100190000&POSID=000000000&BRANCHID=610000000
		&ccbParam=加密结果...
		 */

        //向建行网关发送请求交易...
//        HttpUtils.doSend(url);
        String result = HttpUtil.post(url, "");
        System.err.println(result);
        return result;
    }

    /**
     * 订单退款
     *
     * @throws Exception
     */
    public static String testCCBPayOrderBack(String orderId) throws Exception {
        //银行接口url
        String host = "https://127.0.0.1:12345/CCBIS/B2CMainPlat_00_BEPAY?";
        //商户信息
        String merInfo = "MERCHANTID=" + MERCHANTID + "&POSID=" + POSID + "&BRANCHID=" + BRANCHID;
        //加密原串【PAY100接口定义的请求参数】
        StringBuffer param = new StringBuffer();
        param.append(merInfo)
                .append("&REQUEST_SN=").append(System.currentTimeMillis())
                .append("&CUST_ID=").append(MERCHANTID)
                .append("&TXCODE=").append("5W1004")
                .append("&PASSWORD=").append("lmy19950714")
                .append("&LANGUAGE=").append("CN")
                .append("&USER_ID=").append("002")
                .append("&MONEY=").append("0.01")
                .append("&ORDER=").append(orderId);

        //执行加密操作
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        String url = ccbPayUtil.makeCCBParam(param.toString(), PUBKEY);
        //拼接请求串
        url = host + merInfo + "&ccbParam=" + url;

        System.out.println("退款接口调用中....");
        System.out.println(url);

        //请求的URL如下所示：
		/*
		https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_00_BEPAY?MERCHANTID=105910100190000&POSID=000000000&BRANCHID=610000000
		&ccbParam=加密结果...
		 */

        //向建行网关发送请求交易...
//        HttpUtils.doSend(url);
        String result = HttpUtil.post(url, "");
        System.err.println(result);
        return result;
    }


    /**
     * 商户通知验签DEMO，该示例以互联网银企直连被扫的PAY100接口为例
     * <p>
     * {"RESULT":"Q",
     * "ORDERID":"353dc759-2dbd-4cd5-a2f8-faaa211e3aa4",
     * "QRCODETYPE":"2",
     * "AMOUNT":"0.01",
     * "WAITTIME":"5",
     * "TRACEID":"1010113711573824537127578",
     * "ERRCODE":"",
     * "ERRMSG":"",
     * "SIGN":"09717d1199004ee946e98e2a02b33bc79255af32d49b15b19680e7a677275b35ba3b5ca4a1b034328b9d8f1e7e8d3b815946f5526dee47da7048643442c01d1cb13bb68411d7106921cbbe07a3987eca2bcc49245edb9b4c53019008d8811ee136d5d73fdef2007a261c8fda113ad3d3704c9efc72f4141f63a24fba4f6b657e"
     * }
     */
    public static void testCCBNotifyCheck(String result, String OrderId, String traceId, String sign) {
        //商户通知参数
        String notifyURLParam = "RESULT=" + result + "&ORDERID=" + OrderId + "&AMOUNT=0.01&WAITTIME=5&TRACEID=" + traceId;
        //验证签名数据
        CCBPayUtil ccbPayUtil = new CCBPayUtil();
        boolean b = ccbPayUtil.verifyNotifySign(notifyURLParam, sign, PUBKEY);
        if (b) {
            System.out.println("签名验证成功！");
        } else {
            System.out.println("签名验证失败！");
        }
    }

    /**
     * {"RESULT":"Y",
     * "ORDERID":"1574349649351",
     * "QRCODETYPE":"2",
     * "AMOUNT":"0.01",
     * "WAITTIME":"",
     * "TRACEID":"1010115211574349651415192",
     * "ERRCODE":"",
     * "ERRMSG":"",
     * "SIGN":"277245ae98c5969d24b21c0d93134fc489562701e3e7fd7fb96f49cbc5379bf554de00a561fed8f31fed8ff2e9cc5edd4b9dde8c8ff340172050301c9d7665f3767b5252e6fdaa3c626eea8d7f5feb34f50d5ffe59030198fb44cd78a7de210be5ec726ff9a556c5701389e5313c9e8fc48be043141daf1f963562d28cdf5e8c"
     * }
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //todo 传入扫码获得到的二维码信息
//        String result = testCCBPayUtil("134781954690208952");
        String result = testCCBPayOrderBack("1574349649351");

        JSONObject jsonObject = JSONObject.parseObject(result);
        // todo 商户通知验证签名 如果需要验签 请打开注释，并且注意修改订单的金额
//        testCCBNotifyCheck(jsonObject.get("RESULT").toString(), jsonObject.get("ORDERID").toString()
//                , jsonObject.get("TRACEID").toString(), jsonObject.get("SIGN").toString());

        //轮训订单，轮训5次，每次间隔时间5s
//        for (int i = 0, j = 5; i < j; i++) {
//            Thread.sleep(5000);
//            String rs = testCCBPayRotationUtil(jsonObject.get("ORDERID").toString(), i + 1);
//            System.err.println(rs);
//        }

        System.err.println(jsonObject);
//        String rs = testCCBPayRotationUtil("353dc759-2dbd-4cd5-a2f8-faaa211e3aa4", 2);
//        System.err.println(rs);
    }
}
