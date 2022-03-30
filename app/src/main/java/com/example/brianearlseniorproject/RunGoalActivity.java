package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RunGoalActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addRunGoal;
    ImageButton ib_runHome3;
    EditText et_runTime3, et_runDistance3;
//    DataBaseHelper runGoalDataBaseHelper;

//    private LineChart weeklyChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_goal);

        btn_addRunGoal = findViewById(R.id.btn_addRunGoal);
        ib_runHome3 = findViewById(R.id.ib_runHome3);
        et_runTime3 = findViewById(R.id.et_runTime3);
        et_runDistance3 = findViewById(R.id.et_runDistance3);
//        runGoalDataBaseHelper = new DataBaseHelper(RunGoalActivity.this);
//        weeklyChart = (LineChart) findViewById(R.id.runChart);
//        weeklyChart.setDragEnabled(true);
//        weeklyChart.setScaleEnabled(false);
//        ArrayList<Entry> week = new ArrayList<>();
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

//        List<RunModel> runWorkouts = runGoalDataBaseHelper.getAllRunWorkouts();
//
//        for (int i = 0; i < runWorkouts.size(); i++) {
//            RunModel run = runWorkouts.get(i);
//            String runDate = run.getRun_date();
//            try {
//                long time = dateFormat.parse(runDate).getTime();
//                //float day = (float) (time / 86400000);
//                //System.out.println("day: " + day);
//                week.add(new Entry(time, run.getRun_speed()));
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        LineDataSet setRun = new LineDataSet(week, "Weekly Run");
//
//        setRun.setFillAlpha(110);
//        setRun.setColor(Color.GREEN);
//        setRun.setLineWidth(3f);
//        setRun.setValueTextSize(10f);
//        setRun.setValueTextColor(Color.BLUE);
//
//        ArrayList<ILineDataSet> runDataSets = new ArrayList<>();
//        runDataSets.add(setRun);
//        LineData runData = new LineData(runDataSets);
//        weeklyChart.setData(runData);

//        List<BikeModel> bikeWorkouts = runGoalDataBaseHelper.getAllBikeWorkouts();
//
//        for (int i = 0; i < bikeWorkouts.size(); i++) {
//            BikeModel bike = bikeWorkouts.get(i);
//            String bikeDate = bike.getBike_date();
//            try {
//                long time = dateFormat.parse(bikeDate).getTime();
//                week.add(new Entry(time, bike.getBike_speed()));
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        LineDataSet setBike = new LineDataSet(week, "Weekly Bike");
//
//        setBike.setFillAlpha(110);
//        setBike.setColor(Color.RED);
//        setBike.setLineWidth(3f);
//        setBike.setValueTextSize(10f);
//        setBike.setValueTextColor(Color.GREEN);
//
//        ArrayList<ILineDataSet> bikeDataSets = new ArrayList<>();
//        bikeDataSets.add(setBike);
//        LineData bikeData = new LineData(bikeDataSets);
//        weeklyChart.setData(bikeData);

//        List<SwimModel> swimWorkouts = runGoalDataBaseHelper.getAllSwimWorkouts();
//
//        for (int i = 0; i < swimWorkouts.size(); i++) {
//            SwimModel swim = swimWorkouts.get(i);
//            String swimDate = swim.getSwim_date();
//            try {
//                long time = dateFormat.parse(swimDate).getTime();
//                week.add(new Entry(time, swim.getSwim_speed()));
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        LineDataSet setSwim = new LineDataSet(week, "Weekly Swim");
//
//        setSwim.setFillAlpha(110);
//        setSwim.setColor(Color.BLUE);
//        setSwim.setLineWidth(3f);
//        setSwim.setValueTextSize(10f);
//        setSwim.setValueTextColor(Color.RED);
//
//        ArrayList<ILineDataSet> swimDataSets = new ArrayList<>();
//        swimDataSets.add(setSwim);
//        LineData swimData = new LineData(swimDataSets);
//        weeklyChart.setData(swimData);

//        final ArrayList<String> xLabel = new ArrayList<>();
//        xLabel.add("9");
//        xLabel.add("15");
//        xLabel.add("21");
//        xLabel.add("27");
//        xLabel.add("33");
//
//        XAxis xAxis = runChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return xLabel.get((int)value);
//            }
//        });

        //button Listeners for the home, add and view all buttons
        ib_runHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        btn_addRunGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunGoalModel runGoalModel;// = new RunGoalModel(-1, "", 0.0F);
                try {
                    runGoalModel = new RunGoalModel(-1, et_runTime3.getText().toString(), Float.parseFloat(et_runDistance3.getText().toString()));
                    Toast.makeText(RunGoalActivity.this, runGoalModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RunGoalActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    runGoalModel = new RunGoalModel(-1, "00:00", 0);
                }
                DataBaseHelper runGoalDataBaseHelper = new DataBaseHelper(RunGoalActivity.this);
                boolean success = runGoalDataBaseHelper.addRunGoal(runGoalModel);
                Toast.makeText(RunGoalActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
