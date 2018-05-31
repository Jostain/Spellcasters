package com.example.erik.spellcasters;

import android.text.Html;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by minty on 2016-12-26.
 */

public class SharedData {
    public static String token;
    public static User player;
    public static Spell thing;
    public static HashMap<String,Spell> spellLibrary;
    public static boolean initiated = false;
    public static DatabaseReference mDatabase;
    public static DatabaseReference mUsers;
    public static DatabaseReference mUser;
    public static DatabaseReference mGeoFireUsers;
    public static DatabaseReference mGeoFireMana;
    public static ArrayList<String> targets;
    public static DatabaseReference mAnnouncements;
    public static Random random;
    public static String announcements = "";
    public static Spell storage;


    public static TextView username;
    public static TextView healthMeter;
    public static TextView mentalMeter;
    public static TextView octarineMana;
    public static TextView purpleMana;
    public static TextView redMana;
    public static TextView orangeMana;
    public static TextView yellowMana;
    public static TextView greenMana;
    public static TextView blueMana;
    public static TextView cyanMana;
    public static TextView speedMeter;
    public static TextView shieldMeter;
    public static TextView singleShieldMeter;
    public static TextView announcer;
    public static TextView counter;
    public static TextView dodgeMeter;
    public static TextView heatMeter;

    public static void updateStats()
    {
        healthMeter.setText("" + player.health);
        dodgeMeter.setText("" + player.evasive);
        mentalMeter.setText("" + player.mental);
        shieldMeter.setText("" + player.shield);
        singleShieldMeter.setText("" + player.singleShield);
        octarineMana.setText("" + player.octarineMana);
        purpleMana.setText("" + player.purpleMana);
        redMana.setText("" + player.redMana);
        orangeMana.setText("" + player.orangeMana);
        yellowMana.setText("" + player.yellowMana);
        greenMana.setText("" + player.greenMana);
        blueMana.setText("" + player.blueMana);
        cyanMana.setText("" + player.cyanMana);
        heatMeter.setText("" + player.temperature);
        speedMeter.setText("" + player.speed);
        announcer.setText(Html.fromHtml(announcements));
    }
}
