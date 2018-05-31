package com.example.erik.spellcasters;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Erik on 2016-12-10.
 */

@IgnoreExtraProperties
public class User {

    public String username = "???";
    public int health = 100;
    public int mental = 100;
    public int redMana = 100;
    public int orangeMana = 100;
    public int yellowMana = 100;
    public int greenMana = 100;
    public int blueMana = 100;
    public int cyanMana = 100;
    public int purpleMana = 100;
    public int octarineMana = 100;
    public int shield = 0;
    public int singleShield = 0;
    public int temperature = 0;
    public int wet = 0;
    public boolean visible = true;
    public boolean ignore = false;
    public boolean blind = false;
    public double speed = 1;
    public double longitude = 0;
    public double latitude = 0;
    public int evasive = 0;
    public boolean hideSpell = false;
    public String lastCastSpell = "none";
    public String forcedSpell = "";
    public String spellbook = "Learn Spell,Absorb Mana,Meditate";
    public String availableSpells = "Learn Spell,Absorb Mana,Meditate";
    public Long timeStamp = (long)0;
    public HashMap<String, Integer> statusEffects = new HashMap<>();
    //public boolean mindloop = false;
    public boolean mirror = false;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, int health, int mana) {
        this.username = username;
        this.health = health;
        //this.mana = mana;
    }

    public String resolveSpell(Spell spell,HashSet<String> availableTargets) {
        System.out.println("MyTag: resolving spell! "+spell.spellName);
        String announcement = "";
        int finalDamage = 0;
        String caster = spell.casterName;
        String casterColor = caster.split(" The ")[1];
        String target = spell.targetName;
        String targetColor = target.split(" The ")[1];;
        String spellString = spell.effects;
        String[] spellEffectStrings = spellString.split(",");
        String color = "#EE0000";
        for(int i = spell.multiples;i > 0;i--)
        {
            if(mirror == true)
            {
              announcement = resolveMirrorBreak(spell);
            }
            else if(singleShield > 0)
            {
               singleShield--;
               announcement = "" + spell.casterName + " cast " + spell.spellName + " at " + spell.targetName + "(<font color='#EE0000'>" + "resisted!" + "</font>)";
                return announcement;
            }
            else
            {
                Random random = new Random();
                int roll = random.nextInt(100);
                if(roll<=evasive)
                {
                    announcement = "" + "<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + caster + "</font>" + " cast " + spell.spellName + " at " + "<font color='#"+NameGenerator.colorHex(targetColor)+"'>" + target + "</font>" + "(<font color='"+"Yellow"+"'>" + "Dodged!" + "</font>)";
                    return announcement;
                }
                else{
                    evasive = 0;
            for(String spellEffect:spellEffectStrings)
            {
                String[] stats = spellEffect.split(":");
                switch(stats[0])
                {
                    case "silence":
                        System.out.println("MyTag: Silence!");
                        long currentTime = System.currentTimeMillis()+Long.parseLong(stats[1]);
                        if(currentTime > timeStamp)
                        {
                            timeStamp = currentTime;
                        }
                        break;
                    case "damage":
                        finalDamage = finalDamage - resolveDamage(Integer.parseInt(stats[1]));
                        break;
                    case "heal":
                        if(health + (Integer.parseInt(stats[1]))<=100){
                        health = health + (Integer.parseInt(stats[1]));
                        finalDamage = finalDamage + (Integer.parseInt(stats[1]));}
                        break;
                    case "harm":
                        SharedData.mDatabase.child("returnSpells").child(spell.casterToken).push().setValue("harm:"+stats[1]);
                        break;
                    case "shield":
                        resolveShield(Integer.parseInt(stats[1]));
                        break;
                    case "barrier":
                        resolveBarrier(Integer.parseInt(stats[1]));
                        break;
                    case "manaBurn":
                        //mana = mana - Integer.parseInt(stats[1]);
                        break;
                    case "manaBreath":
                       // mana = mana + Integer.parseInt(stats[1]);
                        break;
                    case "vampirism":
                        finalDamage = finalDamage - resolveVampirism(Integer.parseInt(stats[1]),spell.casterToken);
                        break;
                    case "fire":
                        finalDamage = finalDamage - resolveFire(Integer.parseInt(stats[1]));
                        break;
                    case "ice":
                        finalDamage = finalDamage - resolveIce(Integer.parseInt(stats[1]));
                        break;
                    case "cooldown":
                        resolveCooldown(Integer.parseInt(stats[1]));
                        break;
                    case "invisible":
                        visible = false;
                        SharedData.mDatabase.child("users").child(SharedData.token).child("visible").setValue(false);
                        SharedData.mDatabase.child("visible").child(SharedData.token).child("visible").setValue(false);
                        break;
                    case "evasive":
                        //System.out.println("MyTag: Shimmer!");
                        evasive = Integer.parseInt(stats[1]);
                       // System.out.println("MyTag: Shimmer!"+evasive);
                       // SharedData.mDatabase.child("users").child(SharedData.token).setValue(this);
                        break;
                    case "speed":
                        speed =Double.parseDouble(stats[1]);
                        break;
                    case "insanity":
                        mental = mental - Integer.parseInt(stats[1]);
                        break;
                    case "sanity":
                        mental = mental + Integer.parseInt(stats[1]);
                        if(mental > 100)
                        {
                            mental = 100;
                        }
                        break;

                    case "chain":
                        //System.out.println("resolving chain lightning!");
                        resolveChain(Integer.parseInt(stats[1]),Integer.parseInt(stats[2]),availableTargets);
                        break;
                    case "burst":
                       for(String secondaryTarget:availableTargets)
                       {
                           SharedData.mDatabase.child("returnSpells").child(secondaryTarget.split(":")[0]).push().setValue(spellEffect);
                       }
                        break;
                    case "forgetLast":
                        availableSpells = availableSpells.replace(lastCastSpell+",", "");
                        break;
                    case "forgetRandom":
                        String[] tempSpells = availableSpells.split(",");
                        int length = tempSpells.length;
                        random.nextInt(length);
                        availableSpells = availableSpells.replace(tempSpells[random.nextInt(length)]+",", "");
                        break;
                    case "mindLoop":
                        forcedSpell = lastCastSpell;
                        break;
                    case "mirror":
                        mirror = true;
                        break;
                    case "hideSpell":
                        hideSpell = true;
                        break;

                }
            }
                if(finalDamage>0)
                { color = "#00ff00";}

                if(caster.equals(target))
                {announcement = "" + "<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + caster + "</font>" + " cast " + spell.spellName + " at " + "themself" + "(<font color='"+color+"'>" + finalDamage + "</font>)";}
                else
                {
                announcement = "" + "<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + caster + "</font>" + " cast " + spell.spellName + " at " + "<font color='#"+NameGenerator.colorHex(targetColor)+"'>" + target + "</font>" + "(<font color='"+color+"'>" + finalDamage + "</font>)";
            }}




      // System.out.println("MyTag"+health);

        return announcement;
    }}
        return "???";
    }

    private String resolveMirrorBreak(Spell spell) {
        forcedSpell = spell.spellName;
       String announcement = "" + spell.casterName + " cast " + spell.spellName + " at " + spell.targetName + "("+"<font color='#EE0000'>"+ "Mirrored!" + "</font>)";
        mirror = false;
        return announcement;
    }

    private void resolveChain(int percent,int damage,HashSet<String> availableTargets) {

        ArrayList<String> targets = new ArrayList<>(availableTargets);
        int currentPercent = percent;
        Random random = new Random();
        int dice = SharedData.random.nextInt(100);
        while(dice<currentPercent)
        {
            dice = SharedData.random.nextInt(100);
            int target = random.nextInt(targets.size());
            SharedData.mDatabase.child("returnSpells").child(targets.get(target).split(":")[0]).push().setValue("chain:" + damage);
            currentPercent = currentPercent/2;
        }
        {

        }
    }

    private void broadCast(String s, HashSet<String> availableTargets) {
        for(String key:availableTargets)
        {
            String push = SharedData.mDatabase.push().getKey();
            SharedData.mDatabase.child("announcements").child("users").child(key.split(":")[0]).child(push).setValue("[" + System.currentTimeMillis() + "]" + s);
            //SharedData.mDatabase.child("users").child(spell.key).child("timeStamp").setValue(ServerValue.TIMESTAMP);
        }
    }

    private void resolveCooldown(int seconds) {
       long future = System.currentTimeMillis()+(seconds*1000);
        if(SharedData.player.timeStamp < future)
        {
            timeStamp = future;
        }
    }
    private int resolveFire(int heat) {
        temperature = temperature + heat;
        int damage = 0;
        if(temperature>100)
        {
            damage = temperature-100;
            temperature = 100;
        }
        health = health - damage;
        return damage;
    }
    private int resolveIce(int ice) {
        temperature = temperature - ice;
        int damage = 0;
        if(temperature<-100)
        {
            damage = temperature+100;
            temperature = -100;
        }
        health = health - Math.abs(damage);
        return Math.abs(damage);
    }
    public void resolveEffect(String effect,HashSet availableTargets){
        String[] stats = effect.split(":");
        switch(stats[0])
        {
            case "damage":
                health = health - resolveDamage(Integer.parseInt(stats[1]));
                break;
            case "heal":
                health = health + (Integer.parseInt(stats[1]));
                break;
            case "harm":
                health = health - (Integer.parseInt(stats[1]));
                break;
            case "shield":
                resolveShield(Integer.parseInt(stats[1]));
                break;
            case "barrier":
                resolveBarrier(Integer.parseInt(stats[1]));
                break;
            case "manaBurn":
                //mana = mana - Integer.parseInt(stats[1]);
                break;
            case "manabreath":
                //mana = mana - Integer.parseInt(stats[1]);
                break;
            case "invisibility":
                visible = false;
                break;


            case "burst":
                if(singleShield > 0) {
                    broadCast(username + " was Hit by a burst of "+ stats[1] + "(<font color='#EE0000'>" + "resisted!" + "</font>)", availableTargets);
                }
                else
                {
                    String casterColor = username.split(" The ")[1];
                    switch(stats[1])
                    {
                        case "fire":
                            broadCast("<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + username + "</font>" + " was Hit by a burst of fire" + "(<font color='#EE0000'>-" + resolveFire(Integer.parseInt(stats[2])) + "</font>)", availableTargets);
                            break;
                        case "ice":
                            broadCast("<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + username + "</font>" + " was Hit by a burst of cold" + "(<font color='#EE0000'>-" + resolveIce(Integer.parseInt(stats[2])) + "</font>)", availableTargets);
                            break;
                        case "damage":
                            broadCast("<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + username + "</font>" + " was Hit by a powerful shockwave" + "(<font color='#EE0000'>-" + resolveIce(Integer.parseInt(stats[2])) + "</font>)", availableTargets);
                            break;
                    }

                }
                break;


            case "chain":
               if(singleShield > 0) {
                   broadCast(username + " was Hit by Chain Lightning" + "(<font color='#EE0000'>" + "resisted!" + "</font>)", availableTargets);
               }
                else
               {
                   int damage = resolveDamage(Integer.parseInt(stats[1]));
                   String casterColor = username.split(" The ")[1];
                   broadCast("<font color='#"+NameGenerator.colorHex(casterColor)+"'>" + username + "</font>" + " was Hit by Chain Lightning" + "(<font color='#EE0000'>-" + damage + "</font>)", availableTargets);
               }
                break;
        }
    }
    private int resolveVampirism(int i,String casterToken) {
        int damage = i;
        //System.out.println("MyTag "+damage);
        if (damage > 0) {
            if (shield > 0) {
                if (shield > damage) {
                    shield = shield - damage;
                    damage = 0;
                } else {
                    damage = damage - shield;
                    shield = 0;
                    // System.out.println("MyTag "+damage);
                }
            }

            // System.out.println("MyTag "+damage);
            health = health - damage;
            SharedData.mDatabase.child("returnSpells").child(casterToken).push().setValue("heal:"+damage/2);

            }return damage;
    }
    private int resolveDamage(int rawDamage) {
        int damage = rawDamage;
        //System.out.println("MyTag "+damage);
        if (damage > 0) {
               if (shield > 0) {
                    if (shield > damage) {
                        shield = shield - damage;
                        damage = 0;
                    } else {
                        damage = damage - shield;
                        shield = 0;
                       // System.out.println("MyTag "+damage);
                    }
                }

           // System.out.println("MyTag "+damage);
            health = health - damage;
            return damage;
        }
        return 0;
    }
    private void resolveShield(int newShield) {
        if(newShield > shield)
        {
            shield = newShield;
        }
        else
        {

        }

    }
    private void resolveBarrier(int newShield) {
        if(newShield > singleShield)
        {
            singleShield = newShield;
        }
        else
        {

        }

    }
}
