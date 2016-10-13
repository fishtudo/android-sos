package api.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class MessageDialog extends DialogFragment {
	public static String TAG = MessageDialog.class.getSimpleName();
	private String mText = "";
	private String mTitle = null;
	private String mOkButtonText = null;
	private OnClickListener mOkButtonListener;
	private String mCancelButtonText = null;
	private OnClickListener mCancelButtonListener;

	public MessageDialog() {

	}

	public void setText(String text) {
		mText = text;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		if (mTitle != null)
			alertDialogBuilder.setTitle(mTitle);
		alertDialogBuilder.setMessage(mText);
		if (mOkButtonText != null)
			alertDialogBuilder.setPositiveButton(mOkButtonText,
					mOkButtonListener);
		if (mCancelButtonText != null)
			alertDialogBuilder.setNegativeButton(mCancelButtonText,
					mCancelButtonListener);

		return alertDialogBuilder.create();
	}

	public void setOKButton(String okButtonText, OnClickListener listener) {
		mOkButtonText = okButtonText;
		mOkButtonListener = listener;
	}

	public void setCancelButton(String cancelButtonText,
			OnClickListener cancelButtonListener) {
		mCancelButtonText = cancelButtonText;
		mCancelButtonListener = cancelButtonListener;
	}
}