package com.application.sutdup.Library;

import com.application.sutdup.Exceptions.ValidatorException;

public class Validators {

    public static boolean isSGPhoneNumber(String input)
    {

        if(!(input.length() == 8 && input.matches("[0-9]+"))){
            return false;
        }
        return true;
    }

    public static boolean lengthCheck(String input) {
        if (input.length()<8){
            return false;
        }
        return true;
    }
    public static boolean isNumber(String input) {
        if (!input.matches("\\d")){
            return false;
        }
        return true;
    }

}
