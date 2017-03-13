package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

public class QuizInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizinfo);
    }

    public void cancelQuiz(View view) {
        Intent cancelQuiz = new Intent(QuizInfoActivity.this, HomeScreenActivity.class);
        startActivity(cancelQuiz);
    }

    public void startQuizFromInfo(View view) {
        Intent startQuizConfirmed = new Intent(QuizInfoActivity.this, QuizResultsActivity.class);
        startActivity(startQuizConfirmed);
    }
}
