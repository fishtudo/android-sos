package api.activities;

import android.app.Activity;
import android.content.Intent;

public interface ActivityResultObserver {

    public void onActivityResult(int requestCode, int resultCode, Intent data);
}
