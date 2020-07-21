package com.chenganrt.smartSupervision.business.electronic.ui;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PhotoTools {
    public static PhotoTools ins = new PhotoTools();

    public final static String[] imageColumns = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.SIZE};

    String cropPhotoString = null;

    public String getFilePath() {
        return cropPhotoString;
    }

    private Uri cameraUri;

    public Uri getLastUri() {
        Uri ret = cameraUri;
        cameraUri = null;
        return ret;
    }

    @SuppressLint("NewApi")
    public Uri getUseableUri(Context context, Uri oldUri) {
        String filePath = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, oldUri)) {
            String wholeID = DocumentsContract.getDocumentId(oldUri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                    new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        } else if ("content".equalsIgnoreCase(oldUri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(oldUri,
                    projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
        } else if ("file".equalsIgnoreCase(oldUri.getScheme())) {
            filePath = oldUri.getPath();
        }
        return Uri.fromFile(new File(filePath));
    }

    public Intent getGalleryIntent() {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*"); // 设置文件类型
//		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentFromGallery.setAction(Intent.ACTION_PICK);
        intentFromGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intentFromGallery;
    }

    public Intent getCameraIntent(Context context) {
        Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "testing");
        values.put(MediaStore.Images.Media.DESCRIPTION, "this is description");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri tmp = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, tmp);
        cameraUri = getUseableUri(context, tmp);

        return intentFromCapture;
    }

    public Uri parseGallerySuccessUri(Context ctx, Intent arg2) {
        Uri olduor = arg2.getData();
        if (olduor == null) {
            return null;
        } else {
            return getUseableUri(ctx, olduor);
        }
    }

    public Intent getCropIntent(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        //intent.putExtra("return-data", true);

        File photoFile = new File(getAlbumStorageDir("ebpp"), "ebpp_avater" + System.currentTimeMillis() + ".jpg");
        cropPhotoString = photoFile.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        return intent;
    }

    public void saveCropPhoto(Bitmap photo) {
        try {
            File photoFile = new File(getAlbumStorageDir("ebpp"), "ebpp_avater" + System.currentTimeMillis() + ".jpg");
            cropPhotoString = photoFile.getAbsolutePath();
            saveBitmapToJPG(photo, photoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.  
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {

        }
        return file;
    }


    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while (os.toByteArray().length / 1024 > maxSize) {
            os.reset();
            options -= 2;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
        os.close();
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     * @throws IOException
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static String getPathByUri(Context context, Uri uri) {
        String path = "";
        Cursor cursor = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                    && DocumentsContract.isDocumentUri(context, uri)) {
                String wholeID = DocumentsContract.getDocumentId(uri);
                String id = wholeID.split(":")[1];
                String sel = MediaStore.Images.Media._ID + "=?";
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        imageColumns, sel, new String[]{id}, null);
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndexOrThrow(imageColumns[0]));
            } else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
                cursor = context.getContentResolver().query(uri, imageColumns, null, null, null);
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndexOrThrow(imageColumns[0]));
            } else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
                path = uri.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * 文件转base64字符串
     *
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }
}
