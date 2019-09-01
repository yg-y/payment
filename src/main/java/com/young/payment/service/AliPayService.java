package com.young.payment.service;

import com.young.payment.entity.AliPayEntity;

public interface AliPayService {

    Object aliPayGetCode(AliPayEntity aliPayEntity);

    Object aliPayOrder(AliPayEntity aliPayEntity);

}
