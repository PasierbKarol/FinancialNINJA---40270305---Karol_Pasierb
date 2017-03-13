package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the about the app screen
 * At the basic functionality it contains only 2 buttons that allow user either to create a new account or cancel and return to welcome screen
 *  This screen is purely to inform the user about the app and what it contains
 *
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }


    public void returnToWelcomeScreen(View view) {
        Intent cancelAccountCreation = new Intent(AboutUsActivity.this, WelcomeActivity.class);
        startActivity(cancelAccountCreation);
    }

    public void createAccountWelcome(View view) {
        Intent createNewAccount = new Intent(AboutUsActivity.this, CreateAccountActivity.class );
        startActivity(createNewAccount);
    }
}
