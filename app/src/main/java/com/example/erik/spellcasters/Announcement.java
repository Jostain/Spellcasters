package com.example.erik.spellcasters;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Erik on 2016-12-10.
 */

@IgnoreExtraProperties
public class Announcement {

    public String text;
    public long timeStamp;

    public Announcement() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Announcement(String text) {
        this.text = text;
    }

}
