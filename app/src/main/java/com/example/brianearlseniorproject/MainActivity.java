package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn_log, btn_swim, btn_bike, btn_run;
    ImageButton ib_preferences;
    TextView tv_PB_swim_miles, tv_PB_bike_miles, tv_PB_run_miles,
            tv_PB_swim_HM, tv_PB_bike_HM, tv_PB_run_HM,
            tv_G_swim_miles, tv_G_bike_miles, tv_G_run_miles,
            tv_G_swim_HM, tv_G_bike_HM, tv_G_run_HM;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_log = findViewById(R.id.btn_log);
        btn_swim = findViewById(R.id.btn_swim);
        btn_bike = findViewById(R.id.btn_bike);
        btn_run = findViewById(R.id.btn_run);
        ib_preferences = findViewById(R.id.ib_preferences);
        tv_PB_swim_miles = findViewById(R.id.tv_PB_swim_miles);
        tv_PB_bike_miles = findViewById(R.id.tv_PB_bike_miles);
        tv_PB_run_miles = findViewById(R.id.tv_PB_run_miles);
        tv_PB_swim_HM = findViewById(R.id.tv_PB_swim_HM);
        tv_PB_bike_HM = findViewById(R.id.tv_PB_bike_HM);
        tv_PB_run_HM = findViewById(R.id.tv_PB_run_HM);
        tv_G_swim_miles = findViewById(R.id.tv_G_swim_miles);
        tv_G_bike_miles = findViewById(R.id.tv_G_bike_miles);
        tv_G_run_miles = findViewById(R.id.tv_G_run_miles);
        tv_G_swim_HM = findViewById(R.id.tv_G_swim_HM);
        tv_G_bike_HM = findViewById(R.id.tv_G_bike_HM);
        tv_G_run_HM = findViewById(R.id.tv_G_run_HM);

        btn_swim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SwimActivity.class);
                startActivity(i);
            }
        });

        btn_bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BikeActivity.class);
                startActivity(i);
            }
        });

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RunActivity.class);
                startActivity(i);
            }
        });
    }
}