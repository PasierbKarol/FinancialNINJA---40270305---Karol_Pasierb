package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/** Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the list of all existing quotes for the current user and displays them
 *
 *
 * Last Update: 15/03/2017
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

        //getting instance of the user and accessing UI elements
        currentUser = User_Singleton.getUser_Instance();
        personalQuotesTitle = (TextView) findViewById(R.id.personalQuotesTitle);
        quoteOfTheDayList = (TextView) findViewById(R.id.quoteOfTheDayList);
        fullListOfQuotes = (TextView) findViewById(R.id.fullListOfQuotes);

        //displaying the user's content
        fullListOfQuotes.setText(currentUser.displayPersonalQuotes());
        personalQuotesTitle.append(currentUser.getName());
    }

    public void returnToMainHome(View view) {
        Intent returnToMainHome = new Intent(QuotesList_Activity.this, MainHome_Activity.class);
        startActivity(returnToMainHome);
    }
}
