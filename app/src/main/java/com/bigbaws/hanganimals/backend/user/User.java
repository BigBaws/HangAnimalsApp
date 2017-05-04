package com.bigbaws.hanganimals.backend.user;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class User {

    public static String id;
    public static String name;
    public static String token;
    public static String study;
//    public String singleplayer;
//    public String multiplayer;
//    public String animal;w
//    public String animalColor;

    public User(String id, String name, String study, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.study = study;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getUserName() { return this.name; }
//    public String getUserID() { return this.id;}
//    public String getUserToken() { return this.token;}
//    public String getUserStudy() { return this.study;}


}