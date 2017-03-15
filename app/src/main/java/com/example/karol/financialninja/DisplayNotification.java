package com.example.karol.financialninja;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Karol on 2017-03-15.
 */

public class DisplayNotification {

    public boolean notificationActive = false;

    private DisplayNotification(){

    }
    private static DisplayNotification notificationServiceInstance;

    public static DisplayNotification getNotificationServiceInstance() {
        if (notificationServiceInstance == null) {
            notificationServiceInstance = new DisplayNotification();
        }
        return notificationServiceInstance;
    }

    private int serialCode = 667;
    private  NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public void showNotification(Context context, String quote){
        notificationActive = true;

        mBuilder =  new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentTitle("Notification time")
                .setContentText(quote)
                .setOngoing(true)
                .setPriority(2)
                .setVisibility(1);

        Intent resultIntent = new Intent(context, MainHome_Activity.class);
        resultIntent.setAction("android.intent.action.VIEW");
        resultIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);


        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(serialCode, mBuilder.build());
    }

    public void updateNotification (Context context, String time, String quote) {
        mBuilder.setContentTitle(time);
        mBuilder.setContentText(quote);
        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(serialCode, mBuilder.build());
    }

}
