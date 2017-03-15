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

public class FinancialNinja_TimeService extends Service{




    private static final int serialVersionUID = 666;
    private Notification mNotification;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private String title = "Time until next quote: ";
    private NotificationManager mNotificationManager;


    /** Called when the service is being created. */
    @Override
    public void onCreate() {



        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        Log.i("Karol" , "Service Created");


        notificationIntent = new Intent(this, MainHome_Activity.class);

        pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        String quoteToDisplay = notificationIntent.getStringExtra("quote_to_display");
        Log.i("Karol" , "quoteToDisplay received " + quoteToDisplay);
        mNotification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentTitle(title)
                .setContentText(notificationIntent.getStringExtra(quoteToDisplay))
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true).build();
        Log.i("Karol" , "notification started");



        startForeground(serialVersionUID, mNotification);
    }
    public  FinancialNinja_TimeService() {

    }

    private void startForeground() {
        startForeground(serialVersionUID, getMyActivityNotification(""));
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags , startId);

        Bundle extras = intent.getExtras();

        //just checking
        if( extras != null )
            Toast.makeText(this,extras.getString("quote_to_display"), Toast.LENGTH_SHORT).show();




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




    private Notification getMyActivityNotification(String text){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        /*pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);*/

        return new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.determinedninja)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setOngoing(true)
                .getNotification();
    }

    /**
     * This is the method that can be called to update the Notification
     */
    private void updateNotification() {
        String text = "Some text that will update the notification";

        mNotification = getMyActivityNotification(text);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(serialVersionUID, mNotification);
    }
}
