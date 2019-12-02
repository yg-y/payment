package com.young.payment.service.impl;

import ccb.pay.api.util.CCBPayUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.young.payment.config.CCBPayConfig;
import com.young.payment.entity.CCBPayEntity;
import com.young.payment.entity.R;
import com.young.payment.service.ICCBPayService;
import com.young.payment.util.CCBPaySocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class CCBPayServiceImpl implements ICCBPayService {

    /**
     * 建行扫码支付
     * <p>
     * 分账字段，需要分账时， FZINFO1必送，有需要时再送FZINFO2
     * 格式：
     * （1）分账组信息，最少1组，最多5组但总长度不超过200字节，支付时需上送完整的全部分账组信息。FZINFO1和FZINFO2累加金额等于付款金额AMOUNT。
     * （2）格式：分账类型代码!编号类型^收款编号一^账号类型^费用名称一（限5个汉字内）^金额^退款标志#编号类型^收款编号二^账号类型^费用名称二（限5个汉字）^金额^退款标志#——每组中各要素分别用“^”分隔符分开，分账信息组间用“#”分隔符分开，最后以“#”结束。
     * （3）分账类型代码：21-汇总间接二清（资金流：持卡人-收单机构-收单商户-收款编号）、22-汇总直接二清（资金流：持卡人-收单机构-收款编号）、31-明细间接二清（资金流：持卡人-收单机构-收单商户-收款编号）、32-明细直接二清（资金流：持卡人-收单机构-收款编号）
     * （4）编号类型：01-商户编号、02-终端号、03-账号，必填。
     * （5）收款编号：商户编号或者终端编号或账号（最长27位），必填。
     * （6）账号类型：01-本行对公、02-本行对私借记、03-合约账户、07-内部帐。编号类型为03时必填，其余编号类型无需填值。
     * （7）金额：整数部分最长10位，小数部分最长2位，如1.23，必填，无需补空格。
     * （8）退款标志取值用法：0-未退款，1-已退款；支付时送0，退款时送1；必填。
     * 需使用escape编码，escape前FZINFO1的总长度不超过200。
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
        //判断是否有分账参数
        //判断分账信息1
        if (!StringUtils.isEmpty(ccbPayEntity.getFzinfo1())) {
            param.append("&FZINFO1=").append(ccbPayEntity.getFzinfo1());
        }
        //判断分账信息2
        if (!StringUtils.isEmpty(ccbPayEntity.getFzinfo2())) {
            param.append("&FZINFO2=").append(ccbPayEntity.getFzinfo2());
        }
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


    /**
     * 建行外联退款接口
     *
     * @param ccbPayEntity
     * @return
     */
    @Override
    public R ccbPayBack(CCBPayEntity ccbPayEntity) {
        //获取退款xml
        String xml = CCBPaySocketUtil.getXmlInfo(ccbPayEntity.getRequestSn(), ccbPayEntity.getOrderId(), ccbPayEntity.getAmount());
        if (StringUtils.isEmpty(xml)) {
            return R.ERROR("xml 为空");
        }
        log.info("发送到建行退款的xml:{}", xml);
        //发送到建行
        String resultXml = CCBPaySocketUtil.sendCCBPaySocket(xml);
        if (StringUtils.isEmpty(resultXml)) {
            return R.ERROR("发送失败");
        }
        log.info("发送到建行退款返回xml：{}", resultXml);
        Object result = resultXml;
        return R.SUCCESS(result);
    }
}
