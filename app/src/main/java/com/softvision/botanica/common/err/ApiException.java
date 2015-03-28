package com.softvision.botanica.common.err;

import com.softvision.botanica.common.pojo.constants.ErrorCode;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 5/7/13
 * Time: 4:18 PM
 * Used to catch and handle server comunication errors
 */
public class ApiException extends Exception {
    private ErrorCode errorCode;

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    public ApiException(ErrorCode errorCode) {
        this.errorCode = (errorCode != null)?errorCode:ErrorCode.NO_REPLY;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
