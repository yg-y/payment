package com.young.payment;

import com.young.payment.entity.AliPayEntity;
import com.young.payment.service.AliPayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    AliPayService aliPayService;

    /**
     * 生成二维码接口
     * 商家生成二维码，用户使用支付宝扫一扫此二维码即可支付
     * <p>
     * 必传参数
     * 订单编号: out_trade_no （说明：此编号每次生成二维码连接时，必须唯一，前端生成，后续拿这个订单编号，进行轮训）
     * out_trade_no:123456789100022
     * 订单金额: total_amount （订单金额，用户扫描二维码时需要付款的金额）
     * total_amount:9899
     * 订单标题：subject (用户扫描二维码时，展示的商品名称)
     * subject:iPhoneXS
     **/
    @Test
    public void aliPayGetCode() {
        AliPayEntity aliPayEntity = new AliPayEntity();
        aliPayEntity.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        aliPayEntity.setTotal_amount("8999");
        aliPayEntity.setSubject("这是一个测试商品");
        aliPayService.aliPayGetCode(aliPayEntity);

    }

    /**
     * 商家扫描用户二维码
     * 必传字段：auth_code （扫描用户付款码获得的数据：示例 28763443825664394 ）
     * 必传字段：subject  订单标题
     * 必传字段：out_trade_no  订单编号（由前端生成，扫描后前端轮训，查看是否交易成功）
     *
     * @return
     */
    @Test
    public void aliPayOrder() {
        AliPayEntity aliPayEntity = new AliPayEntity();
        //这里输入扫码获取的付款码
        aliPayEntity.setAuth_code("284511624552751451");
        aliPayEntity.setSubject("这是一个测试商品");
        aliPayEntity.setOut_trade_no(UUID.randomUUID().toString().replace("-", ""));
        aliPayEntity.setTotal_amount("8999");
        aliPayService.aliPayOrder(aliPayEntity);
    }

}
