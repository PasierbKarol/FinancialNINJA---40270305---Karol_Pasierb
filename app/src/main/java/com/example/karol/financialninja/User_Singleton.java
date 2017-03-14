package com.example.karol.financialninja;

/* Author: Karol Pasierb - Software Engineering - 40270305
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
 * Last Update: 14/02/2017
 */

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_Singleton implements Serializable
{

    private static final long serialVersionUID = 666L;
    //instance fields with getters and setters
    private ArrayList<String> personalQuotes = new ArrayList<String>();

    public ArrayList<String> getPersonalQuotes() {
        Log.i("Karol", "Quotes number is " + this.personalQuotes.size());

        for (String text : personalQuotes) {
            Log.i("Karol", "Quotes are " + text );
        }

        return personalQuotes;
    }

    public void setPersonalQuotes(String quote, String author) {
        Log.i("Karol", "Quotes number before is " + this.personalQuotes.size());
                this.personalQuotes.add(quote + ", by: " + author);
        Log.i("Karol", "Quote added to the list");
        Log.i("Karol", "Quotes number after is " + this.personalQuotes.size());
    }


    private String name;
    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String userName;
    public String getUserName() {

        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }


    private String user_id;
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String quote_content;
    public String getQuote_content() {
        return quote_content;
    }
    public void setQuote_content(String quote_content) {
        this.quote_content = quote_content;
    }

    private  String quote_author;
    public String getQuote_author() {
        return quote_author;
    }
    public void setQuote_author(String quote_author) {
        this.quote_author = quote_author;
    }






/*
    public boolean isNewUser() {

        return newUser;
    }
    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }
*/




    public static User_Singleton user_Instance;
    public static User_Singleton getUser_Instance() {
        if (user_Instance == null) {
            user_Instance = new User_Singleton();
        }
        return user_Instance;
    }



    private User_Singleton() {
    }


    //variables for serializing data
    private transient String dbFilename ;
    private transient FileOutputStream serializerOut;
    private transient FileInputStream serializerIn;
    private transient ObjectInputStream inputSerializer;
    private transient ObjectOutputStream outputSerializer;


    public String displayQuote(){
        String quote = "";


        return quote;
    }


    public void readQuotesOnStartup (Context context){
        dbFilename =  "user-"+getUserName()+".bin";
        try {
            serializerIn = context.openFileInput(dbFilename);
            inputSerializer = new ObjectInputStream(serializerIn);
            try {
                personalQuotes = (ArrayList<String>) inputSerializer.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Karol", "Loading file error is " + e.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Log.i("Karol", "Loading file error is " + e.toString());
            }
            inputSerializer.close();
            serializerIn.close();
            Log.i("Karol", "File loaded! Filename " + dbFilename);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Karol", "Loading error is " + e.toString());
        }

        //Once the data is loaded at the startup we print it to check if it worked
       getPersonalQuotes();
    }

    public void  saveQuotes (Context context) {
        dbFilename =  "user-"+getUserName()+".bin";
        try {
            serializerOut = context.openFileOutput(dbFilename, Context.MODE_PRIVATE);
            outputSerializer = new ObjectOutputStream(serializerOut);
            outputSerializer.writeObject(personalQuotes);
            outputSerializer.close();
            serializerOut.close();
            Log.i("Karol", "File saved! Filename " + dbFilename);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Karol", "Saving error is " + e.toString());
        }
    }
}
