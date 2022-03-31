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

public class MonthlySwimActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart monthlySwimChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_swim);

        dataBaseHelper = new DataBaseHelper(MonthlySwimActivity.this);
        monthlySwimChart = (LineChart) findViewById(R.id.monthlySwimChart);
        monthlySwimChart.setDragEnabled(true);
        monthlySwimChart.setScaleEnabled(false);
        ArrayList<Entry> month = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<SwimModel> swimWorkouts = dataBaseHelper.getAllSwimWorkouts();

        for (int i = 0; i < swimWorkouts.size(); i++) {
            SwimModel swim = swimWorkouts.get(i);
            String swimDate = swim.getSwim_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 30;
            try {
                long time = dateFormat.parse(swimDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    month.add(new Entry(day, swim.getSwim_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setSwim = new LineDataSet(month, "Monthly Swim");

        setSwim.setFillAlpha(110);
        setSwim.setColor(Color.BLUE);
        setSwim.setLineWidth(3f);
        setSwim.setValueTextSize(10f);
        setSwim.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> swimDataSets = new ArrayList<>();
        swimDataSets.add(setSwim);
        LineData swimData = new LineData(swimDataSets);

        monthlySwimChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        monthlySwimChart.setData(swimData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        //String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] weeks = {"1", "2", "3", "4"};
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((long) (value * 86400000)));
            return weeks[calendar.get(Calendar.WEEK_OF_MONTH) - 1];
        }
    }
}