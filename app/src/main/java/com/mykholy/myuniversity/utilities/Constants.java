package com.mykholy.myuniversity.utilities;

import android.content.Context;

public class Constants {
    private static SPreferences sPreferences;

    public static final SPreferences getSPreferences(Context context) {
        if (sPreferences == null) {
            sPreferences = new SPreferences(context);
        }
        return sPreferences;
    }
}
