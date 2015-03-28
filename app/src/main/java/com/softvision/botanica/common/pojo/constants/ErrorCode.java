package com.softvision.botanica.common.pojo.constants;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 5/7/13
 * Time: 4:18 PM
 */
public enum ErrorCode {
    //these are device error codes
    NO_REPLY(-1),                     //if device has no connectivity
    NO_NETWORK(-2),                     //if device has no connectivity
    CONNECTION_TIMED_OUT(-3),           //if connection was lost after server call was initiated

    //below are API error codes
    SUCCESS(0), GEO_LOCATION_UNKNOWN(1);

    private final Integer indicator;

    ErrorCode(Integer indicator) {
        this.indicator = indicator;
    }

    public static ErrorCode getByIndicator(Integer indicator) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.indicator.equals(indicator)) {
                return errorCode;
            }
        }
        return null;
    }

    public Integer getIndicator() {
        return indicator;
    }

    @Override
    public String toString() {
        return name() + "[" + indicator + "]";
    }
}
