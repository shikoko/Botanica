package com.softvision.botanica.ui.utils;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 5/27/13
 * Time: 12:00 PM
 * Local user input validation
 */
public class ValidationUtils {
    public static boolean isValidEmailAdress(String address) {
        return (address.contains("@") && address.contains("."));
    }
}
