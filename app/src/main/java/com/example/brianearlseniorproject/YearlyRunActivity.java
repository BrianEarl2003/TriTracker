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
import com.github.mikephil.charting.components.YAxis;
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

public class YearlyRunActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;

    private LineChart yearlyRunChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_run);

        dataBaseHelper = new DataBaseHelper(YearlyRunActivity.this);
        yearlyRunChart = (LineChart) findViewById(R.id.yearlyRunChart);
        yearlyRunChart.setDragEnabled(true);
        yearlyRunChart.setScaleEnabled(false);
        ArrayList<Entry> year = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        min.add(Calendar.YEAR, -1);

        List<RunModel> runWorkouts = dataBaseHelper.getAllRunWorkouts();

        for (int i = 0; i < runWorkouts.size(); i++) {
            RunModel run = runWorkouts.get(i);
            String runDate = run.getRun_date();
            try {
                long time = dateFormat.parse(runDate).getTime();
                if (time >= min.getTimeInMillis() && time <= now.getTimeInMillis())
                    year.add(new Entry(time, run.getRun_speed()));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(year, new EntryComparator());

        LineDataSet setRun = new LineDataSet(year, "Yearly Run");

        setRun.setFillAlpha(110);
        setRun.setColor(Color.BLUE);
        setRun.setLineWidth(3f);
        setRun.setValueTextSize(10f);
        setRun.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> runDataSets = new ArrayList<>();
        runDataSets.add(setRun);
        LineData runData = new LineData(runDataSets);

        yearlyRunChart.getXAxis().setAvoidFirstLastClipping(true);
        yearlyRunChart.getXAxis().setValueFormatter(new MyXAxisFormatter());
        yearlyRunChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        yearlyRunChart.getXAxis().setAxisMaximum((float)now.getTimeInMillis());
        yearlyRunChart.getXAxis().setAxisMinimum((float)min.getTimeInMillis());
        yearlyRunChart.getXAxis().setLabelCount(12);

        yearlyRunChart.getAxisRight().setDrawLabels(false);

        yearlyRunChart.setData(runData);
    }

    private static class MyXAxisFormatter extends ValueFormatter {
        //String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        //String[] weeks = {"1", "2", "3", "4"};
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            Date date = new Date((long) value);
            return new SimpleDateFormat("MMM", Locale.getDefault()).format(date);
        }
    }
}