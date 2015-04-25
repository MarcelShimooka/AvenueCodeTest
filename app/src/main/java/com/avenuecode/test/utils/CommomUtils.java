package com.avenuecode.test.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Marcel on 24/04/2015 22:52
 */
public class CommomUtils {

    /**
     * Hides the soft keyboard on the screen
     */
    public static boolean hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            boolean b = imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return b;
        }
        return false;
    }
}