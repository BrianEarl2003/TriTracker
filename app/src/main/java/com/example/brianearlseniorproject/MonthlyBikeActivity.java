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
import com.github.mikephil.charting.components.XAxis;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthlyBikeActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart monthlyBikeChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_bike);

        dataBaseHelper = new DataBaseHelper(MonthlyBikeActivity.this);
        monthlyBikeChart = (LineChart) findViewById(R.id.monthlyBikeChart);
        monthlyBikeChart.setDragEnabled(true);
        monthlyBikeChart.setScaleEnabled(false);
        ArrayList<Entry> month = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);

        List<BikeModel> bikeWorkouts = dataBaseHelper.getAllBikeWorkouts();

        for (int i = 0; i < bikeWorkouts.size(); i++) {
            BikeModel bike = bikeWorkouts.get(i);
            String bikeDate = bike.getBike_date();
            try {
                long time = dateFormat.parse(bikeDate).getTime();
                if (time >= min.getTimeInMillis() && time <= now.getTimeInMillis())
                    month.add(new Entry(time, bike.getBike_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(month, new EntryComparator());

        LineDataSet setBike = new LineDataSet(month, "Monthly Bike");

        setBike.setFillAlpha(110);
        setBike.setColor(Color.RED);
        setBike.setLineWidth(3f);
        setBike.setValueTextSize(10f);
        setBike.setValueTextColor(Color.RED);

        ArrayList<Entry> weekGoal = new ArrayList<>();
        BikeGoalModel goal = dataBaseHelper.getBikeGoal();
        try {
            String goalTime = goal.getBikeGoal_time();
            String tArr[] = goalTime.split(":");
            float h, mm, ss;
            mm = Float.parseFloat(tArr[0]);
            ss = Float.parseFloat(tArr[1]);
            h = ((ss / 60) + mm)/60;
            float goalDistance = goal.getBikeGoal_distance();
            float goalSpeed = goalDistance / h;
            for (int i = 0; i < 31; i++) {
                Calendar now2 = Calendar.getInstance();
                now2.add(Calendar.DAY_OF_MONTH, -i);
                long now3 = now2.getTimeInMillis();
                weekGoal.add(new Entry((float) now3, goalSpeed));
            }
        } catch (Exception e) {e.printStackTrace();}
        Collections.sort(weekGoal, new EntryComparator());
        LineDataSet setBikeGoal = new LineDataSet(weekGoal, "Bike Goal");
        setBikeGoal.setFillAlpha(110);
        setBikeGoal.setColor(Color.BLACK);
        setBikeGoal.setLineWidth(3f);
        setBikeGoal.setValueTextSize(10f);
        setBikeGoal.setValueTextColor(android.R.color.transparent);

        ArrayList<ILineDataSet> bikeDataSets = new ArrayList<>();
        bikeDataSets.add(setBikeGoal);
        bikeDataSets.add(setBike);
        LineData bikeData = new LineData(bikeDataSets);

        monthlyBikeChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        monthlyBikeChart.getXAxis().setAvoidFirstLastClipping(true);
        monthlyBikeChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        monthlyBikeChart.getXAxis().setAxisMaximum((float)now.getTimeInMillis());
        monthlyBikeChart.getXAxis().setAxisMinimum((float)min.getTimeInMillis());
        monthlyBikeChart.getXAxis().setLabelCount(5);

        monthlyBikeChart.getAxisRight().setDrawLabels(false);

        monthlyBikeChart.setData(bikeData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Date date = new Date((long) value);
            return new SimpleDateFormat("W", Locale.getDefault()).format(date);
        }
    }
}
