package com.glass.feishurobot.rpc.dto;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private T data;
    private String msg;

    // 快捷判断是否成功的方法
    public boolean isSuccess() {
        return code != null && code == 200;
    }
}
