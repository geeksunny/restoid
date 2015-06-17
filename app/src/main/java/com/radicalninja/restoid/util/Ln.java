package com.radicalninja.restoid.util;

import android.util.Log;

/**
 * Ln is a "Natural Log" class, meant to make Android logging easier than
 * it currently is. Based off of RoboGuice's Ln and it's method signatures,
 * I've simplified the implementation and removed its dependency on the Injection
 * framework.
 *
 * @author dan
 */
public class Ln {
    private static int LOG_LEVEL = 3;

    public static void wtf(Throwable t) {
        printLn(Log.ASSERT, Log.getStackTraceString(t));
    }

    public static void wtf(String message, Object... formatArgs) {
        printLn(Log.ASSERT, String.format(message, formatArgs));
    }

    public static void wtf(Throwable t, String tag, String message, Object... formatArgs) {
        printLn(Log.ASSERT, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }

    public static void e(Throwable t) { //TODO: should return 1
        printLn(Log.ERROR, Log.getStackTraceString(t));
    }

    public static void e(String message, Object... formatArgs) {  //TODO: should return 1
        printLn(Log.ERROR, String.format(message, formatArgs));
    }

    public static void e(Throwable t, String tag, String message, Object... formatArgs) {  //TODO: should return 1
        printLn(Log.ERROR, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }

    public static void w(Throwable t) {
        printLn(Log.WARN, Log.getStackTraceString(t));
    }

    public static void w(String message, Object... formatArgs) {
        printLn(Log.WARN, String.format(message, formatArgs));
    }

    public static void w(Throwable t, String tag, String message, Object... formatArgs) {
        printLn(Log.WARN, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }


    public static void i(Throwable t) {
        printLn(Log.INFO, Log.getStackTraceString(t));
    }

    public static void i(String message, Object... formatArgs) {
        printLn(Log.INFO, String.format(message, formatArgs));
    }

    public static void i(Throwable t, String tag, String message, Object... formatArgs) {
        printLn(Log.INFO, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }

    public static void d(Throwable t) {
        printLn(Log.DEBUG, Log.getStackTraceString(t));
    }

    public static void d(String message, Object... formatArgs) {
        printLn(Log.DEBUG, String.format(message, formatArgs));
    }

    // added this method so that anything can be passed in, not just Strings
    public static void d(Object object) {
        printLn(Log.DEBUG, object != null ? object.toString() : "null");
    }

    public static void d(Throwable t, String tag, String message, Object... formatArgs) {
        printLn(Log.DEBUG, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }

    public static void v(Throwable t) {
        printLn(Log.VERBOSE, Log.getStackTraceString(t));
    }

    public static void v(String message, Object... formatArgs) {
        printLn(Log.VERBOSE, String.format(message, formatArgs));
    }

    public static void v(Throwable t, String tag, String message, Object... formatArgs) {
        printLn(Log.VERBOSE, String.format(message, formatArgs) + "\n" + Log.getStackTraceString(t));
    }

    private static void printLn(int priority, String msg) {
        if (priority >= LOG_LEVEL) {
            Log.println(priority, getTag(), msg);
        }
    }

    private static String getTag() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length > 6) {
            String[] packagePieces = stackTraceElements[5].getClassName().split("\\.");
            return "Ln-"+packagePieces[packagePieces.length - 1];
        } else {
            return "";
        }
    }

    /**
     * Sets the level of log statements that print (default is 3):
     *
     *  2 = verbose
     *  3 = debug
     *  4 = info
     *  5 = warn
     *  6 = error
     *  7 = assert
     *
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        LOG_LEVEL = logLevel;
    }
}