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
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        min.add(Calendar.MONTH, -1);

        List<SwimModel> swimWorkouts = dataBaseHelper.getAllSwimWorkouts();

        for (int i = 0; i < swimWorkouts.size(); i++) {
            SwimModel swim = swimWorkouts.get(i);
            String swimDate = swim.getSwim_date();
            try {
                long time = dateFormat.parse(swimDate).getTime();
                if (time >= min.getTimeInMillis() && time <= now.getTimeInMillis())
                    month.add(new Entry(time, swim.getSwim_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(month, new EntryComparator());

        LineDataSet setSwim = new LineDataSet(month, "Monthly Swim");

        setSwim.setFillAlpha(110);
        setSwim.setColor(Color.BLUE);
        setSwim.setLineWidth(3f);
        setSwim.setValueTextSize(10f);
        setSwim.setValueTextColor(Color.BLUE);

        ArrayList<Entry> weekGoal = new ArrayList<>();
        SwimGoalModel goal = dataBaseHelper.getSwimGoal();
        try {
            String goalTime = goal.getSwimGoal_time();
            String tArr[] = goalTime.split(":");
            float h, mm, ss;
            mm = Float.parseFloat(tArr[0]);
            ss = Float.parseFloat(tArr[1]);
            h = ((ss / 60) + mm)/60;
            float goalDistance = goal.getSwimGoal_distance();
            float goalSpeed = goalDistance / h;
            for (int i = 0; i < 31; i++) {
                Calendar now2 = Calendar.getInstance();
                now2.add(Calendar.DAY_OF_MONTH, -i);
                long now3 = now2.getTimeInMillis();
                weekGoal.add(new Entry((float) now3, goalSpeed));
            }
        } catch (Exception e) {e.printStackTrace();}
        Collections.sort(weekGoal, new EntryComparator());
        LineDataSet setSwimGoal = new LineDataSet(weekGoal, "Swim Goal");
        setSwimGoal.setFillAlpha(110);
        setSwimGoal.setColor(Color.BLACK);
        setSwimGoal.setLineWidth(3f);
        setSwimGoal.setValueTextSize(10f);
        setSwimGoal.setValueTextColor(android.R.color.transparent);

        ArrayList<ILineDataSet> swimDataSets = new ArrayList<>();
        swimDataSets.add(setSwimGoal);
        swimDataSets.add(setSwim);
        LineData swimData = new LineData(swimDataSets);

        monthlySwimChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        monthlySwimChart.getXAxis().setAvoidFirstLastClipping(true);
        monthlySwimChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        monthlySwimChart.getXAxis().setAxisMaximum((float)now.getTimeInMillis());
        monthlySwimChart.getXAxis().setAxisMinimum((float)min.getTimeInMillis());
        monthlySwimChart.getXAxis().setLabelCount(5);

        monthlySwimChart.getAxisRight().setDrawLabels(false);

        monthlySwimChart.setData(swimData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Date date = new Date((long) value);
            return new SimpleDateFormat("W", Locale.getDefault()).format(date);
        }
    }
}