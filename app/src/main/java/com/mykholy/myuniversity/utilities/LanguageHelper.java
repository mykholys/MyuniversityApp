package com.mykholy.myuniversity.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {

    public static void setLanguage(Activity activity, String lang) {
        Locale locale_new = new Locale(lang);
        Locale.setDefault(locale_new);
        Resources res = activity.getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        newConfig.locale = locale_new;
        newConfig.setLayoutDirection(locale_new);
        res.updateConfiguration(newConfig, res.getDisplayMetrics());

        Constants.getSPreferences(activity).setLanguage(lang);

    }
    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }
}
