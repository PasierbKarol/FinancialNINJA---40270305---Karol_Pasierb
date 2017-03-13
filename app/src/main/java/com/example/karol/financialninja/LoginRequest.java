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

public class LoginRequest extends StringRequest{

    //this variable provides url to our file on the server responsible for creating new users
    private static final String LOGIN_REQUEST_URL = "http://40270305.soc-web-liv-06.napier.ac.uk/financialninja/login.php";
    private Map<String, String> params;

    //constructor takes parameters required for the account creation and sends them to the file on the server
    public LoginRequest(String username,String password, Response.Listener<String> myListener){

        super(Request.Method.POST, LOGIN_REQUEST_URL, myListener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    //@Override
    public Map<String, String> getParams() {
        return params;
    }
}
