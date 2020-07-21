package com.android.commonlib.okhttp;

import android.os.Environment;

import java.io.File;

public final class NetConfig {
    private static final String TEST_FILE_NAME = ".testfeedback123456";

    private static final String TEST_SERVER = "http://183.233.90.234:9527/ssitg-constructionwaste";
    private static final String PRODUCT_SERVER = "http://183.233.90.234:9527/ssitg-constructionwaste";

    private static String sServerUrl;

    static {
        if (isExsitTestFile()) {
            sServerUrl = TEST_SERVER;
        } else {
            sServerUrl = PRODUCT_SERVER;
        }
    }

    static String getServerAddress() {
        return sServerUrl;
    }

    private static boolean isExsitTestFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootPath = Environment.getExternalStorageDirectory().getPath();
            File testFile = new File(rootPath + File.separator + TEST_FILE_NAME);
            if (testFile.exists()) {
                return true;
            }
        }
        return false;
    }
}
