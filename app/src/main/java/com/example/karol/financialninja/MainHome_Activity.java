package com.example.karol.financialninja;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the quiz info screen
 * At the basic functionality it contains only 2 buttons that allow user either to participate in the quiz or cancel
 *  This screen is purely to inform the user about the rules regarding the quiz
 *
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */

public class MainHome_Activity extends AppCompatActivity {

    User_Singleton currentUser;
    CountDownTimer quoteTimer;
    int timeToDisplayQuote = 30000;
    int quoteToDisplay = 0;
    int quotesTotal;
    TextView quoteOfTheDay;
    TextView quoteToRememberTitle;
    MediaPlayer soundEffectPlayer;
    boolean first_run = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //receiving data from previous activity and making sure it was handled without errors
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                first_run = false;
            } else {
                first_run = extras.getBoolean("first_run");
            }
        }


        setContentView(R.layout.main_home);

        //accessing user and the quotes
        currentUser = User_Singleton.getUser_Instance();
        currentUser.readQuotesOnStartup(this);
        Log.i("Karol", currentUser.getName() + " has " + currentUser.getPersonalQuotes().size() + " quotes\n Read the quotes from File");


        //accessing UI elements
        TextView welcomeBackTitle = (TextView) findViewById(R.id.mainHomeTitle);
        quoteOfTheDay = (TextView) findViewById(R.id.quoteOfTheDay);
        quoteToRememberTitle = (TextView) findViewById(R.id.quoteToRememberTitle);
        quoteToRememberTitle.animate().alpha(1f).setDuration(1000);

        welcomeBackTitle.append("Welcome back Ninja " + currentUser.getName().toString());

        FinancialNinja_TimeService timeService = new FinancialNinja_TimeService();
        boolean test = isMyServiceRunning(FinancialNinja_TimeService.class);
        Toast.makeText(this, "is my service running " + test, Toast.LENGTH_SHORT).show();
        Log.i("Karol","is my service running " + test + " and first_run is " + first_run);
        if (first_run) {
            Intent startTimeService = new Intent(MainHome_Activity.this, FinancialNinja_TimeService.class);
            startTimeService.putExtra("quote_to_display", "Welcome back Ninja");
            startService(startTimeService);

        }

        //checkQuotes();
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkQuotes();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }


    private boolean isMyServiceRunning(Class<FinancialNinja_TimeService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void addQuote(View view) {
        Intent addQuote = new Intent(MainHome_Activity.this, AddQuote.class);
        startActivity(addQuote);
    }

    public void openQuotesList(View view) {
        Intent openQuotesList = new Intent(MainHome_Activity.this, QuotesList_Activity.class);
        startActivity(openQuotesList);
    }

    public void showQuotesSettings(View view) {
        GridLayout changeSettingsForQuotes = (GridLayout) findViewById(R.id.changeSettingsForQuotes);

        if (changeSettingsForQuotes.getVisibility() == View.INVISIBLE){
            changeSettingsForQuotes.setVisibility(View.VISIBLE);
        } else {
            changeSettingsForQuotes.setVisibility(View.INVISIBLE);
        }
    }

    public void setNewQuoteTime(View view) {
        EditText setNewQuoteTime = (EditText) findViewById(R.id.setQuoteTime);
        timeToDisplayQuote = (Integer.parseInt(setNewQuoteTime.getText().toString())*1000)+100;
        startingTimerForQuotes(timeToDisplayQuote);
        setNewQuoteTime.setText("");
    }

    private   void startingTimerForQuotes(int timeToDisplayQuote){
        if (quoteTimer != null) {
            quoteTimer.cancel();
        }

        quoteTimer = new CountDownTimer(timeToDisplayQuote, 1000) {
            TextView timeLeftForDisplayingQuote = (TextView) findViewById(R.id.timeLeftForDisplayingQuote);


            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished/1000)/60;
                int seconds = (int) (millisUntilFinished/1000)%60;
                timeLeftForDisplayingQuote.setText("Time left to display current quote: " + Integer.toString(minutes) + ":" +Integer.toString(seconds));

                //updateNotification(Integer.toString(minutes) + ":" +Integer.toString(seconds));
            }

            @Override
            public void onFinish() {

                quoteToDisplay++;
                if (quoteToDisplay >= quotesTotal) {
                    quoteToDisplay = 0;
                }
                changeQuoteWithSound();
                this.start();
            }
        }.start();
    }


    private   void changeQuoteWithSound(){
        soundEffectPlayer = MediaPlayer.create(this, R.raw.computermagic);
        soundEffectPlayer.start();
        quoteOfTheDay.animate().alpha(0f).setDuration(500);
            quoteOfTheDay.setText(currentUser.getPersonalQuotes().get(quoteToDisplay));
            quoteOfTheDay.animate().alpha(1f).setDuration(500).setStartDelay(600);

    }

    public void checkQuotes(){
        quotesTotal = currentUser.getPersonalQuotes().size();
        Log.i("Karol", "quotesTotal = " + quotesTotal);
        if (quotesTotal == 0) {
            quoteOfTheDay.setText("Click Add Quotes to set the quotes to be visible here");
        } else {
            quoteOfTheDay.setText(currentUser.getPersonalQuotes().get(quoteToDisplay));
            Log.i("Karol", "quoteOfTheDay1 text = " + currentUser.getPersonalQuotes().get(quoteToDisplay));
            //setting up the timer for displaying quotes
            startingTimerForQuotes(timeToDisplayQuote);
        }
    }
}
