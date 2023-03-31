package com.application.sutdup.Library.db;

import com.application.sutdup.Exceptions.ValidatorException;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains the validators required for inputs
 * Editors: Kwok Keith
 **/
public class Validator {

    /**
      Check that input `String` contains ONLY letters.
      @params input: String
     **/
    public static void isWord(String input) throws Exception {

        if (!Pattern.matches("[a-zA-Z]+", input))
            throw new ValidatorException("isWord", "Input String must contain only letters") ;
    }

    /**
     Check that input `String` contains letters and/or whitespaces.
     @params input: String
     **/
    public static void isSentence(String input) throws Exception {

        if(!Pattern.matches("[a-zA-Z\\s]*", input)){
            throw new ValidatorException("isSentence", "Input String must contain only letters" +
                    "or whitespaces") ;
        };
    }

    /**
     Check that input `String` length is at most atMostLength `Integer`
        @params input: String
        @params atMostLength: Int
     **/
    public static void lengthCheck(String input, int atMostLength) throws Exception {

        if (atMostLength < 0)
        {
            throw new ValidatorException("lengthCheck", "Input length cannot be less than 0");
        }
        if( !(input.length() <= atMostLength)) {
            throw new ValidatorException("lengthCheck", "Input String exceeded the length of " +
                    Integer.toString(atMostLength)) ;
        };
    }

    /**
     * Check that input `String` length is at most max `Integer` and at least min `Integer`
        @params input: String
        @params min: Int
        @params max: Int
     **/
    public static void lengthCheck(String input, int min, int max) throws Exception {


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

    /**
     * Checks if an input is a number
     * @param input: String
     **/
    public static void isNumber(String input) throws Exception{

        try{
            Integer.parseInt(input);
        }
        catch (Exception E){
            throw new ValidatorException( "isNumber", "Input String must be a number" );
        }
    }

    /**
     * Checks if an input is all digits `Strings``/Strings`
     * @params input: String
     **/
    public static void isDigits(String input) throws Exception{

        for (int i=0; i < input.length(); i++){
            if (!Character.isDigit(input.charAt(i)))
            {
                throw new ValidatorException( "isDigits", "Input String must only contain digits" );
            }
        }
    }

    /**
     * Checks if input string is a valid Singapore phone number
     * @param input: String
     **/
    public static boolean isSGPhoneNumber(String input)
    {

        if(!(input.length() == 8 && input.matches("[0-9]+"))){
            return false;
        }
        return true;
    }

    /**
     * Checks if a number is within the range of min and max (Inclusive)
     * @params num: Int
     * @params min: Int (Inclusive)
     * @params max: Int (Inclusive)
     **/
    public static void rangeCheck(int num, int min, int max) throws Exception
    {

        if(!(num >= min && num <= max))
            throw new ValidatorException( "rangeCheck", Integer.toString(num) + "is not within " +
                    Integer.toString(min) + " and " + Integer.toString(max));
    }

    /**
     * Check if the password is at least 8 characters and contains number, capital,
     * special character and lowercase
     * @params password: String
     **/
    public static void isPassword(String password) throws Exception
    {

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

    /**
     * Takes in a password string and checks if it contains number, capital and lowercase
     * letters, and special character.
     * @params password: String
     **/
    private static boolean checkPass(String password) throws Exception {

        boolean containsNum = false;
        boolean containsCap = false;
        boolean containsLow = false;
        boolean containsSpecialCharacter = false;
        char c;
        try{
            hasSpecialCharacter(password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i < password.length(); i++)
        {
            c = password.charAt(i);
            if (Character.isDigit(c)) containsNum = true;
            else if (Character.isUpperCase(c)) containsCap = true;
            else if (Character.isLowerCase(c)) containsLow = true;
            if (containsNum && containsLow && containsCap)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if input string has whitespace (Space)
     * @params input: String
     **/
    public static void hasSpace(String input) throws Exception
    {

       for (char currentChar : input.toCharArray()){
           if (Character.isWhitespace(currentChar)) {
               return;
           }
       }
        throw new ValidatorException( "hasSpace", "Input String contains no whitespace");
    }

    /**
     * Check if input string is a valid ID, contains number and letters only
     * @params input: String
     **/
    public static void isValidId(String input) throws Exception
    {

        if(!Pattern.matches("[a-zA-Z0-9]*", input)){
            throw new ValidatorException("isValidId", "Input String must contain letters" +
                    "and/or numbers only") ;
        };

    }

    /**
     * Checks if an input string contains special characters
     * @params input: String
     **/
    public static void hasSpecialCharacter(String input) throws Exception
    {

        Pattern sPattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher sMatcher = sPattern.matcher(input);

         if(sMatcher.matches()){
             throw new ValidatorException( "hasSpecialCharacter", "Input String does not contain" +
                     "any special character");
         }
    }

    /**
     * Checks if an input satisfies an email format. ____@____.com
     * @params input: String
     **/
    public static void isEmail(String input) throws Exception
    {

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        if(!matcher.find()){
            throw new ValidatorException( "isEmail", "Input String is not of a valid email format");
        }
    }

    /**
     * Checks if the input date is a valid date of dd/mm/yyyy format
     * @params date: String
     **/
    public static void isDate(String date) throws Exception
    {


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

    /**
     * Checks if String input is a valid image file extension
     * @params input: String
     **/
    public static void isValidImageFile(String input) throws Exception
    {

        Pattern sPattern = Pattern.compile("[^\\\\s]+(\\\\.(?i)(jpe?g|png|gif|bmp))$");
        Matcher sMatcher = sPattern.matcher(input);
        if(!sMatcher.matches()){
            throw new ValidatorException( "isValidImageFile", "Input String is not a valid" +
                    "image file extension");
        }
    }

    /**
     *   Check if an input string is at most 2 decimal place
     * @params input: String
     **/
    public static void isUpToTwoDecimal(String input) throws Exception{

        if(!(input.matches("^\\d+\\.\\d{0,2}$"))){
            throw new ValidatorException("isUpToTwoDecimal", "Input String is not up to 2 decimal" +
                    "places. (Exceeds 2 decimal places)");
        }
    }

    /**
     * Checks if input string is an empty string, throws Exception.
     * @params input: String
     **/
    public static void isEmpty(String input) throws Exception
    {

        if (!(input.matches("[^$]"))){
           throw new ValidatorException( "isEmpty", "Input String is not empty");
        }
    }

    /**
     * Checks if input string is an empty string, returns Boolean
     * @params input: String
     **/
    public static boolean isEmptyBool(String input)
    {

        return input.matches("[^$]");
    }

}

