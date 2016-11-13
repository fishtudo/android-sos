package api.util;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by Carlos on 13/11/2016.
 */
public class AndroidUtils {

    public static Drawable paintDrawable(Drawable d, int color){
        PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
        d.setColorFilter(color,mMode);
        return d;
    }
}
