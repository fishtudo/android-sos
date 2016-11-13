package fishtudo.sos.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Carlos on 13/11/2016.
 */
public class FTTextView extends TextView{

    String fontName = "fonts/helvetica_ normal.ttf";

    public FTTextView(Context context) {
        super(context);
        initFont();
    }

    public FTTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public FTTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FTTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initFont();
    }

    private void initFont(){
        setTypeface(Typeface.createFromAsset(getContext().getAssets(),fontName));
    }
}
