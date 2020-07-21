package com.chenganrt.smartSupervision.business.electronic.util;


/**
 * Created by Administrator on 2019/3/11.
 */

public class AppConstant {

    public enum CompileEnv {
        TEST_SELF,
        TEST_PUBLIC,
        RELEASE
    }

    public static CompileEnv CURENV = CompileEnv.TEST_PUBLIC;

    /**
     * 运行模式 DEBUG = 1; INFO = 2; WARN = 3; ERROR = 4; 优先�?DEBUG < INFO < WARN <6
     * ERROR
     */
    public static final int runningMode = AppTools.isDebug() ? 1 : 11;

    public static final int HTTP_CONNECT_TIMEOUT = 50;
    public static final int HTTP_READ_TIMEOUT = 50;
    public static final int HTTP_WRITE_TIMEOUT = 50;
    public static final int HTTP_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    /*
     * 任务运行状态
     */
    public static final class TaskState {
        public static final int SUCCESS = 0x1111;// 任务成功
        public static final int FAILURE = 0x1112;// 任务失败
        public static final int ISRUNING = 0x1113;// 任务正在运行
        public static final int PAUSE = 0x1114;// 任务暂停
        public static final int EXCEPITON = 0x1115;// 任务异常
        public static final int TOKENEXPIRE = 0x1116;// Token过期
        public static final int CODEEXCEPTION = 0x1117;// 任务异常
        public static final int EMPTY = 0x1118;// 空记录
    }

    public static final String RESULT_CODE_SUCCESS = "00000";
    public static final String RESULT_CODE_EMPTY = "00008";
    public static final String PLATFORM_ANDROID = "0";

    public static boolean appIsLaunch = false;

    public static final String CP_USER_AGENT = "&channel=cp&plantform=android";

}
