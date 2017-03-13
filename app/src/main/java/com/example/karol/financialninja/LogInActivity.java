package com.example.karol.financialninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This activity contains the code for all the actions of the log in screen
 * At the basic functionality it contains only 2 button and 2 textboxes:
 *  - cancel button will take the user back to the welcome screen
 *  - log in confirmation button
 *
 *  Below is the code to react to the press of those buttons.
 *  User has to input username and password to access the app.
 *
 *  Future updates:
 *  - this page will use method calls to authenticate and authorize user's access to the app
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */


public class LogInActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void returnToWelcomeScreen(View view) {
        Intent cancelAccountCreation = new Intent(LogInActivity.this, WelcomeActivity.class);
        startActivity(cancelAccountCreation);
    }

    public void logInToAccount(View view) {

        EditText username = (EditText) findViewById(R.id.enterUserName);
        EditText password = (EditText) findViewById(R.id.passwordLoginTxt);

        final Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //creating JSON object to receive the response from the server
                    //JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean success = jsonResponse.getBoolean("success");
                    Log.i("Karol","Success = " + success);
                    if (success) {
                        //String name = jsonResponse.getString("name");
                        Intent logInToExistingAccount = new Intent(LogInActivity.this, HomeScreenActivity.class);
                        //logInToExistingAccount.putExtra("name", name);
                        startActivity(logInToExistingAccount);
                    } else {

                        //if no, create new alert dialog with the message about failure
                        AlertDialog.Builder failedAccountCreationAlert= new AlertDialog.Builder(LogInActivity.this);
                        failedAccountCreationAlert.setMessage("Account Authorization Unsuccessful!")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    Log.i("Karol", "I'm inside catch and there was an error " + e.toString());
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(
                username.getText().toString().trim(),
                password.getText().toString(),
                responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(LogInActivity.this);
        requestQueue.add(loginRequest);

    }
}
