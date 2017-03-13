package com.example.karol.financialninja;

import android.content.Intent;
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
 * This activity contains the code for all the actions of the creating account screen
 * At the basic functionality it contains only 2 buttons and few textboxes:
 *  - cancel button to return to the previous screen
 *  - create new account confirmation button
 *  - few text boxes that will collect the data about the user required for the account creation
 *
 * If the user will input invalid data appropriate message will be displayed
 *  Below is the code to react to the press of each of those buttons
 *
 * Design Patterns Used:
 *
 * Last Update: 14/02/2017
 */



public class CreateAccountActivity extends AppCompatActivity {

    //method for creating toast for whatever message we want to pass
    public void makeToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
    }

    public void createNewAccount(View view) {

        EditText name = (EditText) findViewById(R.id.enterNameTxt);
        EditText username = (EditText) findViewById(R.id.enterUserName);
        EditText emailTxt = (EditText) findViewById(R.id.enterEmail);
        EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        EditText passwordTxtConfirm = (EditText) findViewById(R.id.passwordTxt2);

        //Validator object is checking for certain validations required depending on which activity is using it
        Validator validator = new Validator(findViewById(R.id.LinearLayout_Create),
                passwordTxt.getText().toString(),
                emailTxt.getText().toString().trim(),name.getText().toString());


        //before creating new account email and password validity must be checked
        //first checking if both passwords are the same
        if (passwordTxt.getText().toString().contentEquals(passwordTxtConfirm.getText().toString())) {

            //if all data is correct and all fields are filled in application proceeds
            if (validator.isPasswordValid() == true && validator.isEmailValid() == true
                    && validator.isEmpty() == false && validator.isNameFormatValid() == true ) {

                final Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse (String response) {
                        Log.i("Karol", "I'm inside onResponse");
                        try {
                            Log.i("Karol", " Response is " + response);
                            //creating JSON object to receive the response from the server
                           // JSONObject jsonObjectResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            JSONObject jsonObjectResponse = new JSONObject(response);
                            //this boolean is expecting to receive success message from server
                            boolean successResponse = jsonObjectResponse.getBoolean("success");
                            Log.i("Karol", "JSON and bool created");
                            //checking if the message was received correctly
                            if (successResponse) {
                                Log.i("Karol", "I'm inside success");
                                //if yes, we're starting next activity
                                Intent createNewAccount = new Intent(CreateAccountActivity.this, HomeScreenNEWactivity.class );
                                startActivity(createNewAccount);
                            } else  {
                                Log.i("Karol", "I'm else and there should be alert box");
                                //if no, create new alert dialog with the message about failure
                                AlertDialog.Builder failedAccountCreationAlert= new AlertDialog.Builder(CreateAccountActivity.this);
                                failedAccountCreationAlert.setMessage("Account Creation Unsuccessful!")
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

                Log.i("Karol", "\nFuck!!!!!!!!!!!\n");

                //performing account creation on the server
                CreateAccountRequest createAccountRequest = new CreateAccountRequest(
                        name.getText().toString(),
                        username.getText().toString(),
                        emailTxt.getText().toString().trim(),
                        passwordTxt.getText().toString(),
                        responseListener
                        );
                RequestQueue requestQueue = Volley.newRequestQueue(CreateAccountActivity.this);
                requestQueue.add(createAccountRequest);


                //otherwise user will be prompted with correct error messages
            } else if (validator.isEmpty()) {
                    makeToast("Please fill in all empty fields.");
            } else if(!validator.isNameFormatValid()){
                makeToast("Please enter the valid name.");
            } else {
                    if (!validator.isEmailValid()) {
                        if (!validator.isPasswordValid()) {
                            makeToast("Your email and password are in wrong format");

                        }
                        else {
                            makeToast("Your email is incorrect. Please type in correct email");
                        }
                    } else {
                        makeToast("Your password is incorrect. \nIt needs to contain letters and numbers and to be at least 8 characters long.");
                    }
            }
            //if the passwords were miss-typed user will be informed
        } else {
            makeToast("Your passwords do not match!");
        }
    }

    public void returnToWelcomeScreen(View view) {
        Intent cancelAccountCreation = new Intent(CreateAccountActivity.this, WelcomeActivity.class);
        startActivity(cancelAccountCreation);
        Log.i("Karol","Name is");
    }
}
