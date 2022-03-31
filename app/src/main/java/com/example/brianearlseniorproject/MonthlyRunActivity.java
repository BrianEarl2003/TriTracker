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

public class MonthlyRunActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart monthlyRunChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_run);

        dataBaseHelper = new DataBaseHelper(MonthlyRunActivity.this);
        monthlyRunChart = (LineChart) findViewById(R.id.monthlyRunChart);
        monthlyRunChart.setDragEnabled(true);
        monthlyRunChart.setScaleEnabled(false);
        ArrayList<Entry> month = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        List<RunModel> runWorkouts = dataBaseHelper.getAllRunWorkouts();

        for (int i = 0; i < runWorkouts.size(); i++) {
            RunModel run = runWorkouts.get(i);
            String runDate = run.getRun_date();
            long millisDate = System.currentTimeMillis();
            float now = (float) millisDate / 86400000;
            float min = now - 30;
            try {
                long time = dateFormat.parse(runDate).getTime();
                float day = (float) (time / 86400000);
                if (day >= min && day <= now)
                    month.add(new Entry(day, run.getRun_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        LineDataSet setRun = new LineDataSet(month, "Monthly Run");

        setRun.setFillAlpha(110);
        setRun.setColor(Color.GREEN);
        setRun.setLineWidth(3f);
        setRun.setValueTextSize(10f);
        setRun.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> runDataSets = new ArrayList<>();
        runDataSets.add(setRun);
        LineData runData = new LineData(runDataSets);

        monthlyRunChart.getXAxis().setValueFormatter(new MyXAxisFormatter());

        monthlyRunChart.setData(runData);
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
