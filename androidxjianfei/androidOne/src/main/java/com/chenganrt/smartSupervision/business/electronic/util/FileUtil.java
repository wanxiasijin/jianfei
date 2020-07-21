package com.chenganrt.smartSupervision.business.electronic.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

public class FileUtil {

    private static final String DIR_UPDATE = "update";
    private static final String DIR_DOWNLOAD = "download";
    private static final String DIR_IMAGE = "image";
    private static final String DIR_ICON = "icon";
    private static final String DIR_CACHE = "cache";

    /**
     * 初始化项目目录
     */
    public static void initFileCache(Context context) {
        //创建项目目录
        createDir(getExternalStoragePath(context));
        //创建上传目录
        createDir(getUploadDir(context));
        //创建下载目录
        createDir(getDownloadDir(context));
        //创建系统一级下载目录，在有些手机上可能会缺失
        createDir(getExternalStoragePublicDirectoryDownload());
        createDir(getExternalStoragePublicDirectoryPicture());
    }

    public static String getExternalStoragePublicDirectoryDownload() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    public static String getExternalStoragePublicDirectoryPicture() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * 获取sd卡目录
     *
     * @return
     */
    public static String getExternalStoragePath(Context context) {
       // LogUtil.d("getExternalStoragePath context::" + context);
        String storageState = Environment.getExternalStorageState();
        String cacheDir = "";
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = Environment.getExternalStorageDirectory().getPath();
        } else {
       //     LogUtil.d("no sdcard");
            cacheDir = context.getCacheDir().getPath();
        }
        return cacheDir + File.separator + "sz_elect" + File.separator;
    }

    /**
     * 创建目录
     */
    public static void createDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
//                if (file.mkdirs()) {
//                    LogUtil.d("file " + path + " is created");
//                } else {
//                    LogUtil.d("file " + path + " created faild");
//                }
            } else {
              //  LogUtil.d("file " + path + " is exists");
            }
        } else {
            if (file.mkdirs()) {
             //   LogUtil.d("file " + path + " is created");
            } else {
           //     LogUtil.d("file " + path + " created faild");
            }
        }
    }

    /**
     * 获取上传目录
     */
    public static String getUploadDir(Context context) {
        return getExternalStoragePath(context) + DIR_UPDATE + File.separator;
    }

    /**
     * 获取下载目录
     *
     * @return
     */
    public static String getDownloadDir(Context context) {
        return getExternalStoragePath(context) + DIR_DOWNLOAD + File.separator;
    }

    /**
     * 获取银行图标目录
     *
     * @return
     */
    public static String getIconDir(Context context) {
        return getExternalStoragePath(context) + DIR_ICON + File.separator;
    }

    /**
     * 获取banner的本地路径
     *
     * @param pictureUrl
     * @return
     */
    public static String getBannerFileName(String pictureUrl) {
        return AppTools.getMD5String(pictureUrl) + "_banner";
    }

    public static String getImageDir(Context context) {
        File file = new File(getExternalStoragePath(context) + DIR_IMAGE);
        if (!file.exists()) file.mkdirs();
        return file.getAbsolutePath();
    }

    public static File getCacheDir(Context context) {
        File file = new File(getExternalStoragePath(context) + DIR_CACHE);
        if (!file.exists()) file.mkdirs();
        return file;
    }

    public static Bitmap getBitmapFromFile(String filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(filePath);
        } catch (Exception e) {
            //LogUtil.e(e.getMessage());
        }
        return bitmap;
    }

    public static String readAllAssetsTxt(Context ctx, String filename) {
        try {
            InputStream is = ctx.getAssets().open(filename);

            StringBuffer sb = new StringBuffer();
            String tmp = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((tmp = reader.readLine()) != null) {
                sb.append(tmp);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(File oldPath, File newPath) throws IOException {
        Sink sink = Okio.sink(newPath);
        BufferedSource source = Okio.buffer(Okio.source(oldPath));
        source.readAll(sink);
    }

    public static boolean isImage(String path) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            return options.outWidth > 0 && options.outHeight > 0;
        } catch (Throwable e) {
            return false;
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable var2) {
                ;
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable var2) {
                ;
            }
        }
    }
}
