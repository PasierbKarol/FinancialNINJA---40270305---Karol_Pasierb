package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the quiz results screen
 * At the basic functionality it contains only 1 buttons that takes user back to the home screen after completing the quiz.
 *  This screen is to inform the user about the results of the quiz and possible update of the Ninja's level
 *
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */

public class QuotesList_Activity extends AppCompatActivity {

    User_Singleton currentUser;
    TextView personalQuotesTitle;
    TextView quoteOfTheDayList;
    TextView fullListOfQuotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quotes_list);
        currentUser = User_Singleton.getUser_Instance();
        personalQuotesTitle = (TextView) findViewById(R.id.personalQuotesTitle);
        quoteOfTheDayList = (TextView) findViewById(R.id.quoteOfTheDayList);
        fullListOfQuotes = (TextView) findViewById(R.id.fullListOfQuotes);


        personalQuotesTitle.append(currentUser.getName());

    }

    public void returnToHome(View view) {
        Intent quizCompleted = new Intent(QuotesList_Activity.this, Home_WelcomeUser_Activity.class);
        startActivity(quizCompleted);
    }
}
