package catvinhquang.qkit;

import android.content.Context;

/**
 * Created by Quang Cat on 22/02/2018.
 */

public class Utils {

    public static int dpToPx(Context context, float dp) {
        return (int) Math.ceil(dp * context.getResources().getDisplayMetrics().density);
    }

}
