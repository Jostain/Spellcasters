package com.example.erik.spellcasters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Erik on 2017-04-21.
 */

public class SpellLibrary {
    public static HashMap<String,Spell> library;
    public static void initiateLibrary()
    {
        library = new HashMap<>();
        library.put("Absorb Mana",new Spell("Absorb Mana","siphons mana from the surrounding area","absorbMana","0:0:0:0:0:0:0:0","siphon",100,1));
        library.put("Amnesia",new Spell("Amnesia","Target forgets the last spell it has cast. The target must now meditate in order to regain the lost knowlege","Forget","2:5:0:0:0:0:2:0","forgetLast",0,1));
        library.put("Blood Barrier",new Spell("Blood Barrier","Caster uses some of their own blood to create a barrier around the target. Caster loses 20 health and the target gains 1 barrier","BloodBarrier","0:0:5:1:0:2:0:0","barrier:1:harm:20",30,1));
        library.put("Blood Transfusion",new Spell("Blood Transfusion","Caster Transfer 20 health to target","BloodTransfusion","0:0:5:2:0:0:0:0","heal:20:harm:20",60,1));
        library.put("Chain Lightning",new Spell("Chain Lightning","A bolt of lightning spring forth from the casters hand striking the target with the potential of bouncing of the target and hitting additional targets for 10 damage. This includes caster wich makes it a risky spell in a target poor enviroment","ChainLightning","0:0:0:1:0:0:2:5","damage:20,chain:100:10",120,1));
        library.put("Chaos Orb",new Spell("Chaos Orb","Nobody knows what this does. Not even the caster themself","ChaosOrb","1:1:1:1:1:1:1:1","chaos:10",30,1));
        library.put("Earth Coffin",new Spell("Earth Coffin","The earth beneath the target becomes alive and swallows the target alive. The process does 40 crushing damage to the target and imobalizes them for 5 minutes","EarthCoffin","0:0:2:0:1:5:0:0","damage:40,shield:20,cooldown:360",0,1));
        library.put("Fire Lance",new Spell("Fire Lance","A beam of searing heat hits the target causing 10 force damage as well as 30 fire damage","FireLance","0:0:0:5:0:1:0:0","damage:10,fire:30",30,1));
        library.put("Fireball",new Spell("Fireball","Caster lobs a large orb of consentrated flame that explodes on impact causing firedamage to the surrounding","Fireball","0:0:0:5:0:0:0:3","damage:30,fire:10,burst:fire:20",60,1));
        library.put("Fire Burst",new Spell("Fire Burst","A cone of fire spews forth ","ImageCode","0:0:0:5:0:0:0:0","damage:10,fire:10",20,1));
        library.put("Forgetfulness",new Spell("Forgetfulness","Target forgets a random spell and will need to meditate in order to regain the lost knowlege","forgetfulness","2:5:0:0:0:0:0:0","forgetRandom",60,1));
        library.put("Healing Hand",new Spell("Healing Hand","heals target for 10 health","HealingHand","1:0:5:0:0:1:0:0","heal:10",30,1));
        library.put("Hide Spell",new Spell("Hide Spell","next spell will not be announced to other players","HideSpell","2:2:0:0:0:0:0:3","hideSpell",60,1));
        library.put("Ice Shard",new Spell("Ice Shard","The Caster creates a shard of frozen water from the air moisture and launches it towards the target causing 10 physical damage as well as 10 frost damage","IceShard","0:0:0:5:0:2:0:1","damage:10,ice:10",15,1));
        library.put("Invicibility",new Spell("Invicibility","Renders the caster invicible to other casters until a they cast a new spell","Invicible","5:0:0:0:0:0:0:5","Invicibility",60,1));
        library.put("Learn Spell",new Spell("Learn Spell","The caster spends time and resources attemptning to learn a new spell. If succesfull the spell will be memorized after a meditation.","LearnSpell","5:5:5:5:5:5:5:5","none",60,1));
        library.put("Lightning Bolt",new Spell("Lightning Bolt","The Caster calls down a lightning bolt from the sky, striking the target. The lightning does 20 physical damage as well as searing the target for 10 heat damage with a small chance of shocking surrounding targets(5 damage).","Lightning Bolt","0:0:0:1:0:0:5:3","damage:20,heat:10,chain:15:5",120,1));
        library.put("Magic Armor",new Spell("Magic Armor","A shell of raw magic solidifies and forms around the caster creating a weightless armor","MagicArmor","3:0:5:0:0:0:0:0","Shield:30",10,1));
        library.put("Magic Missile",new Spell("Magic Missile","The caster forms a ball of pure magic and trows it at the target(10 damage)","MagicMissile","1:0:0:1:1:0:1:1","damage:10",10,1));
        library.put("Mana Breath(Octarine)",new Spell("Mana Breath(Octarine)","Transfer 10 units of octarine mana to the target","ManaBreath","10:0:0:0:0:0:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Purple)",new Spell("Mana Breath(Purple)","Transfer 10 units of octarine mana to the target","ManaBreath",  "0:10:0:0:0:0:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Red)",new Spell("Mana Breath(Red)","Transfer 10 units of octarine mana to the target","ManaBreath",     "0:0:10:0:0:0:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Orange)",new Spell("Mana Breath(Orange)","Transfer 10 units of octarine mana to the target","ManaBreath",  "0:0:0:10:0:0:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Yellow)",new Spell("Mana Breath(Yellow)","Transfer 10 units of octarine mana to the target","ManaBreath",  "0:0:0:0:10:0:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Green)",new Spell("Mana Breath(Green)","Transfer 10 units of octarine mana to the target","ManaBreath",   "0:0:0:0:0:10:0:0","manaBreath:10",10,1));
        library.put("Mana Breath(Blue)",new Spell("Mana Breath(Blue)","Transfer 10 units of octarine mana to the target","ManaBreath",    "0:0:0:0:0:0:10:0","manaBreath:10",10,1));
        library.put("Mana Breath(Cyan)",new Spell("Mana Breath(Cyan)","Transfer 10 units of octarine mana to the target","ManaBreath",    "0:0:0:0:0:0:0:10","manaBreath:10",10,1));
        library.put("Mana Burn(Octarine)",new Spell("Mana Burn(Octarine)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","10:0:0:0:0:0:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Purple)",new Spell("Mana Burn(Purple)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:10:0:0:0:0:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Red)",new Spell("Mana Burn(Red)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:10:0:0:0:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Orange)",new Spell("Mana Burn(Orange)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:0:10:0:0:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Yellow)",new Spell("Mana Burn(Yellow)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:0:0:10:0:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Green)",new Spell("Mana Burn(Green)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:0:0:0:10:0:0","manaBurn:10",0,1));
        library.put("Mana Burn(Blue)",new Spell("Mana Burn(Blue)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:0:0:0:0:10:0","manaBurn:10",0,1));
        library.put("Mana Burn(Cyan)",new Spell("Mana Burn(Cyan)","By using their own mana the caster tries to negate the targets mana supply","ManaBurn","0:0:0:0:0:0:0:10","manaBurn:10",0,1));
        library.put("Meditate",new Spell("Meditate","Places the caster in a meditative state returning the casters basic mana and health. The meditation returns the health instantly but the caster can not interact with the world for 8 hours.","Meditate","0:0:0:0:0:0:0:0","meditate",120,1));
        library.put("Mind Loop",new Spell("Mind Loop","Mind loop compels the target to repeat its previous action","MindLoop","0:15:0:0:0:0:0:0","mindLoop:insanity:20",60,1));
        library.put("Mirror Image",new Spell("Mirror Image","The Caster creates five Mirror images to confuse their attacker","ImageCode","2:0:5:0:0:0:0:1","barrier:5",10,1));
        library.put("Mirror Spell",new Spell("Mirror Spell","Creates a reflective barrier around the caster, bouncing back the next incoming spell","MirrorSpell","10:0:5:0:0:0:0:0","mirror",10,1));
        library.put("Shimmer",new Spell("Shimmer","A cloak of shimmering energy envelopes the caster making them harder to hit","Shimmer","5:0:5:0:0:0:0:5","evasive:40",10,1));
        library.put("Silence",new Spell("Silence","Silence falls around the target making anyone within range incapable of casting spells for a time(This includes the caster)","ImageCode","5:1:5:1:1:1:1:5","effects",20,1));
        library.put("Spell Parry",new Spell("Spell Parry","Not today! Caster parries the next incoming spell","SpellParry","10:0:0:0:0:0:0:0","barrier:1",10,1));
        library.put("Stone Skin",new Spell("Stone Skin","The targets skin hardens and creates improved protection at the cost of a slower casting time.","ImageCode","2:0:2:2:0:2:0:0","effects",20,1));
        library.put("Vampire Fang",new Spell("Vampire Fang","Sucks the life force(30 health) out of the target to heal themself.","VampireFang","2:0:15:0:1:2:0:0","vampirism:30",15,1));
        //library.put("",new Spell("name","description","ImageCode","0:0:0:0:0:0:0:0","effects",0,1));
    }

    public static String getRandomSpell() {
        String[] strings = library.keySet().toArray(new String[library.size()]);
        Collections.shuffle(Arrays.asList(strings));
        return strings[0];
    }
}
