package com.example.karol.financialninja;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
 *  User_Singleton has to input username and password to access the app.
 *
 *  Future updates:
 *  - this page will use method calls to authenticate and authorize user's access to the app
 *
 * Design Patterns Used:
 *
 * Last Update: 09/02/2017
 */


public class LogInActivity extends AppCompatActivity{

    User_Singleton currentUser;
    SharedPreferences sharedpreferences;

    private void resetUser(){
        if (currentUser != null)
        {
            //currentUser.getmNotificationManager().cancelAll();
            currentUser = null;
            boolean test = isMyServiceRunning(FinancialNinja_TimeService.class);
            Toast.makeText(this, "is my service running " + test, Toast.LENGTH_SHORT).show();
            //stopService(new Intent(LogInActivity.this, FinancialNinja_TimeService.class));
        }
    }

    private boolean isMyServiceRunning(Class<FinancialNinja_TimeService> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private String mypPeference = "ninjaPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sharedpreferences = this.getSharedPreferences(
                "com.example.karol", this.MODE_PRIVATE);
        resetUser();

    }
    @Override
    protected void onResume() {
        super.onResume();
        resetUser();
    }

    public void returnToWelcomeScreen(View view) {
        Intent cancelAccountCreation = new Intent(LogInActivity.this, WelcomeActivity.class);
        startActivity(cancelAccountCreation);
    }

    public void logInToAccount(View view) {

        final EditText username = (EditText) findViewById(R.id.enterUserName);
        EditText password = (EditText) findViewById(R.id.passwordLoginTxt);

        final Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //creating JSON object to receive the response from the server
                    JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        //creating new user that will be used throughout the application
                        currentUser = User_Singleton.getUser_Instance();
                        currentUser.setUser_id(jsonResponse.getString("user_id"));
                        String tempName = jsonResponse.getString("name");
                        tempName = tempName.substring(0,1).toUpperCase() + tempName.substring(1);
                        currentUser.setName(tempName);
                        currentUser.setUserName(jsonResponse.getString("username"));




                        //SharedPreferences.Editor editor = sharedpreferences.edit();
                        //editor.putString("dupa", "chuj");



                        //starting new activity with existing user
                        Intent logInToExistingAccount = new Intent(LogInActivity.this, Home_WelcomeUser_Activity.class);
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
