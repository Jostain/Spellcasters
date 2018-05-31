package com.example.erik.spellcasters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

//import com.google.firebase.iid.FirebaseInstanceId;

public class targetPlayerActivity extends AppCompatActivity {
    ListView listView ;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private DatabaseReference mUsers = mDatabase.child("users");
    private DatabaseReference mLocation = mDatabase.child("locations");
    String[] list;
    ArrayAdapter<String> adapter;
    HashSet<String> availableTargets = new HashSet<>();
    ArrayList<String> targetTokens = new ArrayList<>();
    String area;
    private GeoFire geoFire;
    protected GeoQuery geoQuery;
    private ArrayList<String> finalTargets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final targetPlayerActivity activity = this;
  if(SharedData.storage.spellName.equals("Meditate"))
  {
      SharedData.player.health = 100;
      // mana = 100;
      SharedData.player.availableSpells = SharedData.player.spellbook;
      SharedData.player.timeStamp = System.currentTimeMillis()+50000;
      SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);
      finish();
  }
  else if(SharedData.storage.spellName.equals("Learn Spell"))
  {

      String newSpell = SpellLibrary.getRandomSpell();
      String[] manacost = SharedData.storage.manaCost.split(":");
      if(!SharedData.player.spellbook.contains(newSpell)) {
          SharedData.player.spellbook = SharedData.player.spellbook + "," + newSpell;
      }
      Toast.makeText(activity, "You learned " + newSpell, Toast.LENGTH_SHORT).show();
      SharedData.player.octarineMana = SharedData.player.octarineMana-Integer.parseInt(manacost[0]);
      SharedData.player.purpleMana = SharedData.player.purpleMana-Integer.parseInt(manacost[1]);
      SharedData.player.redMana = SharedData.player.redMana-Integer.parseInt(manacost[2]);
      SharedData.player.orangeMana = SharedData.player.orangeMana-Integer.parseInt(manacost[3]);
      SharedData.player.yellowMana = SharedData.player.yellowMana-Integer.parseInt(manacost[4]);
      SharedData.player.greenMana = SharedData.player.greenMana-Integer.parseInt(manacost[5]);
      SharedData.player.blueMana = SharedData.player.blueMana-Integer.parseInt(manacost[6]);
      SharedData.player.cyanMana = SharedData.player.cyanMana-Integer.parseInt(manacost[7]);

      SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);
      finish();
  }
  else if(SharedData.storage.spellName.equals("Summon minor Demon"))
  {
      Demon.summonDemon("minor");
  }
  else if(SharedData.storage.spellName.equals("Summon Demon"))
  {
      Demon.summonDemon("standard");
  }
  else if(SharedData.storage.spellName.equals("Summon major Demon"))
  {
      Demon.summonDemon("major");
  }
  else if(SharedData.storage.spellName.equals("Summon leviathan"))
  {
      Demon.summonDemon("leviathan");
  }
  else if(SharedData.storage.spellName.equals("Absorb Mana"))
   {

       final int[] finalPenalty = new int[1];
       SharedData.player.timeStamp = System.currentTimeMillis()+((SharedData.storage.coolDown*(long)(SharedData.player.speed))*1000);
       geoFire = new GeoFire(SharedData.mGeoFireMana);
       geoQuery = geoFire.queryAtLocation(new GeoLocation(SharedData.player.longitude, SharedData.player.latitude), 0.5);
       geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
           int penalty = 0;
           int octarine = 0;
           int purple = 0;
           int red = 0;
           int orange = 0;
           int yellow = 0;
           int green = 0;
           int blue = 0;
           int cyan = 0;
           @Override
           public void onKeyEntered(final String key, GeoLocation location) {
            switch(key.split(":")[1])
            {
                case "octarine":
                   octarine++;
                    break;
                case "purple":
                    purple++;
                    break;
                case "red":
                    red++;
                    break;
                case "orange":
                    orange++;
                    break;
                case "yellow":
                    yellow++;
                    break;
                case "green":
                    green++;
                    break;
                case "blue":
                    blue++;
                    break;
                case "cyan":
                    cyan++;
                    break;
            }
           }

           @Override
           public void onKeyExited(String key) {

           }

           @Override
           public void onKeyMoved(String key, GeoLocation location) {

           }

           @Override
           public void onGeoQueryReady() {
                SharedData.player.octarineMana =SharedData.player.octarineMana + SharedData.random.nextInt(20)*octarine;
               SharedData.player.redMana = SharedData.player.redMana + SharedData.random.nextInt(20)*red;
               SharedData.player.purpleMana =SharedData.player.purpleMana + SharedData.random.nextInt(20)*purple;
               SharedData.player.orangeMana = SharedData.player.orangeMana + SharedData.random.nextInt(20)*orange;
               SharedData.player.yellowMana =SharedData.player.yellowMana + SharedData.random.nextInt(20)*yellow;
               SharedData.player.greenMana =SharedData.player.greenMana + SharedData.random.nextInt(20)*green;
               SharedData.player.blueMana = SharedData.player.blueMana + SharedData.random.nextInt(20)*blue;
               SharedData.player.cyanMana =SharedData.player.cyanMana + SharedData.random.nextInt(20)*cyan;
              // System.out.println(SharedData.penalty);
               geoFire = new GeoFire(SharedData.mGeoFireMana);
               //SharedData.mGeoFireUsers = SharedData.mDatabase.child("geoFireMana");
               if(octarine+red+purple+orange+yellow+green+blue+cyan==0)
               {
                   String[] colors = new String[]{"octarine","red","purple","orange","yellow","green","blue","cyan"};
                   String key = SharedData.mGeoFireMana.push().getKey()+":"+colors[SharedData.random.nextInt(7)];
                   geoFire.setLocation(key, new GeoLocation(SharedData.player.longitude, SharedData.player.latitude));
               }
               SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);

           }

           @Override
           public void onGeoQueryError(DatabaseError error) {

           }
       });


       finish();
   }
   else
   {

        setContentView(R.layout.activity_target_player);
        //area = SharedData.player.location;
        System.out.println(area);
      //  listView = (ListView) findViewById(R.id.availableTargets);
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.pickTargetToolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Pick spell target");
        availableTargets.add("tester!");
        availableTargets = new HashSet<String>();
        geoFire = new GeoFire(SharedData.mGeoFireUsers);
        SharedData.mGeoFireUsers = SharedData.mDatabase.child("geoFireUsers");
        
        geoQuery = geoFire.queryAtLocation(new GeoLocation(SharedData.player.longitude, SharedData.player.latitude), 3);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, GeoLocation location) {
                SharedData.mDatabase.child("visible").child(key.split(":")[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean visible = dataSnapshot.getValue(Boolean.class);
                        if(visible||key.split(":")[0].equals(SharedData.token)){availableTargets.add(key);}
                        //System.out.println("MyTag:"+key+" Detected!");
                        updateList(activity);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onKeyExited(String key) {
                availableTargets.remove(key);
                System.out.println("MyTag:"+key+" Left Area!");
                updateList(activity);
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println("MyTag:"+key+" On The move!");
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("MyTag:"+availableTargets+"availableTargets!");
                updateList(activity);

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }}

    private void updateList(final targetPlayerActivity activity)
{
    finalTargets = new ArrayList<>();
    for (String target : availableTargets) {
        String name = target.split(":")[1];
        // long timeStamp = postSnapshot.child("timeStamp").getValue(Long.class);
        String token = target.split(":")[0];
        System.out.println("token:"+token);
        targetTokens.add(0,token);
        finalTargets.add(0,name);
    }

    // System.out.println("MyTag testing 2!");
    listView = (ListView) findViewById(R.id.list);
    list = new String[]{};
    adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1, android.R.id.text1, finalTargets)
    {
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            // Get the Item from ListView
            View view = super.getView(position, convertView, parent);

            // Initialize a TextView for ListView each Item
            TextView tv = (TextView) view.findViewById(android.R.id.text1);

            // Set the text color of TextView (ListView Item)
            String targetColor = (String) tv.getText();
            String color = targetColor.split(" The ")[1];
            System.out.print("target"+color);
            String hexColor = NameGenerator.colorHex("Aqua");
            tv.setTextColor(Color.parseColor("#"+hexColor));

            // Generate ListView Item using TextView
            return view;
        }
    };;
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("MyTag"+SharedData.player.timeStamp);
            SharedData.storage.targetToken = targetTokens.get(position);
            SharedData.storage.targetName = finalTargets.get(position);
            SharedData.storage.casterName = SharedData.player.username;
            SharedData.storage.casterToken = SharedData.token;
            SharedData.storage.area = area;
            castSpell(SharedData.storage);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            Toast.makeText(activity, "You cast "+ SharedData.storage.spellName, Toast.LENGTH_SHORT).show();

           SharedData.mUsers.child(SharedData.token).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    finish();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }
            );
        }});
    adapter.notifyDataSetChanged();

}
    private void castSpell(Spell spell) {

        if(SharedData.player.hideSpell)
        {
            spell.silent = true;
        }
        SharedData.player.timeStamp = System.currentTimeMillis()+((SharedData.storage.coolDown*(long)(SharedData.player.speed))*1000);
        //.player.mana = SharedData.player.mana - Spell.storage.manaCost;
        SharedData.player.visible = true;
        SharedData.player.speed = 1;
        SharedData.player.forcedSpell = "";
        SharedData.player.hideSpell = false;
        SharedData.player.lastCastSpell = spell.spellName;
        SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);
        String key = mDatabase.child("incomingSpells").child(spell.targetToken).push().getKey();
        mDatabase.child("incomingSpells").child(spell.targetToken).child(key).setValue(spell);
        String[] manaCost = spell.manaCost.split(":");

        SharedData.player.octarineMana = SharedData.player.octarineMana-Integer.parseInt(manaCost[0]);
        SharedData.player.purpleMana = SharedData.player.purpleMana-Integer.parseInt(manaCost[1]);
        SharedData.player.redMana = SharedData.player.redMana-Integer.parseInt(manaCost[2]);
        SharedData.player.orangeMana = SharedData.player.orangeMana-Integer.parseInt(manaCost[3]);
        SharedData.player.yellowMana = SharedData.player.yellowMana-Integer.parseInt(manaCost[4]);
        SharedData.player.greenMana = SharedData.player.greenMana-Integer.parseInt(manaCost[5]);
        SharedData.player.blueMana = SharedData.player.blueMana-Integer.parseInt(manaCost[6]);
        SharedData.player.cyanMana = SharedData.player.cyanMana-Integer.parseInt(manaCost[7]);

        SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Intent intent = new Intent();
                // intent.putExtra("edittextvalue","value_here");
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
    }



