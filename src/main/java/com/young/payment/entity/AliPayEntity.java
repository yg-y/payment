package com.young.payment.entity;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;
import lombok.Data;

import java.io.Serializable;

/**
 * 支付宝请求实体类
 */
@Data
public class AliPayEntity extends AlipayObject implements Serializable {
    private static final long serialVersionUID = 7570966023318720818L;
    //out_trade_no	String	必选	64	商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复	20150320010101001
    @ApiField("out_trade_no")
    private String out_trade_no;
    //seller_id	String	可选	28	卖家支付宝用户ID。
    //如果该值为空，则默认为商户签约账号对应的支付宝用户ID	2088102146225135
    @ApiField("seller_id")
    private String seller_id;
    //total_amount	Price	必选	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    //如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】	88.88
    @ApiField("total_amount")
    private String total_amount;
    //discountable_amount	Price	可选	9	可打折金额.
    //参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
    //如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】	8.88
    @ApiField("discountable_amount")
    private String discountable_amount;
    //subject	String	必选	256	订单标题	Iphone6 16G
    @ApiField("subject")
    private String subject;
    //body	String	可选	128	对交易或商品的描述	Iphone6 16G
    @ApiField("body")
    private String body;
    //buyer_id	String	特殊可选	28	买家的支付宝唯一用户号（2088开头的16位纯数字）	2088102146225135
    @ApiField("buyer_id")
    private String buyer_id;
    // goods_detail	GoodsDetail[]	可选		订单包含的商品列表信息，json格式，其它说明详见：“商品明细说明”	[{"goods_id":"apple-01","goods_name":"ipad","goods_category":"7788230","price":"2000.00","quantity":"1"}]
    @ApiField("goods_detail")
    private String goods_detail;
    //product_code	String	可选	32	销售产品码。
    //如果签约的是当面付快捷版，则传OFFLINE_PAYMENT;
    //其它支付宝当面付产品传FACE_TO_FACE_PAYMENT；
    //不传默认使用FACE_TO_FACE_PAYMENT；	FACE_TO_FACE_PAYMENT
    @ApiField("product_code")
    private String product_code;
    //operator_id	String	可选	28	商户操作员编号	Yx_001
    @ApiField("operator_id")
    private String operator_id;
    //store_id	String	可选	32	商户门店编号	NJ_001
    @ApiField("store_id")
    private String store_id;
    //terminal_id	String	可选	32	商户机具终端编号	NJ_T_001
    @ApiField("terminal_id")
    private String terminal_id;
    // extend_params	ExtendParams	可选		业务扩展参数	{“sys_service_provider_id”:” 2088511833207846”}
    @ApiField("extend_params")
    private String extend_params;
    //timeout_express	String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。	90m
    @ApiField("timeout_express")
    private String timeout_express;
    // settle_info	SettleInfo	可选		描述结算信息，json格式，详见结算参数说明
    @ApiField("settle_info")
    private String settle_info;
    // logistics_detail	LogisticsDetail	可选		物流信息
    @ApiField("logistics_detail")
    private String logistics_detail;
    // business_params	BusinessParams	可选		商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式	{"data":"123"}
    @ApiField("business_params")
    private String business_params;
    // receiver_address_info	ReceiverAddressInfo	可选		收货人及地址信息
    @ApiField("receiver_address_info")
    private String receiver_address_info;

    @ApiField("scene")
    private String scene;
    @ApiField("auth_code")
    private String auth_code;

}
