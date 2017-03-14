package com.example.karol.financialninja;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Karol on 2017-03-13.
 */

public class SubmitQuoteRequest extends StringRequest {
    //this variable provides url to our file on the server responsible for creating new users
    private static final String SUBMIT_QUOTE_REQUEST_URL = "http://40270305.soc-web-liv-06.napier.ac.uk/financialninja/addQuote.php";
    private Map<String, String> params;

    //constructor takes parameters required for the account creation and sends them to the file on the server
    public SubmitQuoteRequest(String quote, String author, String user_id, Response.Listener<String> myListener){

        super(Request.Method.POST, SUBMIT_QUOTE_REQUEST_URL, myListener, null);
        params = new HashMap<>();
        params.put("quote", quote);
        params.put("author", author);
        params.put("user_id", user_id);

       // Log.i("Karol", "url " + SUBMIT_QUOTE_REQUEST_URL.toString());
    }

    //@Override
    public Map<String, String> getParams() {
       // Log.i("Karol", "params are " + params);
        return params;
    }

}
