package com.telefield.onebody.exception;

public class UserException extends RuntimeException {
    private final UserErrorCode userErrorCode;
    private final String detailMessage;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.userErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public UserException(UserErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.userErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
