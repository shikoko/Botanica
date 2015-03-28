package com.softvision.botanica.bl.misc;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 12:41 PM
 * App Settings
 */
public class Settings {
    //prod env
    public static final String API_BASE_URL = "http://192.168.114.81/api/";
    public static final String API_METHOD_INFO = "info";
    public static final String API_METHOD_PLANTS = "plants";

    public static String apiBaseUrl() {
        return API_BASE_URL;
    }
}
