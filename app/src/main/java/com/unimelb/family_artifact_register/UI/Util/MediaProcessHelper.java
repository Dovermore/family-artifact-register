package com.unimelb.family_artifact_register.UI.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-18 23:06:53
 * @description methods used when process images in android pure fabricated
 */
public class MediaProcessHelper {
    /**
     * image type constant
     */
    public static final int TYPE_IMAGE = 1;

    /**
     * video type constant
     */
    public static final int TYPE_VIDEO = 2;

    /**
     * class tag
     */
    private static final String TAG = MediaProcessHelper.class.getSimpleName();

    /**
     * @param context      context
     * @param image        image address
     * @param deleteSource delete original or not
     * @return compressed image address
     */
    public static Uri compressUriImage(Context context, Uri image, boolean deleteSource) {
        // store in app cache directory
        File storageDir = new File(context.getCacheDir().getPath() + "/EasyImage/");
        return Uri.parse(SiliCompressor.with(context).compress(image.getPath(), storageDir, deleteSource));
    }

    /**
     * @param bmp bitmap original
     * @return bitmap center cropped
     */
    public static Bitmap cropCenter(Bitmap bmp) {
        int dimension = Math.min(bmp.getWidth(), bmp.getHeight());
        return ThumbnailUtils.extractThumbnail(bmp, dimension, dimension);
    }

    /**
     * @param bm        bitmap original
     * @param newWidth  new width
     * @param newHeight new height
     * @return bitmap resized
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bm, newWidth, newHeight, true);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * @param context context
     * @param video   video original
     * @return video compressed
     * @throws URISyntaxException
     */
    public static Uri compreUriVideo(Context context, Uri video) throws URISyntaxException {
        String storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                .getPath();
        return Uri.parse(SiliCompressor.with(context).compressVideo(video.getPath(), storageDir));
    }

    /**
     * @param uriFrom from
     * @return to
     */
    public static File copyFileToExternal(Uri uriFrom) {
        File fileFrom = new File(uriFrom.getPath());
        String fileTo = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileFrom.getName();
        System.out.println("copy file - from: " + fileFrom.toString() + " to: " + fileTo);

        copyFile(uriFrom.getPath(), fileTo);

        return new File(fileTo);
    }

    /**
     * @param inputPath  from
     * @param outputPath to
     */
    public static void copyFile(String inputPath, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

            Log.d(TAG, "Copied file to " + outputPath);

        } catch (Exception fnfe1) {
            System.out.println("error !!!");
        }
    }

    /**
     * @return created video file object
     */
    public static File createVideoFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "VID_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File videoFile = null;
        try {
            videoFile = File.createTempFile("Compressed_" + videoFileName, ".mp4", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoFile;
    }

    /**
     * @param options image option
     * @return size
     */
    private static int calculateInSampleSize(BitmapFactory.Options options) {
        int height = options.outHeight;
        int width = options.outWidth;
        // default pixel percentage ，change to original's 1/2
        int inSampleSize = 2;
        // original's min length
        int minLen = Math.min(height, width);
        // if original's min length > 100dp Note: unit should be dp not px
        if (minLen > 100) {
            // calculate ratio to be compressed
            float ratio = (float) minLen / 100.0f;
            inSampleSize = (int) ratio;
        }

        return inSampleSize;
    }

    /**
     * @param imagePath image address in phone
     * @return image option
     */
    public static BitmapFactory.Options getCompressImageOption(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // only access image's size info, not read whole image into memory so that won't memory leak
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        // after compressed, can to load original
        options.inJustDecodeBounds = false;
        // set the compress ratio just calculated
        options.inSampleSize = calculateInSampleSize(options);

        return options;
    }

    /**
     * compress String path is easy. But compress from InputStream is tricky.
     * https://stackoverflow.com/questions/39316069/bitmapfactory-decodestream-from-assets-returns-null-on-android-7
     *
     * @param context  context
     * @param imageUri image
     * @return image option
     * @throws FileNotFoundException
     */
    public static BitmapFactory.Options getCompressImageOption(Context context, Uri imageUri) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        // only access image's size info, not read whole image into memory so that won't memory leak
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        // after compressed, can to load original
        options.inJustDecodeBounds = false;
        // set the compress ratio just calculated
        options.inSampleSize = calculateInSampleSize(options);

        return options;
    }
}
