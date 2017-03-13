package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

public class QuizResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_results);
    }

    public void returnToHome(View view) {
        Intent quizCompleted = new Intent(QuizResultsActivity.this, HomeScreenActivity.class);
        startActivity(quizCompleted);
    }
}
