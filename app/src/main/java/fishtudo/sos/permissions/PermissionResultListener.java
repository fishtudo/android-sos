package fishtudo.sos.permissions;

import android.support.annotation.NonNull;

/**
 * Created by Carlos on 14/10/2016.
 */
public interface PermissionResultListener {

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

}
