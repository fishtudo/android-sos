package api.callback;

import android.app.Activity;
import android.graphics.Bitmap;

public interface PictureTakeActionCallback<T extends Activity> {
	public void onPictureSelected(Bitmap result);

	public void updateContextActivity(T activity);
}
