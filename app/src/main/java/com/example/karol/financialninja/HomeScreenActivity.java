package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the home screen that appears differently for each user depending on the current Ninja's level
 * At the basic functionality it contains only 1 button:
 *  - start quiz that does what it says
 *
 *  Below is the code to react to the press of that button
 *
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */

public class HomeScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }

    public void startQuizFromHome(View view) {
        Intent startQuiz = new Intent(HomeScreenActivity.this, QuizInfoActivity.class);
        startActivity(startQuiz);
    }

}
