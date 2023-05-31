package com.telefield.onebody.exception;

import lombok.Getter;

@Getter
public class EventException extends RuntimeException {
    private final EventErrorCode eventErrorCode;
    private final String detailMessage;

    public EventException(EventErrorCode errorCode) {
        super(errorCode.getMessage());
        this.eventErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public EventException(EventErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.eventErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
