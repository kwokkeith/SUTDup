package com.application.sutdup.Exceptions;

public class HandledException extends RuntimeException{

    public HandledException(){}

    public HandledException(String message){
        super(message);
    }

    public HandledException(Throwable cause){
        super(cause);
    }

    public HandledException(String message, Throwable cause){
        super(message, cause);
    }

}
