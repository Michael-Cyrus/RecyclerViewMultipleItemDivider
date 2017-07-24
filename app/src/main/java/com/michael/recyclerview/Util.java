package com.michael.recyclerview;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by chenyao on 2017/7/24.
 */

public class Util {

    public static int getPhoneWidth(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }
}
