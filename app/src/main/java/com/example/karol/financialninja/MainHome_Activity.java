package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    int quoteToDisplay = 0;
    int quotesTotal;
    TextView quoteOfTheDay1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_home);

        //accessing user and the quotes
        currentUser = User_Singleton.getUser_Instance();
        currentUser.readQuotesOnStartup(this);
        Log.i("Karol", currentUser.getName() + " has " + currentUser.getPersonalQuotes().size() + " quotes\n Read the quotes from File");




        //accessing UI elements
        TextView welcomeBackTitle = (TextView) findViewById(R.id.mainHomeTitle);
        quoteOfTheDay1 = (TextView) findViewById(R.id.quoteOfTheDay1);
        final TextView quoteOfTheDayLabel = (TextView) findViewById(R.id.quoteOfTheDayLabel);
        welcomeBackTitle.append("Welcome back Ninja " + currentUser.getName().toString());


        quotesTotal = currentUser.getPersonalQuotes().size();
        Log.i("Karol", "quotesTotal = " + quotesTotal);
        quoteOfTheDay1.setText(currentUser.getPersonalQuotes().get(quoteToDisplay));
        Log.i("Karol", "quoteOfTheDay1 text = " + currentUser.getPersonalQuotes().get(quoteToDisplay));

        //setting up teh timer for displaying quotes
        quoteTimer = new CountDownTimer(3100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                quoteOfTheDayLabel.setText("Your Quote to remember is: " + Long.toString(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                quoteToDisplay++;
                if (quoteToDisplay >= quotesTotal) {
                    quoteToDisplay = 0;
                }
                quoteOfTheDay1.setText(currentUser.getPersonalQuotes().get(quoteToDisplay));
                this.start();
            }
        }.start();


    }

    public void addQuote(View view) {
        Intent addQuote = new Intent(MainHome_Activity.this, AddQuote.class);
        startActivity(addQuote);
    }

    public void startQuizFromInfo(View view) {
        Intent startQuizConfirmed = new Intent(MainHome_Activity.this, QuotesList_Activity.class);
        startActivity(startQuizConfirmed);
    }
}
