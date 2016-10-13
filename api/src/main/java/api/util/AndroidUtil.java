package api.util;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

public class AndroidUtil {
    public static void closeKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void openKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static float convertDpToPixel(Resources resorces, int sizeInDp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp,
                resorces.getDisplayMetrics());
    }

    public static float convertSpToPixel(Resources resorces, int sizeInDp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizeInDp,
                resorces.getDisplayMetrics());
    }
}
