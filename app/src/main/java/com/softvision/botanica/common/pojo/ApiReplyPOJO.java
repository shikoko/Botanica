package com.softvision.botanica.common.pojo;

import com.softvision.botanica.common.pojo.constants.ErrorCode;

public class ApiReplyPOJO<T> {
    private Integer error;
    private T payload;

    public ErrorCode getError() {
        return ErrorCode.getByIndicator(error);
    }

    public void setError(ErrorCode error) {
        this.error = error.getIndicator();
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
