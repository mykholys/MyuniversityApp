package com.mykholy.myuniversity.utilities;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.ui.MainActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.mykholy.myuniversity.utilities.App.CHANNEL_1_ID;
import static com.mykholy.myuniversity.utilities.App.id;

public class NotificationHelper {


    public static void displayNotification(Context context, String title, String body) {
        int notification_count = Constants.getSPreferences(context).getNotificationCount() + 1;
        Constants.getSPreferences(context).setNotificationCount(notification_count);
        Log.i("NotificationCount_int:", String.valueOf(notification_count));
        Log.i("NotificationCount_in:", "yes in");
        Log.i("NotificationCount_msg:", String.valueOf(Constants.getSPreferences(context).getNotificationCount()));


        Intent mainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_log_iv)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setColor(Color.parseColor("#FF5722"))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[0])
                .build();

        NotificationManagerCompat notificationManagerCompat2 = NotificationManagerCompat.from(context);

        notificationManagerCompat2.notify(id++, notification);


    }

}
