package api.mask;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import api.util.StringUtil;

/**
 * Created by charles on 19/09/2015.
 */
public class MonetaryTextWatcher implements TextWatcher {

    private CharSequence previousText = "";

    private EditText editText;

    public MonetaryTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence entranceString, int start, int before, int count) {
        int unmaskedNumber = StringUtil.unFormatCurrency(0 + entranceString.toString());
        String result = (unmaskedNumber == 0) ? "" : StringUtil.formatCurrency(unmaskedNumber);

        if (!result.equals(previousText)) {
            previousText = result;
            editText.setText(result);
            editText.setSelection(result.length());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
