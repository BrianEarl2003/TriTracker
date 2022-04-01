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
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        min.add(Calendar.DAY_OF_WEEK, -7);

        List<RunModel> runWorkouts = runGoalDataBaseHelper.getAllRunWorkouts();

        for (int i = 0; i < runWorkouts.size(); i++) {
            RunModel run = runWorkouts.get(i);
            String runDate = run.getRun_date();
            try {
                long time = dateFormat.parse(runDate).getTime();
                if (time >= min.getTimeInMillis() && time <= now.getTimeInMillis())
                    week.add(new Entry(time, run.getRun_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(week, new EntryComparator());

        LineDataSet setRun = new LineDataSet(week, "Weekly Run");

        setRun.setFillAlpha(110);
        setRun.setColor(Color.GREEN);
        setRun.setLineWidth(3f);
        setRun.setValueTextSize(10f);
        setRun.setValueTextColor(Color.GREEN);

        ArrayList<Entry> weekGoal = new ArrayList<>();
        RunGoalModel goal = runGoalDataBaseHelper.getRunGoal();
        try {
            String goalTime = goal.getRunGoal_time();
            String tArr[] = goalTime.split(":");
            float h, mm, ss;
            mm = Float.parseFloat(tArr[0]);
            ss = Float.parseFloat(tArr[1]);
            h = ((ss / 60) + mm)/60;
            float goalDistance = goal.getRunGoal_distance();
            float goalSpeed = goalDistance / h;
            for (int i = 0; i < 8; i++) {
                Calendar now2 = Calendar.getInstance();
                now2.add(Calendar.DAY_OF_WEEK, -i);
                long now3 = now2.getTimeInMillis();
                weekGoal.add(new Entry((float) now3, goalSpeed));
            }
        } catch (Exception e) {e.printStackTrace();}
        Collections.sort(weekGoal, new EntryComparator());
        LineDataSet setRunGoal = new LineDataSet(weekGoal, "Run Goal");
        setRunGoal.setFillAlpha(110);
        setRunGoal.setColor(Color.BLACK);
        setRunGoal.setLineWidth(3f);
        setRunGoal.setValueTextSize(10f);
        setRunGoal.setValueTextColor(android.R.color.transparent);

        ArrayList<ILineDataSet> runDataSets = new ArrayList<>();
        runDataSets.add(setRunGoal);
        runDataSets.add(setRun);
        LineData runData = new LineData(runDataSets);

        weeklyRunChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        weeklyRunChart.getXAxis().setAvoidFirstLastClipping(true);
        weeklyRunChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        weeklyRunChart.getXAxis().setAxisMaximum((float)now.getTimeInMillis());
        weeklyRunChart.getXAxis().setAxisMinimum((float)min.getTimeInMillis());
        weeklyRunChart.getXAxis().setLabelCount(7);

        weeklyRunChart.getAxisRight().setDrawLabels(false);

        weeklyRunChart.setData(runData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Date date = new Date((long) value);
            return new SimpleDateFormat("EEE", Locale.getDefault()).format(date);
        }
    }
}