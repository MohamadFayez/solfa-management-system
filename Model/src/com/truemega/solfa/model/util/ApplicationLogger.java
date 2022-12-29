package com.truemega.solfa.model.util;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import oracle.adf.share.logging.ADFLogger;

public class ApplicationLogger {
    public static final int INFO = 0;
    public static final int WARN = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private static ADFLogger adfLogger = ADFLogger.createADFLogger("com.truemega.solfa");
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static void log(String logMsg, int logLevel) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement st = stackTraceElements[2]; // get the caller
        String className = st.getClassName();
        String methodName = st.getMethodName();
        int lineNo = st.getLineNumber();
        logMsg = sdf.format(new Date()) + " [TU] " + className + " - " + methodName + " - " + lineNo + " : " + logMsg;

        switch (logLevel) {
        case INFO:
            {
                adfLogger.info("Info -- " + logMsg);
                break;
            }
        case WARN:
            {
                adfLogger.warning("Waning -- " + logMsg);
                break;
            }
        case DEBUG:
            {
                adfLogger.info("Debug -- " + logMsg);
                break;
            }
        default:
            {
                adfLogger.severe("Error -- " + logMsg);
            }
        }

    }

    public static void main(String[] args) {
        log(" Test Log Class : ", 0);
    }
}

