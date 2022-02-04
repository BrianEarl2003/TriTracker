package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class BikeActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addBike;
    ImageButton ib_bikeHome;
    EditText et_bikeDate, et_bikeTime, et_bikeDistance;
    ListView lv_bikeList;
    ArrayAdapter bikeArrayAdapter;
    DataBaseHelper bikeDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        btn_addBike = findViewById(R.id.btn_addBike);
        ib_bikeHome = findViewById(R.id.ib_bikeHome);
        et_bikeDate = findViewById(R.id.et_bikeDate);
        et_bikeTime = findViewById(R.id.et_bikeTime);
        et_bikeDistance = findViewById(R.id.et_bikeDistance);
        lv_bikeList = findViewById(R.id.lv_runList);
        bikeDataBaseHelper = new DataBaseHelper(BikeActivity.this);
        ShowBikesOnListView(bikeDataBaseHelper);

        //button Listeners for the home, add and view all buttons
        ib_bikeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BikeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_addBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BikeModel bikeModel;
                try {
                    bikeModel = new BikeModel(-1, et_bikeDate.getText().toString(), et_bikeTime.getText().toString(), Float.parseFloat(et_bikeDistance.getText().toString()));
                    Toast.makeText(BikeActivity.this, bikeModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BikeActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    bikeModel = new BikeModel(-1, "01-01-2000", "00:00", 0);
                }
                DataBaseHelper bikeDataBaseHelper = new DataBaseHelper(BikeActivity.this);
                boolean success = bikeDataBaseHelper.addBikeWorkout(bikeModel);
                Toast.makeText(BikeActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowBikesOnListView(bikeDataBaseHelper);
            }
        });

        lv_bikeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BikeModel clickedBike = (BikeModel) parent.getItemAtPosition(position);
                bikeDataBaseHelper.deleteBike(clickedBike);
                ShowBikesOnListView(bikeDataBaseHelper);
                Toast.makeText(BikeActivity.this, "Deleted" + clickedBike.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowBikesOnListView (DataBaseHelper bikeDataBaseHelper2) {
        bikeArrayAdapter = new ArrayAdapter<BikeModel>(BikeActivity.this, android.R.layout.simple_list_item_1, bikeDataBaseHelper2.getAllBikeWorkouts());
        lv_bikeList.setAdapter(bikeArrayAdapter);
    }
}