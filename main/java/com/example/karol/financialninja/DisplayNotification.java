package com.example.karol.financialninja;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Karol on 2017-03-15.
 */

public class DisplayNotification {

    //used for log out
    public boolean notificationActive = false;
    public boolean secondRun = false;
    public int timeToDisplayQuote = 20000;
    public int currentDisplayTime;

    //timer to be held by the notification objects as it is used by them
    private static CountDownTimer quoteTimer;
    public static CountDownTimer getQuoteTimer() {
        return quoteTimer;
    }
    public static void setQuoteTimer(CountDownTimer quoteTimer) {
        DisplayNotification.quoteTimer = quoteTimer;
    }


    private DisplayNotification(){

    }
    private static DisplayNotification notificationServiceInstance;

    public static DisplayNotification getNotificationServiceInstance() {
        if (notificationServiceInstance == null) {
            notificationServiceInstance = new DisplayNotification();
        }
        return notificationServiceInstance;
    }

    //serial code is used for notifying notification manager we are updating it rather than create new service
    private int serialCode = 667;
    private  NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }


    //methods for using notifications
    public void showNotification(Context context, String quote){
        //when this method starts notification gets activated
        notificationActive = true;

        mBuilder =  new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentTitle("Notification time")
                .setContentText(quote)
                .setOngoing(true)
                .setPriority(2)
                .setVisibility(1);

        //intent is used when user wants to get back to teh application from the notificatuion
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

    public void resetNotification(){
            notificationActive = false;

            if (mNotificationManager != null) {
                mNotificationManager.cancelAll();
                mNotificationManager = null;
            }
        notificationServiceInstance = null;
    }
}
