package com.mykholy.myuniversity.Service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.NotificationHelper;

import java.util.ArrayList;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            if (remoteMessage.getData().size() > 0) {
                ArrayList<String> key = new ArrayList<>(remoteMessage.getData().keySet());
                if (Constants.getSPreferences(getApplicationContext()).getSTUDENT_ACADEMIC_YEAR() ==
                        Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get(key.get(1))))) {
                    if (Constants.getSPreferences(getApplicationContext()).getSTUDENT_DEPT_ID() ==
                            Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get(key.get(0))))) {

                       NotificationHelper.displayNotification(this, title, body);
                    }


                }


                Log.i("for_key:", "data string:" + remoteMessage.getData().toString());

            }

        }

    }
}
