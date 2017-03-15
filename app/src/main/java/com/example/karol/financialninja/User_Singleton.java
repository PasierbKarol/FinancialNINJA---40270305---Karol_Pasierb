package com.example.karol.financialninja;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/** Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This class is the main class of the whole application that takes responsibility over the User and its data
 * This class will be serialized  to save user's quotes locally
 * All other user's data is kept on the server in the database
 *
 *  Future updates:
 *   - user's password must be stored encrypted on the server
 *
 * Design Patterns Used:
 * This class was designed with the use of Singleton Pattern
 * as there can be only one user logged in at the current time.
 * Each activity uses the same instance. In case of user would go back
 * to login, create account or home activities its instance is erased. It becomes null.
 * This is the simplest version of logging out.
 *
 * Last Update: 15/03/2017
 */



public class User_Singleton implements Serializable
{

    //code for serialization
    private static final long serialVersionUID = 666L;


    //instance fields with getters and setters
    private ArrayList<String> personalQuotes = new ArrayList<String>();
    public ArrayList<String> getPersonalQuotes() {
        return personalQuotes;
    }
    public void setPersonalQuotes(String quote, String author) {
        author = author.substring(0,1).toUpperCase() + author.substring(1);
        this.personalQuotes.add(quote + ", by: " + author);
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


    //Class instance variable and the constructor
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

    public void readQuotesOnStartup (Context context){
        //creating unique filename for each user
        dbFilename =  "user-"+getUserName()+".bin";
        try {
            serializerIn = context.openFileInput(dbFilename);
            inputSerializer = new ObjectInputStream(serializerIn);

            //trying to fill in the object with the data from the file
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

            //confirmation log
            Log.i("Karol", "File loaded! Filename " + dbFilename);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Karol", "Loading error is " + e.toString());
        }

        //Once the data is loaded at the startup we print it to check if it worked
       getPersonalQuotes();
    }

    public void  saveQuotes (Context context) {
        //creating unique file for each user.
        //name required in case if file would not exist
        dbFilename =  "user-"+getUserName()+".bin";
        try {
            serializerOut = context.openFileOutput(dbFilename, Context.MODE_PRIVATE);
            outputSerializer = new ObjectOutputStream(serializerOut);
            outputSerializer.writeObject(personalQuotes);
            outputSerializer.close();
            serializerOut.close();

            //confirmation log
            Log.i("Karol", "File saved! Filename " + dbFilename);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Karol", "Saving error is " + e.toString());
        }
    }

    //method for displaying all quotes
    public String displayPersonalQuotes() {
        int i = 1;
        String quotesList = "";
        for (String quote : personalQuotes) {
            quotesList += i + ": " + quote + ".\n\n";
            i++;
        }
        return quotesList;
    }


    //static method to destroy the user and log out
    public static User_Singleton resetUser(User_Singleton userSent){
        if (userSent != null){
            userSent = null;
            //disactivas
            DisplayNotification notification = DisplayNotification.getNotificationServiceInstance();
            notification.notificationActive = false;
            NotificationManager mNotificationManager = notification.getmNotificationManager();
            if (mNotificationManager != null) {
                mNotificationManager.cancelAll();
            }

            return  userSent;
        }
        else {
            return userSent;
        }
    }
}
