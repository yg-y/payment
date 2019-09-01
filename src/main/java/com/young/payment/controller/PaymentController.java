package com.young.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("payment")
@RestController
public class PaymentController {

    /**
     * 提供接口，给前端查询交易状态 建议3s查询一次交易
     *
     * @param payOrder
     * @return
     */
    @GetMapping("/find")
    public Object findPay(String payOrder) {
        //todo 传入订单编号，查询数据库，是否成功付款
        return null;
    }
}
