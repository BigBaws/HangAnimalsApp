package com.bigbaws.hanganimals.backend.user;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class User {

    public String id;
    public String name;
    public String email;
    public String image;

    public void User(String id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return this.name; }

}