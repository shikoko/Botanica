package com.softvision.botanica.common.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/15/13
 * Time: 3:43 PM
 * Custom app logger.
 */
public class Log {
    private static final Logger log = Logger.getLogger("eVigilLogging");
    private static final boolean DEBUGGING = true;

    public static void v(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.OFF, tag + " -- " + message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.INFO, tag + " -- " + message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.INFO, tag + " -- " + message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.WARNING, tag + " -- " + message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.SEVERE, tag + " -- " + message);
        }
    }

    public static void c(String tag, String message) {
        if (DEBUGGING) {
            log.log(Level.CONFIG, tag + " -- " + message);
        }
    }
}
