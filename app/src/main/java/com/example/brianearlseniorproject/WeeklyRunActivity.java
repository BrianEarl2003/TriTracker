package com.example.brianearlseniorproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyRunActivity extends AppCompatActivity {
    DataBaseHelper runGoalDataBaseHelper;

    private LineChart weeklyRunChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_run);

        runGoalDataBaseHelper = new DataBaseHelper(WeeklyRunActivity.this);
        weeklyRunChart = (LineChart) findViewById(R.id.weeklyRunChart);
        weeklyRunChart.setDragEnabled(true);
        weeklyRunChart.setScaleEnabled(false);
        ArrayList<Entry> week = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<RunModel> runWorkouts = runGoalDataBaseHelper.getAllRunWorkouts();

        for (int i = 0; i < runWorkouts.size(); i++) {
            RunModel run = runWorkouts.get(i);
            String runDate = run.getRun_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 7;
            try {
                long time = dateFormat.parse(runDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    week.add(new Entry(day, run.getRun_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setRun = new LineDataSet(week, "Weekly Run");

        setRun.setFillAlpha(110);
        setRun.setColor(Color.GREEN);
        setRun.setLineWidth(3f);
        setRun.setValueTextSize(10f);
        setRun.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> runDataSets = new ArrayList<>();
        runDataSets.add(setRun);
        LineData runData = new LineData(runDataSets);

        weeklyRunChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        weeklyRunChart.setData(runData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        String[] days = {"Su", "Mo", "Tu", "Wed", "Th", "Fr", "Sa"};
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((long) (value * 86400000)));
            return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        }
    }
}