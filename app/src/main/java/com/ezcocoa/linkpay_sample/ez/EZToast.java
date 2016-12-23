package com.ezcocoa.linkpay_sample.ez;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 *
 */
public class EZToast {

    /**
     * show toast message with str
     * @param msg message
     */
    public static void show(final Context ctx, final String msg) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast t = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        });
    }
}
