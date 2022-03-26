package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeFormat;
//import com.jjoe64.graphview.GraphView;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    Button btn_log, btn_swim, btn_bike, btn_run;
    ImageButton ib_preferences, ib_swim_goal, ib_bike_goal, ib_run_goal;
    TextView tv_PB_swim_miles, tv_PB_bike_miles, tv_PB_run_miles,
            tv_PB_swim_HM, tv_PB_bike_HM, tv_PB_run_HM,
            tv_G_swim_miles, tv_G_bike_miles, tv_G_run_miles,
            tv_G_swim_HM, tv_G_bike_HM, tv_G_run_HM;
    DataBaseHelper dataBaseHelper;
    //LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        btn_log = findViewById(R.id.btn_log);
        btn_swim = findViewById(R.id.btn_swim);
        btn_bike = findViewById(R.id.btn_bike);
        btn_run = findViewById(R.id.btn_run);
        ib_preferences = findViewById(R.id.ib_preferences);
        ib_swim_goal = findViewById(R.id.ib_swim_goal);
        ib_bike_goal = findViewById(R.id.ib_bike_goal);
        ib_run_goal = findViewById(R.id.ib_run_goal);
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

        ib_swim_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SwimGoalActivity.class);
                startActivity(i);
            }
        });

        ib_bike_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BikeGoalActivity.class);
                startActivity(i);
            }
        });

        ib_run_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RunGoalActivity.class);
                startActivity(i);
            }
        });

//        double y,x;
//        x = -5.0;
//
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        series = new LineGraphSeries<DataPoint>();
//        for (int i = 0; i<500; i++) {
//            x = x + 0.1;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 500);
//        }
//        graph.addSeries(series);
    }
    @Override
    protected void onResume() {
        super.onResume();
        tv_PB_swim_miles.setText(String.format("%.02f miles", dataBaseHelper.getBestSwimWorkout().getSwim_distance()));
        tv_PB_swim_HM.setText(dataBaseHelper.getBestSwimWorkout().getSwim_time());
        tv_PB_bike_miles.setText(String.format("%.02f miles", dataBaseHelper.getBestBikeWorkout().getBike_distance()));
        tv_PB_bike_HM.setText(dataBaseHelper.getBestBikeWorkout().getBike_time());
        tv_PB_run_miles.setText(String.format("%.02f miles", dataBaseHelper.getBestRunWorkout().getRun_distance()));
        tv_PB_run_HM.setText(dataBaseHelper.getBestRunWorkout().getRun_time());

        tv_G_swim_miles.setText(String.format("%.02f miles", dataBaseHelper.getSwimGoal().getSwimGoal_distance()));
        tv_G_swim_HM.setText(dataBaseHelper.getSwimGoal().getSwimGoal_time());
        tv_G_bike_miles.setText(String.format("%.02f miles", dataBaseHelper.getBikeGoal().getBikeGoal_distance()));
        tv_G_bike_HM.setText(dataBaseHelper.getBikeGoal().getBikeGoal_time());
        tv_G_run_miles.setText(String.format("%.02f miles", dataBaseHelper.getRunGoal().getRunGoal_distance()));
        tv_G_run_HM.setText(dataBaseHelper.getRunGoal().getRunGoal_time());
    }
}