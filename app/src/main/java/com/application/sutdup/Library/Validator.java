package com.application.sutdup.Library;

import com.application.sutdup.Exceptions.ValidatorException;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
/**
 * This class contains the validators required for inputs
 * Editors: Kwok Keith
 */

    public static void isWord(String input) throws Exception {
        /**
         * Check that input <String> contains ONLY letters.
         */
        if (!Pattern.matches("[a-zA-Z]+", input))
            throw new ValidatorException("isWord", "Input String must contain only letters") ;
    }

    public static void isSentence(String input) throws Exception {
        /**
         * Check that input <String> contains letters or whitespaces.
         */
        if(!Pattern.matches("[a-zA-Z\\s]*", input)){
            throw new ValidatorException("isSentence", "Input String must contain only letters" +
                    "or whitespaces") ;
        };
    }

    public static void lengthCheck(String input, int length) throws Exception {
        /**
         *  Check that input <String> length is at most length <Integer>
         */
        if (length < 0)
        {
            throw new ValidatorException("lengthCheck", "Input length cannot be less than 0");
        }
        if( !(input.length() <= length)) {
            throw new ValidatorException("lengthCheck", "Input String exceeded the length of " +
                    Integer.toString(length)) ;
        };
    }

    public static void lengthCheck(String input, int min, int max) throws Exception {
        /**
         * Check that input <String> length is at most max <Integer> and at least min <Integer>
         **/

        if (min < 0)
        {
            throw new ValidatorException("lengthCheck", "Input min cannot be less than 0");
        }
        if( input.length() >= max ) {
            throw new ValidatorException("lengthCheck", "Input String exceeded max of " +
                    Integer.toString(max));
        }
        if ( input.length() <= min){
            throw new ValidatorException("lengthCheck", "Input String lesser than min of " +
                    Integer.toString(min));
        }
    }

    public static void isNumber(String input) throws Exception{
        /**
         * Checks if an input is a number
         **/
        try{
            Integer.parseInt(input);
        }
        catch (Exception E){
            throw new ValidatorException( "isNumber", "Input String must be a number" );
        }
    }

    public static void isDigits(String input) throws Exception{
        /**
         * Checks if an input is all digits <Strings></Strings>
         */
        for (int i=0; i < input.length(); i++){
            if (!Character.isDigit(input.charAt(i)))
            {
                throw new ValidatorException( "isDigits", "Input String must only contain digits" );
            }
        }
    }

    public static void isSGPhoneNumber(String input) throws Exception
    {
        /**
         * Checks if input string is a valid Singapore phone number
         */
        if(!(input.length() == 8 && input.matches("[0-9]+"))){
            throw new ValidatorException( "isSGPhoneNumber", "Input String must " +
                    "be of the format (dddd dddd) i.e. 9838 7583.");
        }
    }

    public static void rangeCheck(int num, int min, int max) throws Exception
    {
        /**
         * Checks if a number is within the range of min and max (Inclusive)
         */
        if(!(num >= min && num <= max))
            throw new ValidatorException( "rangeCheck", Integer.toString(num) + "is not within " +
                    Integer.toString(min) + " and " + Integer.toString(max));
    }

    public static void isPassword(String password) throws Exception
    {
        /**
         * Check if the password is at least 8 characters and contains number, capital,
         * special character and lowercase
         */
        if (password.length() > 7)
        {
            if(!checkPass(password))
                throw new ValidatorException( "isPassword", "Password must contain number," +
                        "capital, special character and lowercase");
        }
        else
        {
            throw new ValidatorException( "isPassword", "Password must be at least 8 characters");
        }

    }

    private static boolean checkPass(String password)
    {
        /**
         * Takes in a password string and checks if it contains number, capital and lowercase
         * letters, and special character.
         */
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

    public static void hasSpace(String input) throws Exception
    {
        /**
         * Check if input string has whitespace (Space)
         */
       for (char currentChar : input.toCharArray()){
           if (Character.isWhitespace(currentChar)) {
               return;
           }
       }
        throw new ValidatorException( "hasSpace", "Input String does not contain whitespace");
    }

    public static void hasSpecialCharacter(String input) throws Exception
    {
        /**
         * Checks if an input string contains special characters
         */
        Pattern sPattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher sMatcher = sPattern.matcher(input);

         if(sMatcher.matches()){
             throw new ValidatorException( "hasSpecialCharacter", "Input String does not contain" +
                     "any special character");
         }
    }

    public static void isEmail(String input) throws Exception
    {
        /**
         * Checks if an input satisfies an email format
         */
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        if(!matcher.find()){
            throw new ValidatorException( "isEmail", "Input String is not of a valid email format");
        }
    }

    public static void isDate(String date) throws Exception
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
            }
            catch(Exception e)
            {
                throw new ValidatorException( "isDate", "Input String is not a valid date format" +
                        ", format should be in dd/mm/yyyy");
            }
        }
        throw new ValidatorException( "isDate", "Input String is not a valid date format" +
                ", format should be in dd/mm/yyyy");
    }
}
