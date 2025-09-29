package com.edu.less08.service;

public class ServiceException extends Exception{
    private String userMessage;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public String getUserMessage() {
        return userMessage;
    }

    public ServiceException setUserMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }
}
