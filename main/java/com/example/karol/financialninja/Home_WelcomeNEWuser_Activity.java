package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/** Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the home screen that appears on the first ron of the app for each user
 * At the basic functionality it contains only 1 button and allows to continue to the main home screen
 *
 *  Below is the code to react to the press of that button
 *
 * Design Patterns Used:
 *
 * Last Update: 14/03/2017
 */


public class Home_WelcomeNEWuser_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_welcome_new_user);
    }

    public void goToMainHome(View view) {
        Intent goToMainHome = new Intent(Home_WelcomeNEWuser_Activity.this, MainHome_Activity.class);
        //this extra will let app know if user is visiting the main home screen for teh first time
        goToMainHome.putExtra("first_run", true);
        startActivity(goToMainHome);
    }

}
