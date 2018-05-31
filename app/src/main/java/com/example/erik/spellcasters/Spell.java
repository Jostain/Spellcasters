package com.example.erik.spellcasters;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Erik on 2016-12-11.
 */

@IgnoreExtraProperties
public class Spell {
    public static Spell storage;
    public String spellName;
    public String targetName;
    public String targetToken;
    public String casterName;
    public String casterToken;
    public String area;
    public String description;
    public String imageCode;
    public String manaCost = "0:0:0:0:0:0:0:0";
    public int coolDown = 0;
    public int multiples = 0;
    public boolean silent = false;
    public String effects = "damage:0";

   // public int[] effects;//{cooldown,manaCost,multiplier,rawDamage,heal,manaDrain,manaSurge,castingFatigue,physicalShield,shieldBreak}
    boolean handled;
    long timeStamp;
    String key;


    public Spell() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Spell(String spellName,String description,String imageCode,String manaCost,String effects,int coolDown,int multiples) {
        this.spellName = spellName;
        this.description = description;
        this.imageCode = imageCode;
        this.manaCost = manaCost;
        this.effects = effects;
        this.coolDown = coolDown;
        this.multiples = multiples;
    }


}