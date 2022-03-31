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

public class WeeklyBikeActivity extends AppCompatActivity {
    DataBaseHelper bikeGoalDataBaseHelper;

    private LineChart weeklyBikeChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_bike);

        bikeGoalDataBaseHelper = new DataBaseHelper(WeeklyBikeActivity.this);
        weeklyBikeChart = (LineChart) findViewById(R.id.weeklyBikeChart);
        weeklyBikeChart.setDragEnabled(true);
        weeklyBikeChart.setScaleEnabled(false);
        ArrayList<Entry> week = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<BikeModel> bikeWorkouts = bikeGoalDataBaseHelper.getAllBikeWorkouts();

        for (int i = 0; i < bikeWorkouts.size(); i++) {
            BikeModel bike = bikeWorkouts.get(i);
            String bikeDate = bike.getBike_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 7;
            try {
                long time = dateFormat.parse(bikeDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    week.add(new Entry(day, bike.getBike_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setBike = new LineDataSet(week, "Weekly Bike");

        setBike.setFillAlpha(110);
        setBike.setColor(Color.RED);
        setBike.setLineWidth(3f);
        setBike.setValueTextSize(10f);
        setBike.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> bikeDataSets = new ArrayList<>();
        bikeDataSets.add(setBike);
        LineData bikeData = new LineData(bikeDataSets);

        weeklyBikeChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        weeklyBikeChart.setData(bikeData);
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