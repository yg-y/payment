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
     *
     * @param wxPayEntity
     * @return
     */
    @GetMapping("/micropay")
    public R micropay(@ModelAttribute WxPayEntity wxPayEntity) {
        return wxPayService.micropay(wxPayEntity);
    }
}
