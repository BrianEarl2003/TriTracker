package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Map;

public class RunGoalActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addRunGoal;
    ImageButton ib_runHome3;
    EditText et_runTime3, et_runDistance3;
    DataBaseHelper runGoalDataBaseHelper;

    private LineChart runChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_goal);

        btn_addRunGoal = findViewById(R.id.btn_addRunGoal);
        ib_runHome3 = findViewById(R.id.ib_runHome3);
        et_runTime3 = findViewById(R.id.et_runTime3);
        et_runDistance3 = findViewById(R.id.et_runDistance3);
        runGoalDataBaseHelper = new DataBaseHelper(RunGoalActivity.this);
        runChart = (LineChart) findViewById(R.id.runChart);
        runChart.setDragEnabled(true);
        runChart.setScaleEnabled(false);

        ArrayList<Entry> weeklyRun = new ArrayList<>();

//        for (int i=0; i < runGoalDataBaseHelper.getRunWeeklyCount(); i++) {
//            weeklyRun.add(new Entry(runGoalDataBaseHelper.getAllRunWorkouts().get(i).getRun_distance(), runGoalDataBaseHelper.getAllRunWorkouts().get(i).getRun_speed()));
//        }

        for (int i=0; i < 20; i++) {
            weeklyRun.add(new Entry(i, (float)Math.sin(i)));
        }

        LineDataSet set1 = new LineDataSet(weeklyRun, "Weekly Run");

        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        runChart.setData(data);

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
