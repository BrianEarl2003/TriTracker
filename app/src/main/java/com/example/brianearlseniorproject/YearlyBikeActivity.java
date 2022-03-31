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

public class YearlyBikeActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart yearlyBikeChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_bike);

        dataBaseHelper = new DataBaseHelper(YearlyBikeActivity.this);
        yearlyBikeChart = (LineChart) findViewById(R.id.yearlyBikeChart);
        yearlyBikeChart.setDragEnabled(true);
        yearlyBikeChart.setScaleEnabled(false);
        ArrayList<Entry> year = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<BikeModel> bikeWorkouts = dataBaseHelper.getAllBikeWorkouts();

        for (int i = 0; i < bikeWorkouts.size(); i++) {
            BikeModel bike = bikeWorkouts.get(i);
            String bikeDate = bike.getBike_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 360;
            try {
                long time = dateFormat.parse(bikeDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    year.add(new Entry(day, bike.getBike_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setBike = new LineDataSet(year, "Yearly Bike");

        setBike.setFillAlpha(110);
        setBike.setColor(Color.RED);
        setBike.setLineWidth(3f);
        setBike.setValueTextSize(10f);
        setBike.setValueTextColor(Color.RED);

        ArrayList<ILineDataSet> bikeDataSets = new ArrayList<>();
        bikeDataSets.add(setBike);
        LineData bikeData = new LineData(bikeDataSets);

        yearlyBikeChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        yearlyBikeChart.setData(bikeData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        //String[] weeks = {"1", "2", "3", "4"};
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((long) (value * 86400000)));
            return months[calendar.get(Calendar.MONTH) - 1];
        }
    }
}