package com.example.normu.iutgram;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Chat {
    private String user_id;
    private String the_date;
    private String name;
    private String last_message;
    private Drawable userImage;

    public Chat(String user_id, String the_date, String name, String last_message, Drawable userImage) {
        this.user_id = user_id;
        this.the_date = the_date;
        this.name = name;
        this.last_message = last_message;
        this.userImage = userImage;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getThe_date() {
        return the_date;
    }

    public void setThe_date(String the_date) {
        this.the_date = the_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public Drawable getUserImage() {
        return userImage;
    }

    public void setUserImage(Drawable userImage) {
        this.userImage = userImage;
    }
}
