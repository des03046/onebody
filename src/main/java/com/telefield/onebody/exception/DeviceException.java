package com.telefield.onebody.exception;

import lombok.Getter;

@Getter
public class DeviceException extends RuntimeException {
    private final DeviceErrorCode deviceErrorCode;
    private final String detailMessage;

    public DeviceException(DeviceErrorCode errorCode) {
        super(errorCode.getMessage());
        this.deviceErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }
    public DeviceException(DeviceErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.deviceErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
