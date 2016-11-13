package api.callback;

import android.app.Activity;
import android.graphics.Bitmap;

public interface PictureTakeActionCallback<T extends Activity> {
	void onPictureSelected(Bitmap result);

	void updateContextActivity(T activity);
}
