package com.example.karol.financialninja;

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

/** Author: Karol Pasierb - Software Engineering - 40270305
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
 * Last Update: 20/03/2017
 */

public class MainHome_Activity extends AppCompatActivity {

    private static final String PREFS_NAME = "financialNinja";
    //reference variables for later use
    User_Singleton currentUser;
    CountDownTimer quoteTimer;
    DisplayNotification notificationService;
    int currentDisplayTime;
    //time we pass to the timer

    int quotesTotal;

    //textvies that will change their content
    TextView quoteOfTheDayView;
    TextView quoteToRememberTitle;
    TextView welcomeBackTitle;

    MediaPlayer soundEffectPlayer;
    boolean first_run = false;
    boolean newTimeSet = false;

    //content of the current quote to be displayed
    String displayedQuote;


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
    }
    @Override
    protected void onResume() {
        super.onResume();

        //accessing UI elements
        welcomeBackTitle = (TextView) findViewById(R.id.mainHomeTitle);
        quoteOfTheDayView = (TextView) findViewById(R.id.changingQuoteOfTheDay);
        quoteToRememberTitle = (TextView) findViewById(R.id.quoteToRememberTitle);
        quoteToRememberTitle.animate().alpha(1f).setDuration(1000);

        //accessing user and reading quotes
        currentUser = User_Singleton.getUser_Instance();
        currentUser.readQuotesOnStartup(this);

        //creating notification or getting its instance if already created
        notificationService = DisplayNotification.getNotificationServiceInstance();
        //if previous timer exists we assign it to the variable in this activity
        quoteTimer = DisplayNotification.getQuoteTimer();


        welcomeBackTitle.append("Welcome back Ninja " + currentUser.getName().toString());
        //this method checkes if there are any quotes existing for this user and acts on it
        checkQuotes();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (notificationService != null) {
            notificationService.secondRun = true;
            notificationService.currentDisplayTime = currentDisplayTime;
        }

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



    //this method sets the new time for displaying quotes and starts the timer
    public void setNewQuoteTime(View view) {
        EditText setNewQuoteTime = (EditText) findViewById(R.id.setQuoteTime);
        newTimeSet = true;
        notificationService.timeToDisplayQuote = (Integer.parseInt(setNewQuoteTime.getText().toString())*1000)+100;
        startingNotification();
        setNewQuoteTime.setText("");
    }

    private   void startingTimerForQuotes(final int timeToDisplayQuote){

        //checking if there is a timer running and cancelling it before continue
        if (quoteTimer != null) {
            quoteTimer.cancel();
        }

        //starting new timer
        quoteTimer = new CountDownTimer(timeToDisplayQuote, 1000) {

        TextView timeLeftForDisplayingQuote = (TextView) findViewById(R.id.timeLeftForDisplayingQuote);


            //actions to happen for every second tick
            @Override
            public void onTick(long millisUntilFinished) {
                if(notificationService.notificationActive == false){
                    this.cancel();
                }

                timeLeftForDisplayingQuote.setText(String.format("%02d:%02d:%02d", millisUntilFinished/1000 / 3600,
                        (millisUntilFinished/1000 % 3600) / 60, (millisUntilFinished/1000 % 60)));

                //updating the content of the notification with the remaining time
                notificationService.updateNotification(MainHome_Activity.this, timeLeftForDisplayingQuote.getText().toString(), displayedQuote);
                currentDisplayTime = (int) millisUntilFinished;
            }

            @Override
            public void onFinish() {
            //when the timer finishes we change the quote to display
                User_Singleton.quoteToDisplayNumber++;
                if (User_Singleton.quoteToDisplayNumber >= quotesTotal) {
                    User_Singleton.quoteToDisplayNumber = 0;
                }

                //chaning the display of teh current quote in the activity
                displayedQuote = currentUser.getPersonalQuotes().get(User_Singleton.quoteToDisplayNumber);
                changeQuoteWithSound();

                this.cancel();
                //starting the next timer
                this.start();
            }
        };
        quoteTimer.start();
        //if the new timer was created we update timer in Notification service
        DisplayNotification.setQuoteTimer(quoteTimer);
    }


    //method that will perform the change animation and play the sound
    private   void changeQuoteWithSound(){

        soundEffectPlayer = MediaPlayer.create(this, R.raw.computermagic);
        soundEffectPlayer.start();

        //the line below seems to be faulty and not displaying the correct quote
        quoteOfTheDayView.setText(displayedQuote);
    }

    //in case if this is the new user or fresh run of the application
    //check is performed to see if there are any quotes for the current user
    public void checkQuotes(){
        //getting current number of quotes
        quotesTotal = currentUser.getPersonalQuotes().size();

        if (quotesTotal == 0) {
            //if there are no quotes Texview for displaying them will inform user about it
            quoteOfTheDayView.setText("Click Add Quotes to set the quotes to be visible here");
        } else {
            //if user already has some quotes we want to display the notification
            displayedQuote =  currentUser.getPersonalQuotes().get(User_Singleton.quoteToDisplayNumber);
            quoteOfTheDayView.setText(displayedQuote);

            startingNotification();
        }
    }

    private  void startingNotification(){
        //if user comes from welcome screen or changed the time we want to run the notification
        if ((first_run && quoteTimer == null) || (newTimeSet == true) || notificationService.secondRun == true) {
            //boolean test = isMyServiceRunning(FinancialNinja_TimeService.class);
            first_run = false;

            //we want to display new notification only if there is no existing one or the new time was set
            if (notificationService.secondRun == true || notificationService.notificationActive == false || newTimeSet == true)
            {
                newTimeSet = false;
                notificationService.secondRun = false;
                if (quotesTotal > 0) {
                //starting notification or creating new one
                notificationService = DisplayNotification.getNotificationServiceInstance();
                notificationService.showNotification(MainHome_Activity.this, displayedQuote);
                notificationService.notificationActive = true;

                //setting up the timer for displaying quotes
                quoteTimer = DisplayNotification.getQuoteTimer();

                    startingTimerForQuotes(notificationService.timeToDisplayQuote);
                } else {
                    Toast.makeText(this, "Please add some quotes first.",Toast.LENGTH_SHORT).show();
                }

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
            quoteTimer = null;
            DisplayNotification.setQuoteTimer(null);
        }

        //methods for reseting instances of user and notification
        notificationService.resetNotification();
        notificationService = null;
        User_Singleton.resetUser();
        currentUser = null;

        Intent logOutToHome = new Intent(MainHome_Activity.this, WelcomeActivity.class);
        startActivity(logOutToHome);
    }
}
