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

public class YearlySwimActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart yearlySwimChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_swim);

        dataBaseHelper = new DataBaseHelper(YearlySwimActivity.this);
        yearlySwimChart = (LineChart) findViewById(R.id.yearlySwimChart);
        yearlySwimChart.setDragEnabled(true);
        yearlySwimChart.setScaleEnabled(false);
        ArrayList<Entry> year = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<SwimModel> swimWorkouts = dataBaseHelper.getAllSwimWorkouts();

        for (int i = 0; i < swimWorkouts.size(); i++) {
            SwimModel swim = swimWorkouts.get(i);
            String swimDate = swim.getSwim_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 360;
            try {
                long time = dateFormat.parse(swimDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    year.add(new Entry(day, swim.getSwim_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setSwim = new LineDataSet(year, "Yearly Swim");

        setSwim.setFillAlpha(110);
        setSwim.setColor(Color.BLUE);
        setSwim.setLineWidth(3f);
        setSwim.setValueTextSize(10f);
        setSwim.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> swimDataSets = new ArrayList<>();
        swimDataSets.add(setSwim);
        LineData swimData = new LineData(swimDataSets);

        yearlySwimChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        yearlySwimChart.setData(swimData);
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
