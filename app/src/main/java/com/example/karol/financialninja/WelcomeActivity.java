package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;



/* Author: Karol Pasierb - Software Engineering - 40270305
 *
 * Description:
 * This activity contains the code for all the actions of the first screen that appears when the app starts
 * At the basic functionality it contains only 3 buttons:
 *  - question mark to read more about the app
 *  - create new account
 *  - log in to use the app
 *
 *  Below is the code to react to the press of each of those buttons
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */
    public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void createAccountWelcome(View view) {
        Intent createAccount = new Intent(WelcomeActivity.this, CreateAccountActivity.class);
        WelcomeActivity.this.startActivity(createAccount);
    }

    public void logInWelcome(View view) {

        Intent logInActivity = new Intent(WelcomeActivity.this, LogInActivity.class);
        startActivity(logInActivity);
    }



        public void readAboutTheApp(View view) {
            Intent readAboutApp = new Intent(WelcomeActivity.this, AboutUsActivity.class );
            startActivity(readAboutApp);
        }





}
