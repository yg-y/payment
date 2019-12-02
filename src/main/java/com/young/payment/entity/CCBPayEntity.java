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
    //分账信息1
    //分账字段，需要分账时， FZINFO1必送，有需要时再送FZINFO2
    //格式：
    //（1）分账组信息，最少1组，最多5组但总长度不超过200字节，支付时需上送完整的全部分账组信息。
    // FZINFO1和FZINFO2累加金额等于付款金额AMOUNT。
    //（2）格式：分账类型代码!编号类型^收款编号一^账号类型^费用名称一（限5个汉字内）^金额^退款标志#编号类型^收款编号二^账号类型^费用名称二（限5个汉字）^金额^退款标志#——每组中各要素分别用“^”分隔符分开，分账信息组间用“#”分隔符分开，最后以“#”结束。
    //（3）分账类型代码：21-汇总间接二清（资金流：持卡人-收单机构-收单商户-收款编号）、22-汇总直接二清（资金流：持卡人-收单机构-收款编号）、31-明细间接二清（资金流：持卡人-收单机构-收单商户-收款编号）、32-明细直接二清（资金流：持卡人-收单机构-收款编号）
    //（4）编号类型：01-商户编号、02-终端号、03-账号，必填。
    //（5）收款编号：商户编号或者终端编号或账号（最长27位），必填。
    //（6）账号类型：01-本行对公、02-本行对私借记、03-合约账户、07-内部帐。编号类型为03时必填，其余编号类型无需填值。
    //（7）金额：整数部分最长10位，小数部分最长2位，如1.23，必填，无需补空格。
    //（8）退款标志取值用法：0-未退款，1-已退款；支付时送0，退款时送1；必填。
    //需使用escape编码，escape前FZINFO1的总长度不超过200。
    // 格式实例：21!01^105110070110001^^^1.23^0#01^105110070110002^^^2.46^0#
    private String fzinfo1;
    //分账信息2
    private String fzinfo2;

    //轮训需要的字段
    //轮训次数，第一次为 1 ，再次轮训需要 +1
    private String qry_time;
    //付款码类型 1：龙支付 2：微信 3：支付宝 4：银联
    private String qrCodeType;

    //退款用的 请求序列号
    private String requestSn;
}
