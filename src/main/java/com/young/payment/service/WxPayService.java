package com.young.payment.service;

import com.young.payment.entity.R;
import com.young.payment.entity.WxPayEntity;

public interface WxPayService {
    R micropay(WxPayEntity wxPayEntity);

    R refund(WxPayEntity wxPayEntity);
}
