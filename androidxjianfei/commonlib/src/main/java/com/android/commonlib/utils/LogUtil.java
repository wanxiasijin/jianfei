package com.android.commonlib.utils;


import android.util.Log;

import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.DEBUG;
import static android.util.Log.WARN;


public final class LogUtil {

    private static final String TAG = "FJ_";
    private static String sTag = TAG;

    private static final int METHOD_NAME_LENGTH = getTraceLog("", "", "").length();
    private static final int TRACE_STACK_POSITION = 4;
    private static final int TRACE_PRIORITY = INFO;
    private static final String TRACE_TAG = "TRACE";

    public static int DEBUG_LEVER = DEBUG;

    private LogUtil() {
        throw new RuntimeException("Stub!");
    }

    public static void d(String msg) {
        if (DEBUG >= DEBUG_LEVER) {
            Log.d(sTag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG >= DEBUG_LEVER) {
            Log.d(sTag + "." + tag, msg);
        }
    }

    public static void i(String msg) {
        if (INFO >= DEBUG_LEVER) {
            Log.i(sTag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (INFO >= DEBUG_LEVER) {
            Log.i(sTag + "." + tag, msg);
        }
    }

    public static void w(String msg) {
        if (WARN >= DEBUG_LEVER) {
            Log.w(sTag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (WARN >= DEBUG_LEVER) {
            Log.w(sTag + "." + tag, msg);
        }
    }


    public static void e(String msg) {
        if (ERROR >= DEBUG_LEVER) {
            Log.e(sTag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ERROR >= DEBUG_LEVER) {
            Log.e(sTag + "." + tag, msg);
        }
    }

    /**
     * 打印当前的类名和方法名。
     */
    public static void trace() {
        if (INFO >= DEBUG_LEVER) {
            Log.println(TRACE_PRIORITY, TRACE_TAG, getTraceLog());
        }
    }

    public static void trace(String msg) {
        if (INFO >= DEBUG_LEVER) {
            Log.println(TRACE_PRIORITY, TRACE_TAG, getTraceLog() + msg);
        }
    }

    private static String getTraceLog() {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace == null || (trace.length < TRACE_STACK_POSITION + 1)) {
            return "";
        }

        StackTraceElement ele = trace[TRACE_STACK_POSITION];
        String className = ele.getClassName();
        int dot = className.lastIndexOf('.');
        String simpleClassName = className.substring(dot + 1);
        String methodName = ele.getMethodName();

        Thread thread = Thread.currentThread();
        String threadName = thread.getName() + "(" + thread.getId() + ")";

        String traceLog = getTraceLog(threadName, simpleClassName, methodName);
        return traceLog;
    }

    private static String getTraceLog(String threadName, String simpleClassName,
                                            String methodName) {

        StringBuilder sb = new StringBuilder(threadName.length() + simpleClassName.length()
                + methodName.length() + METHOD_NAME_LENGTH);
        sb.append("[");
        sb.append(threadName);
        sb.append("][");
        sb.append(simpleClassName);
        sb.append(".");
        sb.append(methodName);
        sb.append("]");

        return sb.toString();
    }

}