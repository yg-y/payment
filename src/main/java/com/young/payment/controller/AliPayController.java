package com.young.payment.controller;

import com.young.payment.entity.AliPayEntity;
import com.young.payment.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AliPayController {

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
     * <p>
     * 接口返回成功后的数据
     * {
     * "code": "10000",
     * "msg": "Success",
     * "subCode": null,
     * "subMsg": null,
     * "body": "{\"alipay_trade_precreate_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"123456789100022\",\"qr_code\":\"https:\\/\\/qr.alipay.com\\/bax072373n2wjqmetsav00fe\"},\"sign\":\"SO2J5yUVx/aHRHxAFZLVDLs/Yfa8bIRvJfX1GSoGBJIgmHECmL3u8K7x0EDgE598pHPFKhnvZoPHcLQlg7oHk57KxjwoJ0LMQA2OmOGamfQQ0GyY3+xY5KA7NVPoO7gC/GhopJrGn1Y3SvS0hKVXpikf3150aHb2A5o5fsYyXYkPpbQkbhs0llBquxMoJFo8RTegCuJKqFkgA4SZEQPgNt4H7TUAdf5NzWEZt40/vKWyJdKwxoyJbpdys5TiLTVFtMfHPrrrXzWOPCD9sHYExREXBp/+LdxSJS0IhPtdxUhUdVOV7zLudASAcbtucZw1addRjmRMfU4sZXf7cpc2Eg==\"}",
     * "params": {
     * "biz_content": "{\"out_trade_no\":\"123456789100022\",\"total_amount\":\"9899\",\"subject\":\"iPhoneXS\"}"
     * },
     * "outTradeNo": "123456789100022",
     * "qrCode": "https://qr.alipay.com/bax072373n2wjqmetsav00fe",
     * "success": true,
     * "errorCode": "10000"
     * }
     * 状态 ： "msg": "Success" 代表接口调用成功
     * 二维码链接： "qrCode": "https://qr.alipay.com/bax072373n2wjqmetsav00fe",
     * 说明，接口成功调用后，会返回 qrCode ，获取返回的连接 https://qr.alipay.com/bax072373n2wjqmetsav00fe
     * 将此连接生成二维码给用户调用即可
     *
     * @param aliPayEntity
     * @return
     */
    @PostMapping("/get/code")
    public Object aliPayGetCode(@RequestBody AliPayEntity aliPayEntity) {
        return aliPayService.aliPayGetCode(aliPayEntity);
    }

    /**
     * 商家扫描用户二维码
     * 必传字段：auth_code （扫描用户付款码获得的数据：示例 28763443825664394 ）
     * 必传字段：subject  订单标题
     * 必传字段：out_trade_no  订单编号（由前端生成，扫描后前端轮训，查看是否交易成功）
     *
     * @param aliPayEntity
     * @return
     */
    @PostMapping("/pay/order")
    public Object aliPayOrder(@RequestBody AliPayEntity aliPayEntity) {
        return aliPayService.aliPayOrder(aliPayEntity);
    }

    /**
     * 支付宝支付成功后的通知
     * 此接口配置在支付宝应用里，当用户支付成功后，支付宝会调用这个接口
     * 用户支付成功后，将支付信息存入数据库，前端通过轮训的方式查看这笔交易是否成功
     * <p>
     * todo 此接口，需要内网穿透，然后将地址配置到支付宝中才能在本地测试 推荐工具 ngrok
     *
     * @param request
     */
    @GetMapping("/call/back")
    public void AliPayCallBack(HttpServletRequest request) {
        //todo 支付成功后存入数据库
        Map map = request.getParameterMap();
        for (Object o : map.entrySet()) {
            System.err.println(o.toString());
        }
    }

    @GetMapping
    public String hello() {
        return "hello world";
    }
}
