package com.young.payment.controller;

import com.young.payment.entity.R;
import com.young.payment.entity.WxPayEntity;
import com.young.payment.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxpay")
public class WxPayController {

    @Autowired
    WxPayService wxPayService;

    /**
     * 付款码支付
     * <p>
     * 测试用例请看 WxPayTest
     * 必传参数：{
     * wxPayEntity.setAuth_code("134650175269452540");
     * wxPayEntity.setBody("测试商品");
     * wxPayEntity.setTotal_fee("100");
     * }
     * <p>
     * 支付成功返回结果：{
     * transaction_id=4200000428201910202817377592,
     * nonce_str=GeCgfnrra02Ar6KR,
     * bank_type=CFT,
     * openid=omifgv4aYliWBfuIEMO0qnTRZCPY,
     * sign=51436978DDA9E89D13885FBE68F58826D6ED11DD3221F37383D6B5C01B4B48CE,
     * return_msg=OK,
     * fee_type=CNY,
     * mch_id=1559020831,
     * cash_fee=100,
     * out_trade_no=dc7ecfdd38954ad28d1708df68df623a,
     * cash_fee_type=CNY,
     * appid=wx3dc8e67ebe73bf0e,
     * total_fee=100,
     * trade_type=MICROPAY,
     * result_code=SUCCESS,
     * attach=,
     * time_end=20191020154455,
     * is_subscribe=N,
     * return_code=SUCCESS}
     * }
     *
     * @param wxPayEntity
     * @return
     */
    @GetMapping("/micropay")
    public R micropay(@ModelAttribute WxPayEntity wxPayEntity) {
        return wxPayService.micropay(wxPayEntity);
    }

    /**
     * 退款接口
     * <p>
     * 必传参数：{
     * out_trade_no：后台自定义生成的订单号
     * total_fee：该笔交易的订单金额
     * refund_fee：需要退款的金额
     * <p>
     * }
     *
     * @param wxPayEntity
     * @return
     */
    @GetMapping("/refund")
    public R refund(@ModelAttribute WxPayEntity wxPayEntity) {
        return wxPayService.refund(wxPayEntity);
    }
}
