package com.example.karol.financialninja;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Karol on 2017-03-14.
 */
/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-03-14.
 *
 * Description:
 * Service class to run the notification and changing quotes even if user is not currently active in the application.
 * Class is responsible for setting up teh notifications and updating them
 * Last Update: 15/03/2017
 */

public class FinancialNinja_TimeService extends Service{

    //reference variables
    private static final int serialVersionUID = 666;
    private Notification mNotification;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private String quoteDisplayed;
    private NotificationManager mNotificationManager;


    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

        notificationIntent = new Intent(this, MainHome_Activity.class);

        pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);


        //creating new notification
        mNotification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentTitle(" ")
                .setContentText(" ")
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true).build();

        Log.i("Karol" , "notification started");

        //start Service in teh foreground
        startForeground(serialVersionUID, mNotification);
    }

    public FinancialNinja_TimeService(){


    }

    public void startNotification(String text, String title) {
      startForeground(serialVersionUID, getMyActivityNotification(text, title));
        //updateNotification(title);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags , startId);

        //quote to display will be received from the activity that starts the Service
        Bundle extras = intent.getExtras();
        quoteDisplayed = extras.getString("quote_to_display");
        //just checking
        if( extras != null ) {
            Log.i("Karol" , "Extras if full ");
            Log.i("Karol" , extras.getString("quote_to_display"));
            Toast.makeText(this, extras.getString("quote_to_display"), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Karol" , "Extras if empty ");
        }

       //startForeground(serialVersionUID, getMyActivityNotification(quoteDisplayed, quoteDisplayed));

        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.i("Karol" , "Service started");

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        Log.i("Karol" , "Service cancelled");
        //mNotificationManager.cancelAll();
    }


    private Notification getMyActivityNotification(String text, String title){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        notificationIntent = new Intent(this, MainHome_Activity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //creating new notification
        return new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(quoteDisplayed)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true)
                .getNotification();
    }

    /**
     * This is the method that can be called to update the Notification
     */
    public void updateNotification(String title) {

        mNotification = getMyActivityNotification(quoteDisplayed, title);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(serialVersionUID, mNotification);
    }
}
