package com.example.focus;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity2 extends AppCompatActivity{

    int REQUEST_LOCATION = 123;
    private ArrayList<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Context context;
    private GeofencingClient mGeofenceClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeofenceList = new ArrayList<>();
        mGeofencePendingIntent = null;
        context = getApplicationContext();

        populateGeofenceList();
        mGeofenceClient = LocationServices.getGeofencingClient(this);
    }

    public void populateGeofenceList() {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        databaseReference = firebaseDatabase.getInstance().getReference();
        DatabaseReference user = databaseReference.child("users").child(androidID);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, MyLocation> focusLocations = dataSnapshot.getValue(HashMap.class);
                Set<String> keySet = focusLocations.keySet();
                for (String key : keySet) {
                    MyLocation location = focusLocations.get(key);
                    mGeofenceList.add(new Geofence.Builder()
                            .setRequestId(location.getName())
                            .setCircularRegion(
                                    location.getLat(),
                                    location.getLng(),
                                    (float) location.getRadius())
                            .setExpirationDuration(12 * 60 * 60 * 1000)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                            .build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed");
            }
        });
    }

}
