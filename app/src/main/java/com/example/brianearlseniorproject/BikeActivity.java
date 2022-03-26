package com.example.brianearlseniorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BikeActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    public static final int DEFAULT_UPDATE_INTERVAL = 1000;
    public static final int FAST_UPDATE_INTERVAL = 500;
    public static final String IS_UPDATING = "isUpdating";
    Button btn_addBike;
    ImageButton ib_bikeHome;
    EditText et_bikeDate, et_bikeTime, et_bikeDistance;
    ListView lv_bikeList;
    Switch sw_trackBike;
    ArrayAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Location currentLocation;
    Location previousLocation;
    double totalDistance;
    boolean isUpdatingLocation = false;
    long trackedTime = 0L;
    long previousTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        btn_addBike = findViewById(R.id.btn_addBike);
        ib_bikeHome = findViewById(R.id.ib_bikeHome);
        et_bikeDate = findViewById(R.id.et_bikeDate);
        et_bikeTime = findViewById(R.id.et_bikeTime);
        et_bikeDistance = findViewById(R.id.et_bikeDistance);
        lv_bikeList = findViewById(R.id.lv_bikeList);
        sw_trackBike = findViewById(R.id.sw_trackBike);
        dataBaseHelper = new DataBaseHelper(BikeActivity.this);
        ShowBikesOnListView(dataBaseHelper);
        currentLocation = null;
        previousLocation = null;

        sw_trackBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Is the switch is on?
                boolean on = ((Switch) v).isChecked();
                if (on) {
                    trackedTime = 0L;
                    previousTime = 0L;
                    totalDistance = 0.0;
                    //Do something when switch is on/checked
                    if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    } else {
                        //Req Location Permission
                        startLocService();
                    }
                } else {
                    sw_trackBike.setText("Tracking Off");
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                    isUpdatingLocation = false;
                }
            }
        });

        //button Listeners for the home, add and view all buttons
        ib_bikeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BikeModel bikeModel;
                try {
                    String time = et_bikeTime.getText().toString();
                    String tArr[] = time.split(":");
                    float h, mm, ss;
                    mm = Float.parseFloat(tArr[0]);
                    ss = Float.parseFloat(tArr[1]);
                    h = ((ss / 60) + mm)/60;
                    float distance = Float.parseFloat(et_bikeDistance.getText().toString());
                    float speed = distance / h;
                    bikeModel = new BikeModel(-1, et_bikeDate.getText().toString(), time, distance, speed);
                    //Toast.makeText(BikeActivity.this, bikeModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BikeActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    bikeModel = new BikeModel(-1, "01-01-2000", "00:00", 0, 0);
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(BikeActivity.this);
                boolean success = dataBaseHelper.addBikeWorkout(bikeModel);
                Toast.makeText(BikeActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowBikesOnListView(dataBaseHelper);
            }
        });

        lv_bikeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BikeModel clickedBike = (BikeModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteBike(clickedBike);
                ShowBikesOnListView(dataBaseHelper);
                Toast.makeText(BikeActivity.this, "Deleted" + clickedBike.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //save the location
                currentLocation = locationResult.getLastLocation();

                //Toast.makeText(BikeActivity.this, "Lat: " + currentLocation.getLatitude() + " Lon: " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                if(previousLocation != null) {
                    totalDistance += distance(previousLocation.getLatitude(), currentLocation.getLatitude(), previousLocation.getLongitude(), currentLocation.getLongitude());
                    long currentTime = new Date().getTime();
                    if (previousTime != 0L) {
                        trackedTime += (currentTime - previousTime);
                    }
                    previousTime = currentTime;
                }
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                String todayDate = dateFormat.format(date);
                et_bikeDate.setText(todayDate);
                long mm, ss;
                mm = (trackedTime / 1000) / 60;
                ss = (trackedTime / 1000) % 60;
                et_bikeTime.setText(mm + ":" + ss);
                et_bikeDistance.setText(String.format("%.2f", totalDistance));
                //Toast.makeText(BikeActivity.this, "Time: " + trackedTime / 1000 + "   Total Distance: " + String.format("%.2f", totalDistance) + " miles", Toast.LENGTH_SHORT).show();
                previousLocation = currentLocation;
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putBoolean(IS_UPDATING, isUpdatingLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state.getBoolean(IS_UPDATING, false)) {
            startLocService();
            sw_trackBike.setChecked(true);
        }
    }

    private void startLocService() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        isUpdatingLocation = true;
        sw_trackBike.setText("Tracking On");
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocService();
                } else {
                    Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }

    private void ShowBikesOnListView (DataBaseHelper dataBaseHelper2) {
        arrayAdapter = new ArrayAdapter<BikeModel>(BikeActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAllBikeWorkouts());
        lv_bikeList.setAdapter(arrayAdapter);
    }

    // Java program to calculate Distance Between Two Points on Earth
    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in miles. Use 6371 for km
        double r = 3956;

        // calculate the result
        return(c * r);
    }
}