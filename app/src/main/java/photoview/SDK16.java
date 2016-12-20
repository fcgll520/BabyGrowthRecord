package photoview;

import android.annotation.TargetApi;
import android.view.View;

/**
 * Created by asus on 2016/12/20.
 */

@TargetApi(16)
public class SDK16 {
    public static void postOnAnimation(View view, Runnable r) {
        view.postOnAnimation(r);
    }
}
