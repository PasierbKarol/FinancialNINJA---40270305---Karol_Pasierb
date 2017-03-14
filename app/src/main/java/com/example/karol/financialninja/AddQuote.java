package com.example.karol.financialninja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddQuote extends AppCompatActivity {

    User_Singleton currentUser;


    public  void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = User_Singleton.getUser_Instance();
        setContentView(R.layout.add_quote_activity);


    }

    public void cancelQuote(View view) {
        Intent cancelQuote = new Intent(AddQuote.this, MainHome_Activity.class);
        startActivity(cancelQuote);
    }

    public void submitQuote(View view) {
        final EditText quote = (EditText) findViewById(R.id.quoteContent);
        final EditText author = (EditText) findViewById(R.id.authorContent);

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
                        currentUser.setQuote_content(quote.getText().toString());
                        currentUser.setQuote_author(author.getText().toString());
                        currentUser.setPersonalQuotes(currentUser.getQuote_content(), currentUser.getQuote_author());
                        //currentUser.getPersonalQuotes();
                        currentUser.saveQuotes(AddQuote.this);
                        quote.setText("");
                        author.setText("");
                        makeToast("Quote was submitted. \nYou may enter new quote.");

                    } else  {
                        Log.i("Karol", "I'm else and there should be alert box");
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

        //performing account creation on the server
        SubmitQuoteRequest submitQuoteRequest = new SubmitQuoteRequest(
                quote.getText().toString(),
                author.getText().toString(),
                currentUser.getUser_id(),
                responseListener
        );

        RequestQueue requestQueue = Volley.newRequestQueue(AddQuote.this);
        requestQueue.add(submitQuoteRequest);
    }
}
