package com.example.erik.spellcasters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static android.R.attr.value;

public class SetupActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 87;

    private TextView loading;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        loading = (TextView) findViewById(R.id.loading);




        int permissionCheck = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED && permissionLocation == PackageManager.PERMISSION_GRANTED)
        {
            setupData();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    //enterButton.setEnabled(true);


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    loading.setText("Checking Permissions...");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private String getDeviceId() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }
    private void writeNewUser(String userId, String name, int health,int mana, List<String> list) {
        User user = new User(name,health,mana);

        SharedData.mDatabase.child("users").child(userId).setValue(user);
        //SharedData.mDatabase.child("locations").child(1+":"+1).child(userId).setValue(user.username);

    }
    private void setupData()
    {
        SharedData.random = new Random();
        SharedData.token = getDeviceId();
        SharedData.mDatabase = FirebaseDatabase.getInstance().getReference();
        SharedData.mAnnouncements = SharedData.mDatabase.child("announcements").child("users").child(SharedData.token);
        SharedData.mUsers = SharedData.mDatabase.child("users");
        //SharedData.mGeoFireAnnouncements = SharedData.mDatabase.child("geoFireAnnouncements");
        SharedData.mGeoFireUsers = SharedData.mDatabase.child("geoFireUsers");
        SharedData.mGeoFireMana = SharedData.mDatabase.child("geoFireMana");
       // SharedData.geoFireAnnouncements = new GeoFire(SharedData.mGeoFireAnnouncements);

        SharedData.mUsers.child(SharedData.token).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedData.player = dataSnapshot.getValue(User.class);
                try{
                    loading.setText("Loading Profile...");
                    System.out.println(SharedData.player.username);
                    Intent myIntent = new Intent(SetupActivity.this, MainActivity.class);
                    myIntent.putExtra("list", value); //Optional parameters
                    SetupActivity.this.startActivityForResult(myIntent,1);
                    finish();
                }catch(NullPointerException e)
                {
                    loading.setText("Creating New Data...");
                    writeNewUser(SharedData.token,NameGenerator.generate(),100,100,new ArrayList<String>());
                    //System.out.println("myTag: writing new player");
                    Intent myIntent = new Intent(SetupActivity.this, SetupActivity.class);
                    myIntent.putExtra("list", value); //Optional parameters
                    SetupActivity.this.startActivityForResult(myIntent,1);
                    finish();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
