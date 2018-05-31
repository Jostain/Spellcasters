package com.example.erik.spellcasters;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private ImageButton castSpell;
    GeoFire geoFire;
    private HashSet<String> availableTargets;
    private CountDownTimer timer;
    protected GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    protected LocationRequest mLocationRequest;
    protected GeoQuery geoQuery;
    public HashMap<String,String> announcementsMap;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;

    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        SpellLibrary.initiateLibrary();
        SharedData.targets = new ArrayList<>();
        setContentView(R.layout.activity_main);

        SharedData.mDatabase = FirebaseDatabase.getInstance().getReference();
        SharedData.mUsers = SharedData.mDatabase.child("users");
        SharedData.mUser = SharedData.mDatabase.child(SharedData.token);
        availableTargets = new HashSet<>();
        geoFire = new GeoFire(SharedData.mGeoFireUsers);
        SharedData.mGeoFireUsers = SharedData.mDatabase.child("geoFireUsers");
        geoFire = new GeoFire(SharedData.mGeoFireUsers);


        geoQuery = geoFire.queryAtLocation(new GeoLocation(SharedData.player.longitude, SharedData.player.latitude), 3);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                availableTargets.add(key);
                System.out.println("MyTag:"+key+" Detected!");
            }

            @Override
            public void onKeyExited(String key) {
                availableTargets.remove(key);
                System.out.println("MyTag:"+key+" Left Area!");
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println("MyTag:"+key+" On The move!");
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("MyTag:"+availableTargets+"availableTargets!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });

        castSpell = (ImageButton) findViewById(R.id.castSpell);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(SharedData.player.username);
        final TextView announcementView = (TextView) findViewById(R.id.Announcements);
        announcementView.setMovementMethod(new ScrollingMovementMethod());
        //switchView = (Switch) findViewById(R.id.switchView)
        castSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedData.player.timeStamp > System.currentTimeMillis()) {
                    Toast.makeText(MainActivity.this, "You are still recovering from your last spell!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(SharedData.player.health <= 0)
                {
                    Toast.makeText(MainActivity.this, "You need healing in order to cast new spells!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this, SpellBookActivity.class);
                    myIntent.putExtra("list", value); //Optional parameters
                    MainActivity.this.startActivityForResult(myIntent, 1);
                }
            }
        });
        String key = SharedData.mDatabase.push().getKey();
      //  SharedData.geoFireAnnouncements.setLocation(key, new GeoLocation(0, 0));
       // SharedData.mGeoFireAnnouncements.child(key).child("text").setValue("testing testing!");
       // Toast.makeText(MainActivity.this, "Created!", Toast.LENGTH_SHORT).show();
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String stredittext = data.getStringExtra("edittextvalue");
            }
        }
    }

    protected void onResume()
    {
        super.onResume();
        setupScreen();
        SharedData.healthMeter = (TextView) findViewById(R.id.healthMeter);
        SharedData.octarineMana = (TextView) findViewById(R.id.octarineMana);
        SharedData.purpleMana = (TextView) findViewById(R.id.purpleMana);
        SharedData.redMana = (TextView) findViewById(R.id.redMana);
        SharedData.orangeMana = (TextView) findViewById(R.id.orangeMana);
        SharedData.yellowMana = (TextView) findViewById(R.id.yellowMana);
        SharedData.greenMana = (TextView) findViewById(R.id.greenMana);
        SharedData.blueMana = (TextView) findViewById(R.id.blueMana);
        SharedData.cyanMana = (TextView) findViewById(R.id.cyanMana);
        SharedData.mentalMeter = (TextView) findViewById(R.id.sanityMeter);
        SharedData.shieldMeter = (TextView) findViewById(R.id.shieldMeter);
        SharedData.singleShieldMeter = (TextView) findViewById(R.id.blockMeter);
        SharedData.announcer = (TextView) findViewById(R.id.Announcements);
        SharedData.counter = (TextView) findViewById(R.id.coolDownMeter);
        SharedData.dodgeMeter = (TextView) findViewById(R.id.dodgeMeter);
        SharedData.speedMeter = (TextView) findViewById(R.id.speedMeter);
        SharedData.heatMeter = (TextView) findViewById(R.id.heatMeter);
        SharedData.updateStats();

        //Toast.makeText(MainActivity.this, ""+ SharedData.healthMeter, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this, "Resumed!", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();


      //  Toast.makeText(MainActivity.this, "Started!", Toast.LENGTH_SHORT).show();
    }

    private void setupScreen()
    {

        if (SharedData.initiated == false) {
            SharedData.mUsers.child(SharedData.token).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        User user = dataSnapshot.getValue(User.class);
                        SharedData.player = user;
                        Long currentLong = System.currentTimeMillis();
                        if (timer != null) {
                            timer.cancel();
                            timer.onFinish();
                        }
                        timer = new CountDownTimer((user.timeStamp) - currentLong, 1000) {


                            public void onTick(long millisUntilFinished) {
                                SharedData.counter.setText(""+ millisUntilFinished / 1000);

                                //here you can have your logic to set text to edittext
                            }

                            public void onFinish() {

                                SharedData.counter.setText("Ready to cast Spell!");
                            }

                        }.start();


                        SharedData.updateStats();


                    } catch (NullPointerException e) {

                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            SharedData.mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<String> list = new ArrayList<>();
                    //Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        list.add(user.username);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




            SharedData.mAnnouncements.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SharedData.announcements = "";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        SharedData.announcements = "<p>"+ postSnapshot.getValue()+"<p/>"+ SharedData.announcements;
                    }
                    SharedData.announcer.setText(Html.fromHtml( SharedData.announcements));
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            SharedData.mDatabase.child("incomingSpells").child(SharedData.token).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //System.out.println("MyTag: handling new spells!");
                    ArrayList<Spell> spellList = new ArrayList<>();
                    if (dataSnapshot.getChildrenCount() != 0) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String key = postSnapshot.getKey();
                            Spell spell = postSnapshot.getValue(Spell.class);
                            spell.key = key;
                            spellList.add(spell);
                        }
                        resolveSpell(spellList.get(0));
                        SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);
                        SharedData.mDatabase.child("incomingSpells").child(SharedData.token).child(spellList.get(0).key).removeValue();

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            SharedData.mDatabase.child("returnSpells").child(SharedData.token).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //System.out.println("MyTag: handling new spells!");
                    ArrayList<String> effectList = new ArrayList<>();
                    ArrayList<String> keyList = new ArrayList<>();
                    if (dataSnapshot.getChildrenCount() != 0) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String key = postSnapshot.getKey();
                            String effect = postSnapshot.getValue(String.class);
                            keyList.add(key);
                            effectList.add(effect);
                            //System.out.println(spell.spellName);
                        }
                        //System.out.println("MyTag:"+spellList);
                        SharedData.player.visible = true;
                        SharedData.mDatabase.child("users").child(SharedData.token).child("visible").setValue(true);
                        SharedData.mDatabase.child("visible").child(SharedData.token).setValue(true);
                        SharedData.player.resolveEffect(effectList.get(0),availableTargets);
                        SharedData.mDatabase.child("returnSpells").child(SharedData.token).child(keyList.get(0)).removeValue();
                        SharedData.mDatabase.child("users").child(SharedData.token).setValue(SharedData.player);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            SharedData.initiated = true;
        }

    }
    private void resolveSpell(Spell spell) {


        String announcement = SharedData.player.resolveSpell(spell,availableTargets);
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        if(!spell.silent)
        {
            for (String key : availableTargets) {
                String push = SharedData.mDatabase.push().getKey();
                SharedData.mDatabase.child("announcements").child("users").child(key.split(":")[0]).child(push).setValue("[" + ts + "]" + announcement);
            }
        }
        SharedData.mUsers.child(SharedData.token).setValue(SharedData.player);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {
        if(SharedData.player.timeStamp < System.currentTimeMillis()) {
            mCurrentLocation = location;
            SharedData.player.longitude = mCurrentLocation.getLongitude();
            SharedData.player.latitude = mCurrentLocation.getLatitude();
            geoFire.setLocation(SharedData.token+":"+SharedData.player.username, new GeoLocation(mCurrentLocation.getLongitude(), mCurrentLocation.getLatitude()));
            SharedData.mDatabase.child("visible").child(SharedData.token).setValue(SharedData.player.visible);
            geoQuery.setCenter(new GeoLocation(mCurrentLocation.getLongitude(),mCurrentLocation.getLatitude()));
            SharedData.mUsers.child(SharedData.token).setValue(SharedData.player);
            SharedData.mDatabase.child("geoFireUsers").child(SharedData.token+":"+SharedData.player.username).child("timestamp").setValue(System.currentTimeMillis());
            //Toast.makeText(this, location.toString() + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {createLocationRequest();}

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Log.i("LocationFragment", "Connection failed: ConnectionResult.getErrorCode() " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        //Log.i("LocationFragment", "Connection suspended");
        mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
}
