package com.example.karol.financialninja;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karol on 2017-02-15.
 */

public class CreateAccountRequest extends StringRequest{

    //this variable provides url to our file on the server responsible for creating new users
    private static final String CREATE_ACCOUNT_REQUEST_URL = "http://40270305.soc-web-liv-06.napier.ac.uk/financialninja/createUser.php";
    private Map<String, String> params;

    //constructor takes parameters required for the account creation and sends them to the file on the server
    public CreateAccountRequest(String name, String username, String email, String password, Response.Listener<String> myListener){

        super(Request.Method.POST, CREATE_ACCOUNT_REQUEST_URL, myListener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        Log.i("Karol", "createAccount class.name, username, etc " + name + username + email + password);
        Log.i("Karol", "url " + CREATE_ACCOUNT_REQUEST_URL.toString());
        //getParams();

    }

    //@Override
    public Map<String, String> getParams() {
        Log.i("Karol", "params are " + params);
        return params;
    }
}
