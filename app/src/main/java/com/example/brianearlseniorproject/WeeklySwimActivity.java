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

public class WeeklySwimActivity extends AppCompatActivity {
    DataBaseHelper swimGoalDataBaseHelper;

    private LineChart weeklySwimChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_swim);

        swimGoalDataBaseHelper = new DataBaseHelper(WeeklySwimActivity.this);
        weeklySwimChart = (LineChart) findViewById(R.id.weeklySwimChart);
        weeklySwimChart.setDragEnabled(true);
        weeklySwimChart.setScaleEnabled(false);
        ArrayList<Entry> week = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<SwimModel> swimWorkouts = swimGoalDataBaseHelper.getAllSwimWorkouts();

        for (int i = 0; i < swimWorkouts.size(); i++) {
            SwimModel swim = swimWorkouts.get(i);
            String swimDate = swim.getSwim_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 7;
            try {
                long time = dateFormat.parse(swimDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    week.add(new Entry(day, swim.getSwim_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setSwim = new LineDataSet(week, "Weekly Swim");

        setSwim.setFillAlpha(110);
        setSwim.setColor(Color.BLUE);
        setSwim.setLineWidth(3f);
        setSwim.setValueTextSize(10f);
        setSwim.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> swimDataSets = new ArrayList<>();
        swimDataSets.add(setSwim);
        LineData swimData = new LineData(swimDataSets);

        weeklySwimChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        weeklySwimChart.setData(swimData);
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