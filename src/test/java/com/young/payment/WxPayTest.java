package com.young.payment;

import com.young.payment.entity.R;
import com.young.payment.entity.WxPayEntity;
import com.young.payment.service.WxPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxPayTest {

    @Autowired
    WxPayService wxPayService;

    @Test
    public void micropay() {
        WxPayEntity wxPayEntity = new WxPayEntity();
        wxPayEntity.setAuth_code("134650175269452540");
        wxPayEntity.setBody("测试商品");
        wxPayEntity.setTotal_fee("100");
        R r = wxPayService.micropay(wxPayEntity);
        System.err.println(r.getData().toString());
    }

    @Test
    public void refund() {
        WxPayEntity wxPayEntity = new WxPayEntity();
        wxPayEntity.setOut_trade_no("dc7ecfdd38954ad28d1708df68df623a");
        wxPayEntity.setTotal_fee("100");
        wxPayEntity.setRefund_fee("100");
        R r = wxPayService.refund(wxPayEntity);
        System.err.println(r.getData());
    }
}
