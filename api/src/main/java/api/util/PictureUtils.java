package api.util;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import api.activities.ActivityResultObserver;
import api.activities.BaseActivity;
import api.callback.PictureTakeActionCallback;
import br.mobile.api.R;

/**
 * Requires <uses-permission
 * android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *
 * @author charles
 */
public class PictureUtils {

    public static int REQUEST_IMAGE_CAPTURE = 153;
    public static int REQUEST_GALERY_IMAGE = 154;
    public static int REQUEST_CAMERA_IMAGE = 155;

    public static void startIntentToCameraOrGallery(Activity activity) {

        Intent chooserIntent = Intent.createChooser(
                getIntentToGallery(activity), activity.getResources()
                        .getString(R.string.camera_utils_galery_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                new Intent[]{getIntentToCamera(activity)});

        activity.startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }

    public static void startIntentToCameraOrGallery(BaseActivity activity,
                                                    ActivityResultObserver activityResultObserver) {
        startIntentToCameraOrGallery(activity);
        activity.addActivityResultObserver(REQUEST_IMAGE_CAPTURE,
                activityResultObserver);
    }

    public static void startIntentToCameraOrGallery(
            final BaseActivity activity, final int maxWidth,
            final int maxHeight,
            final PictureTakeActionCallback activityResultObserver) {
        startIntentToCameraOrGallery(activity);
        activity.addActivityResultObserver(REQUEST_IMAGE_CAPTURE,
                new ActivityResultObserver() {

                    @Override
                    public void onActivityResult(int requestCode,
                                                 int resultCode, Intent data) {
                        activityResultObserver.updateContextActivity(activity);
                        activityResultObserver
                                .onPictureSelected(recoverBitmapFromIntent(
                                        activity, data, maxWidth, maxHeight));
                    }
                });
    }

    public static void startIntentToCamera(final BaseActivity activity,
                                           final int maxWidth, final int maxHeight,
                                           final PictureTakeActionCallback activityResultObserver) {
        activity.addActivityResultObserver(REQUEST_CAMERA_IMAGE,
                new ActivityResultObserver() {

                    @Override
                    public void onActivityResult(int requestCode,
                                                 int resultCode, Intent data) {
                        activityResultObserver.updateContextActivity(activity);
                        activityResultObserver
                                .onPictureSelected(recoverBitmapFromIntent(
                                        activity, data, maxWidth, maxHeight));
                    }

                });
        activity.startActivityForResult(getIntentToCamera(activity),
                REQUEST_CAMERA_IMAGE);
    }

    public static void startIntentToGallery(final BaseActivity activity,
                                            final int maxWidth, final int maxHeight,
                                            final PictureTakeActionCallback activityResultObserver) {
        activity.startActivityForResult(getIntentToGallery(activity),
                REQUEST_GALERY_IMAGE);

        activity.addActivityResultObserver(REQUEST_GALERY_IMAGE,
                new ActivityResultObserver() {
                    @Override
                    public void onActivityResult(int requestCode,
                                                 int resultCode, Intent data) {
                        activityResultObserver.updateContextActivity(activity);
                        activityResultObserver
                                .onPictureSelected(recoverBitmapFromIntent(
                                        activity, data, maxWidth, maxHeight));
                    }
                });
    }

    private static Intent getIntentToGallery(Activity activity) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return pickPhoto;
    }

    private static Intent getIntentToCamera(Activity activity) {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public static Bitmap changeImageColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    public static Bitmap changeImageColor(Drawable sourceBitmap, int color) {
        return changeImageColor(convertDrawableToBitmap(sourceBitmap), color);
    }

    public static Bitmap changeImageColor(Context context, int resource, int color) {
        return changeImageColor(context.getResources().getDrawable(resource), color);
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static Bitmap recoverBitmapFromIntent(Activity activity,
                                                 Intent data, int maxWidth, int maxHeight) {
        if (data == null || activity == null)
            return null;

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return decodeBitmap(picturePath, maxWidth, maxHeight);
    }

    public static Bitmap decodeBitmap(String caminho, int reqWidth,
                                      int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(caminho, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(caminho, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap cutBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        if (bitmap == null)
            return null;

        int xExpression = (bitmap.getWidth() - maxWidth) / 2;
        int yExpression = (bitmap.getHeight() - maxHeight) / 2;

        int beginnigX = xExpression > 0 ? xExpression : 0;
        int begginingY = yExpression > 0 ? yExpression : 0;
        int enddingX = xExpression > 0 ? maxWidth : bitmap.getWidth();
        int enddingY = yExpression > 0 ? maxHeight : bitmap.getHeight();

        return Bitmap.createBitmap(bitmap, beginnigX, begginingY, enddingX,
                enddingY, null, false);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

	/*
    * Warning rgb color is not android id color
	* it should be for example Color.BLUE
	*/

    public static Bitmap createBitmapFromSingleColor(int rgbColor, int width, int height) {
        int[] colorCollection = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            colorCollection[i] = rgbColor;
        }

        return Bitmap.createBitmap(colorCollection, width, height, Bitmap.Config.ARGB_8888);
    }

    public static Bitmap createBitmapFromSingleColor(int rgbColor) {
        return createBitmapFromSingleColor(rgbColor, 300, 300);
    }

    public static String encodeTobase64(Resources resources, int image) {
        return encodeTobase64(BitmapFactory.decodeResource(resources, image));
    }

    public static String encodeTobase64(Bitmap image) {
        if (image == null) {
            return "";
        }

        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        if (input == null)
            return null;
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}