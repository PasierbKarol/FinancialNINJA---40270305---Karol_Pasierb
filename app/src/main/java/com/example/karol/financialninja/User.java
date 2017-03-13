package com.example.karol.financialninja;

/* Author: Karol Pasierb - Software Engineering - 40270305
 * Created by Karol on 2017-02-08.
 *
 * Description:
 * This class is the main class of the whole application that takes responsibility over the User and its data
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

public class User
{
    //instance fields with getters and setters
        //stores the points of the current quiz active
    private int currentPoints;
        //stores all the points of all the quizzes
    private int pointsTotal;
        //this field will change depending on the quiz results
    private int ninjaLevel = 0;
        //each time when new user is created is obviously new
        //with this field app will present the data depending on the user's level
    private boolean newUser = true;
        //if the quiz is active user won't be able to quit the app
    private boolean quizActive = false;
    private String name;
    private  String userName;
        //password will be possibly stored elsewhere for security reasons


    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getPointsTotal() {
        return pointsTotal;
    }

    public int getNinjaLevel() {
        return ninjaLevel;
    }

    public void setNinjaLevel(int ninjaLevel) {
        this.ninjaLevel = ninjaLevel;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQuizActive() {

        return quizActive;
    }

    public void setQuizActive(boolean quizActive) {
        this.quizActive = quizActive;
    }

    public boolean isNewUser() {

        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }




}
