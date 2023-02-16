package com.application.sutdup.Library;

import android.icu.text.UFormat;

import com.google.type.DateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
/**
 * This class contains the validators required for inputs
 */

    public static boolean containSpecialChar(String input)
    {
        return Pattern.matches("[a-zA-Z]+", input);
    }

    public static boolean lengthCheck(String input, int length)
    {
        // Check that input <String> length is at most length <Integer>
        if (length < 0)
        {
            return false;
        }
        return input.length() <= length;
    }

    public static boolean lengthCheck(String input, int min, int max)
    {
        // Check that input <String> length is at most max <Integer> and at least min <Integer>
        if (min < 0)
        {
            return false;
        }
        return max >= input.length() && input.length() >= min;
    }

    public static boolean isNumber(String input){
        /**
         * Checks if an input is a number
         */
        try{
            Integer.parseInt(input);
            return true;
        }
        catch (Exception E){
            return false;
        }
    }

    public static boolean isDigits(String input){
        /**
         * Checks if an input is all digits <Strings></Strings>
         */
        for (int i=0; i < input.length(); i++){
            if (!Character.isDigit(input.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isSGPhoneNumber(String input)
    {
        return input.length() == 8 && input.matches("[0-9]+");
    }

    public static boolean rangeCheck(int num, int min, int max)
    {
        return num >= min && num <= max;
    }

    public static boolean isPassword(String password)
    {
        /**
         * Check if the password is at least 8 characters and contains number, capital,
         * special character and lowercase
         */
        if (password.length() > 7)
        {
            return checkPass(password);
        }
        else
        {
            System.out.println("Password too short");
            return false;
        }

    }

    public static boolean checkPass(String password)
    {
        boolean containsNum = false;
        boolean containsCap = false;
        boolean containsLow = false;
        boolean containsSpecialCharacter = false;
        char c;
        for (int i=0; i < password.length(); i++)
        {
            c = password.charAt(i);
            if (Character.isDigit(c)) containsNum = true;
            else if (Character.isUpperCase(c)) containsCap = true;
            else if (Character.isLowerCase(c)) containsLow = true;
            else if (hasSpecialCharacter(password)) containsSpecialCharacter = true;
            if (containsNum && containsLow && containsCap && containsSpecialCharacter)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpace(String input)
    {
        /**
         * Check if input string has whitespace (Space)
         */
       for (char currentChar : input.toCharArray()){
           if (Character.isWhitespace(currentChar)) {
               return true;
           }
       }
       return false;
    }

    public static boolean hasSpecialCharacter(String input)
    {
        /**
         * Checks if an input string contains special characters
         */
        Pattern sPattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher sMatcher = sPattern.matcher(input);

        return !sMatcher.matches();
    }

    public static boolean isEmail(String input)
    {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();
    }

    public static boolean valDate(String date)
    {
        /**
         * Checks if the input date is a valid date of dd/mm/yyyy format
         */

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu",
                            Locale.UK).withResolverStyle(ResolverStyle.STRICT);

            try
            {
                formatter.parse(date);
                return true;
            }
            catch(Exception e)
            {
                return false;
            }
        }
        return false;
    }

}
