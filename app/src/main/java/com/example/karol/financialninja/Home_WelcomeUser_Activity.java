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
 * This activity contains the welcome message for the user coming back to use the app again and allows to continue to the main home screen
 *
 *
 * Design Patterns Used:
 *
 * Last Update: 14/03/2017
 */

public class Home_WelcomeUser_Activity extends AppCompatActivity {

    User_Singleton currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_welcome_user);
        currentUser = User_Singleton.getUser_Instance();

        TextView welcomeBackTitle = (TextView) findViewById(R.id.homeTitle);
        welcomeBackTitle.append("\n" + currentUser.getName().toString());
    }

    public void goToMainHome(View view) {
        Intent goToMainHome = new Intent(Home_WelcomeUser_Activity.this, MainHome_Activity.class);
        goToMainHome.putExtra("first_run", true);
        startActivity(goToMainHome);
    }
}
