package com.example.karol.financialninja;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/** Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-14.
 *
 * Description:
 * This class is purely design to extract validation methods from other classes.
 * This class has methods for checking if the fields are filled in and if the content format is appropriate.
 *
 *  Future updates:
 *
 * Design Patterns Used:
 *
 * Last Update: 14/02/2017
 */

public class Validator {

    private View viewGroup;
    private String emailTxt;
    private boolean validEmail;

    //booleans set to check the password validity
    private boolean validPassword = false;
    private boolean hasNumbers = false;
    private boolean hasLetters = false;
    private String password;
    private String name;


    public  Validator (View view){
        this.viewGroup = view;
    }

    //constructor for creating account
    public  Validator (View view, String password, String email, String name){
        this.viewGroup = view;
        this.password = password;
        this.emailTxt = email;
        this.name = name;
    }

    //this method will check if the edit boxes are empty or filled in
    public boolean isEmpty() {
        //Using group view to iterate through all the textbox fields
        //viewGroup =  findViewById(R.id.LinearLayout_Create);
        boolean empty = false;

        //checking all edit boxes for content
        for (int i = 0; i < ((ViewGroup)viewGroup).getChildCount(); i++) {
            //this line check every child of the layout
            View singleChild = ((ViewGroup)viewGroup).getChildAt(i);
            //this line checks if the child is Edit Box
            if (singleChild instanceof EditText) {
                EditText editText = (EditText)singleChild;
                //this line trims all trailing whitespace and checks if its 0 characters
                if(editText.getText().toString().trim().length() == 0)
                    empty =  true;
            }
        }
        return empty;
    }

    //this method is checking if the password format is correct
    public boolean isPasswordValid() {
        //checking password string by character
        for(int i = 0; i < password.length(); i++){
            char x = password.charAt(i);
            if (Character.isDigit(x)) {
                hasNumbers = true;
            }
            else if (Character.isLetter(x)) {
                hasLetters = true;
            }
            else {
                validPassword = false;
                break;
            }
        }
        //if it contains letter, numbers and it's longer than 8 characters then it's valid
        if (hasLetters && hasNumbers && password.length() >= 8)
        {
            validPassword = true;
        }
        return validPassword;
    }

    //this method checks the validity of an email
    public boolean isEmailValid() {
        //checking email validity
        if (TextUtils.isEmpty(emailTxt)) {
            validEmail =  false;
        } else {
            //
            if(Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                validEmail = true;
            } else{
                validEmail = false;
            }
        }
        return validEmail;
    }

    public boolean isNameFormatValid(){

        if (name.matches("[a-zA-Z ]+")) {
           // Log.i("Karol", "Name is valid: " + name);
            return true;
        }
        else {
            //Log.i("Karol", "Name is invalid!!!!: " + name);
            return false;
        }
    }
}
