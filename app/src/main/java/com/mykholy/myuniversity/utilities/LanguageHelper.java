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
}
