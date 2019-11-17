package com.young.payment.service;

import com.young.payment.entity.CCBPayEntity;
import com.young.payment.entity.R;

public interface ICCBPayService {
    R ccbPayOrder(CCBPayEntity ccbPayEntity);

    R ccbPayRotation(CCBPayEntity ccbPayEntity);
}
