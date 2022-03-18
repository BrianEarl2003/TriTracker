package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class BikeGoalActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addBikeGoal;
    ImageButton ib_bikeHome3;
    EditText et_bikeTime3, et_bikeDistance3;
    DataBaseHelper bikeGoalDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_goal);

        btn_addBikeGoal = findViewById(R.id.btn_addBikeGoal);
        ib_bikeHome3 = findViewById(R.id.ib_bikeHome3);
        et_bikeTime3 = findViewById(R.id.et_bikeTime3);
        et_bikeDistance3 = findViewById(R.id.et_bikeDistance3);
        bikeGoalDataBaseHelper = new DataBaseHelper(BikeGoalActivity.this);

        //button Listeners for the home, add and view all buttons
        ib_bikeHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_addBikeGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BikeGoalModel bikeGoalModel;// = new BikeGoalModel(-1, "", 0.0F);
                try {
                    bikeGoalModel = new BikeGoalModel(-1, et_bikeTime3.getText().toString(), Float.parseFloat(et_bikeDistance3.getText().toString()));
                    Toast.makeText(BikeGoalActivity.this, bikeGoalModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BikeGoalActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    bikeGoalModel = new BikeGoalModel(-1, "00:00", 0);
                }
                DataBaseHelper bikeGoalDataBaseHelper = new DataBaseHelper(BikeGoalActivity.this);
                boolean success = bikeGoalDataBaseHelper.addBikeGoal(bikeGoalModel);
                Toast.makeText(BikeGoalActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
