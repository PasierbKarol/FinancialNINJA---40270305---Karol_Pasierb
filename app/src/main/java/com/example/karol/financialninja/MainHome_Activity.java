package com.example.karol.financialninja;

import android.app.ActivityManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
 * Probably the most complex activity in the whole app.
 * This activity is responsible for displaying quotes to the user.
 * If this is the new user there wil be a message to inform about the quotes.
 * There is a time set for displaying quotes and user may change that with the settings button.
 * At the end of the time the quote will change and the sound will be played.
 *
 * From here user may go to see the list of all quotes
 * Or user may choose to add more quotes
 *
 * This activity starts the constant notification at the Notifications drawer
 * which displays the current quote and time left until next one
 *
 * Last Update: 15/03/2017
 */

public class MainHome_Activity extends AppCompatActivity {

    //reference variables for later use
    User_Singleton currentUser;
    CountDownTimer quoteTimer;
    int timeToDisplayQuote = 60000;
    int quoteToDisplay = 0;
    int quotesTotal;
    TextView quoteOfTheDay;
    TextView quoteToRememberTitle;
    MediaPlayer soundEffectPlayer;
    boolean first_run = false;
    String displayedQuote;
    DisplayNotification notificationService;


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


        //accessing UI elements
        TextView welcomeBackTitle = (TextView) findViewById(R.id.mainHomeTitle);
        quoteOfTheDay = (TextView) findViewById(R.id.quoteOfTheDay);
        quoteToRememberTitle = (TextView) findViewById(R.id.quoteToRememberTitle);
        quoteToRememberTitle.animate().alpha(1f).setDuration(1000);

        welcomeBackTitle.append("Welcome back Ninja " + currentUser.getName().toString());

        checkQuotes();
        //checking if this activity was started after user created account or logged in
        //Then notification starts, if user comes back here notification remains the same

    }
    @Override
    protected void onResume() {
        super.onResume();
        //checkQuotes();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

   /* //method for checking if the service was previously started
    private boolean isMyServiceRunning(Class<FinancialNinja_TimeService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
*/

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


    boolean newTimeSet = false;
    //this method sets the new time for displaying quotes and starts the timer
    public void setNewQuoteTime(View view) {
        EditText setNewQuoteTime = (EditText) findViewById(R.id.setQuoteTime);
        newTimeSet = true;
        timeToDisplayQuote = (Integer.parseInt(setNewQuoteTime.getText().toString())*1000)+100;
        startingNotification();
        setNewQuoteTime.setText("");
    }

    private   void startingTimerForQuotes(int timeToDisplayQuote){

        //checking if there is a timer running and cancelling it before continue
        if (quoteTimer != null) {
            quoteTimer.cancel();
        }

        //creating the notification


        //starting new timer
        quoteTimer = new CountDownTimer(timeToDisplayQuote, 1000) {
        TextView timeLeftForDisplayingQuote = (TextView) findViewById(R.id.timeLeftForDisplayingQuote);



            //actions to happen for every second tick
            @Override
            public void onTick(long millisUntilFinished) {
                if(notificationService.notificationActive == false){
                    this.cancel();
                }
                int hours = (int) (millisUntilFinished/10000)/60;
                int minutes = (int) (millisUntilFinished/1000)/60;
                int seconds = (int) (millisUntilFinished/1000)%60;

                //setting display of seconds to look nice
                String secondsString = Integer.toString(seconds);
                if  (secondsString == "0")
                {
                    secondsString = "00";
                } else if (seconds <= 9)
                {
                    secondsString = "0" + seconds;
                }

                timeLeftForDisplayingQuote.setText(String.format("%02d:%02d:%02d", millisUntilFinished/1000 / 3600,
                        (millisUntilFinished/1000 % 3600) / 60, (millisUntilFinished/1000 % 60)));

                /*timeLeftForDisplayingQuote.setText("Time until next quote " +
                        Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + secondsString);*/

                //updating the content of the notification with the remaining time

                notificationService.updateNotification(MainHome_Activity.this, timeLeftForDisplayingQuote.getText().toString(), displayedQuote);
            }

            @Override
            public void onFinish() {
            //when the timer finishes we change the quote to display
                quoteToDisplay++;
                if (quoteToDisplay >= quotesTotal) {
                    quoteToDisplay = 0;
                }
                Log.i("Karol","quote is " + displayedQuote);
                changeQuoteWithSound();
                notificationService.updateNotification(MainHome_Activity.this, timeLeftForDisplayingQuote.getText().toString(), displayedQuote);
                Log.i("Karol","quote after is " + displayedQuote);
                //starting the next timer
                this.start();
            }
        }.start();
    }


    //method that will perform the change animation and play the sound
    private   void changeQuoteWithSound(){
        soundEffectPlayer = MediaPlayer.create(this, R.raw.computermagic);
        soundEffectPlayer.start();
        quoteOfTheDay.animate().alpha(0f).setDuration(500);
        displayedQuote = currentUser.getPersonalQuotes().get(quoteToDisplay);
            quoteOfTheDay.setText(displayedQuote);
            quoteOfTheDay.animate().alpha(1f).setDuration(500).setStartDelay(600);

    }

    //in case if this is the new user or fresh run of the application
    //check is performed to see if there are any quotes for the current user
    public void checkQuotes(){
        quotesTotal = currentUser.getPersonalQuotes().size();

        if (quotesTotal == 0) {
            //if there are no quotes Texview for displaying them will inform user about it
            quoteOfTheDay.setText("Click Add Quotes to set the quotes to be visible here");
        } else {
            displayedQuote =  currentUser.getPersonalQuotes().get(quoteToDisplay);
            quoteOfTheDay.setText(displayedQuote);
            startingNotification();



        }
    }

    private  void startingNotification(){
        if (first_run || (first_run && quotesTotal > 0)) {
            //boolean test = isMyServiceRunning(FinancialNinja_TimeService.class);
            //Toast.makeText(this, "is my service running " + test, Toast.LENGTH_SHORT).show();
            //Log.i("Karol","is my service running " + test + " and first_run is " + first_run);

            if (notificationService == null || newTimeSet == true)
            {
                newTimeSet = false;
                notificationService = DisplayNotification.getNotificationServiceInstance();
                notificationService.showNotification(MainHome_Activity.this, displayedQuote);
                //setting up the timer for displaying quotes
                startingTimerForQuotes(timeToDisplayQuote);
            }  else {
                Log.i("Karol","Notification is NOT running");
            }

           /* if(timeService == null) {
                Intent startTimeService = new Intent(MainHome_Activity.this, FinancialNinja_TimeService.class);
                startTimeService.putExtra("quote_to_display", displayedQuote);
                startService(startTimeService);
                timeService = new FinancialNinja_TimeService();
                timeService.startNotification(displayedQuote, "Time will be here");
                //setting up the timer for displaying quotes
                startingTimerForQuotes(timeToDisplayQuote);
            } else {
                Log.i("Karol","my service is NOT running");
            }*/

        }
    }

    public void logOut(View view) {
        //checking if there is a timer running and cancelling it before continue
        if (quoteTimer != null) {
            quoteTimer.cancel();
        }
        currentUser = User_Singleton.resetUser(currentUser);
        Intent logOutToHome = new Intent(MainHome_Activity.this, WelcomeActivity.class);
        startActivity(logOutToHome);
    }
}
