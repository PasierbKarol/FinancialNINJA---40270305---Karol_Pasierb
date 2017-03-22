package com.example.karol.financialninja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/** Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This class is the main class of the whole application that takes responsibility over the User_Singleton and its data
 * This class will be serialized and send to the server to save the existing user's data
 *
 *  Future updates:
 *   - do we need Singleton for the user? Would that allow to create other users?
 *   - perhaps instead of Singleton we need some logger class that would be a Singleton
 *   - user's password must be stored encrypted on the server
 *   - find out if all of those fields should be in current class or aditional classes are needed
 * - explain teh email validity code
 * Design Patterns Used:
 *
 * Last Update: 15/03/2017
 */

public class AddQuote extends AppCompatActivity {

    User_Singleton currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_quote_activity);

        currentUser = User_Singleton.getUser_Instance();
    }

    public void cancelQuote(View view) {
        Intent cancelQuote = new Intent(AddQuote.this, MainHome_Activity.class);
        startActivity(cancelQuote);
    }

    public void submitQuote(View view) {
        final EditText quote = (EditText) findViewById(R.id.quoteContent);
        final EditText author = (EditText) findViewById(R.id.authorContent);

        Validator validator = new Validator(findViewById(R.id.addQuotesLayout));

        //using Validator class to check if fields were filled
        if (validator.isEmpty() == false) {
            final Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //creating JSON object to receive the response from the server
                        JSONObject jsonObjectResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        //this boolean is expecting to receive success message from server
                        boolean successResponse = jsonObjectResponse.getBoolean("success");

                        if (successResponse) {

                            //if yes, we're reseting textboxes and posting Toast
                            currentUser.setPersonalQuotes(quote.getText().toString(), author.getText().toString());
                            currentUser.saveQuotes(AddQuote.this);
                            quote.setText("");
                            author.setText("");
                            makeToast("Quote was submitted. \nYou may enter new quote.");

                        } else  {
                            //if no, create new alert dialog with the message about failure
                            AlertDialog.Builder failedSubmittingQuoteAlert= new AlertDialog.Builder(AddQuote.this);
                            failedSubmittingQuoteAlert.setMessage("Quote Submission Unsuccessful!")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        Log.i("Karol", "I'm inside catch. No quote... there was an error " + e.toString());
                        e.printStackTrace();
                    }
                }
            };

            //quote submission to the database on the server
            SubmitQuoteRequest submitQuoteRequest = new SubmitQuoteRequest(
                    quote.getText().toString(),
                    author.getText().toString(),
                    currentUser.getUser_id(),
                    responseListener
            );

            RequestQueue requestQueue = Volley.newRequestQueue(AddQuote.this);
            requestQueue.add(submitQuoteRequest);

        } else {
            makeToast("Please fill in all empty fields.");
        }


    }

    public  void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
