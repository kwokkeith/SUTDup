package com.application.sutdup.Exceptions;

public class ValidatorException extends Exception {
    public ValidatorException(){}

    public ValidatorException(String message){
        super(message);
    }

    public ValidatorException(Throwable cause){
        super(cause);
    }

    public ValidatorException(String codeLocation, String message){
        super(codeLocation + " - " + message);
    }

    public ValidatorException(String message, Throwable cause){
        super(message, cause);
    }
}
