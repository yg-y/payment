package com.young.payment.controller;

import com.young.payment.entity.CCBPayEntity;
import com.young.payment.entity.R;
import com.young.payment.service.ICCBPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ccb/pay")
@RestController
public class CCBPayController {

    @Autowired
    ICCBPayService iccbPayService;

    /**
     * 建行银联被扫付款接口
     * <p>
     * 必传字段如下：
     * //订单编号
     * private String orderId; todo 注意 订单编号只能是 30 位
     * //金额
     * private String amount;
     * //付款码信息
     * private String qrCode;
     *
     * @param ccbPayEntity
     * @return
     */
    @PostMapping("/order")
    public R ccbPayOrder(@RequestBody CCBPayEntity ccbPayEntity) {
        return iccbPayService.ccbPayOrder(ccbPayEntity);
    }

    /**
     * 建行支付接口轮训
     * 轮训必传参数：
     * //订单编号
     * private String orderId;
     * //todo  这个要注意： 轮训次数，第一次为 1 ，再次轮训需要 +1
     * private String qry_time;
     * todo 什么支付类型，传入对应的参数去轮训，否则会失败
     * //付款码类型 1：龙支付 2：微信 3：支付宝 4：银联
     * private String qrCodeType;
     *
     * @param ccbPayEntity
     * @return
     */
    @PostMapping("/rotation")
    public R ccbPayRotation(@RequestBody CCBPayEntity ccbPayEntity) {
        return iccbPayService.ccbPayRotation(ccbPayEntity);
    }
}
