package com.young.payment.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class R {
    private Integer status;
    private String msg;
    private Object data;

    public static R SUCCESS(Object data) {
        return R.builder().data(data).msg("success").status(1).build();
    }

    public static R SUCCESS(String msg) {
        return R.builder().data(null).msg(msg).status(1).build();
    }

    public static R ERROR(String msg) {
        return R.builder().data(null).msg(msg).status(0).build();
    }

    public static R ERROR(Object data) {
        return R.builder().data(data).msg("error").status(0).build();
    }
}
