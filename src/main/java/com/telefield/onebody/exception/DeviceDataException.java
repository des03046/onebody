package com.telefield.onebody.exception;

public class DeviceDataException extends RuntimeException {
    private final DeviceDataErrorCode deviceDataErrorCode;
    private final String detailMessage;

    public DeviceDataException(DeviceDataErrorCode errorCode) {
        super(errorCode.getMessage());
        this.deviceDataErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public DeviceDataException(DeviceDataErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.deviceDataErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
