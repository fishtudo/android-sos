package api.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class HardwareUtils {
	/**
	 * Check if this device has a camera
	 */
	public static boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	public static int getScreenHeight(Context contextActivity) {
		return contextActivity.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getScreenWidth(Context contextActivity) {
		return contextActivity.getResources().getDisplayMetrics().widthPixels;
	}
}