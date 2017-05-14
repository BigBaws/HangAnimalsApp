package com.bigbaws.hanganimals.backend.user;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class User {

    public static String id;
    public static String name;
    public static String token;
    public static String study;
    public static String singleplayer;
    public static String multiplayer;

    public User(String id, String name, String study, String token, String singleplayer, String multiplayer) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.study = study;
        this.singleplayer = singleplayer;
        this.multiplayer = multiplayer;
    }

    public void setId(String id) {
        this.id = id;
    }



}