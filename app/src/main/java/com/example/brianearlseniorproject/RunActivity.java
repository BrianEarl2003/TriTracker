package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class RunActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addRun;
    ImageButton ib_runHome;
    EditText et_runDate, et_runTime, et_runDistance;
    ListView lv_runList;
    ArrayAdapter runArrayAdapter;
    DataBaseHelper runDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        btn_addRun = findViewById(R.id.btn_addRun);
        ib_runHome = findViewById(R.id.ib_runHome);
        et_runDate = findViewById(R.id.et_runDate);
        et_runTime = findViewById(R.id.et_runTime);
        et_runDistance = findViewById(R.id.et_runDistance);
        lv_runList = findViewById(R.id.lv_runList);
        runDataBaseHelper = new DataBaseHelper(RunActivity.this);
        ShowRunsOnListView(runDataBaseHelper);

        //button Listeners for the home, add and view all buttons
        ib_runHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RunActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_addRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunModel runModel;
                try {
                    runModel = new RunModel(-1, et_runDate.getText().toString(), et_runTime.getText().toString(), Float.parseFloat(et_runDistance.getText().toString()));
                    Toast.makeText(RunActivity.this, runModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RunActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    runModel = new RunModel(-1, "01-01-2000", "00:00", 0);
                }
                DataBaseHelper runDataBaseHelper = new DataBaseHelper(RunActivity.this);
                boolean success = runDataBaseHelper.addRunWorkout(runModel);
                Toast.makeText(RunActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowRunsOnListView(runDataBaseHelper);
            }
        });

        lv_runList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RunModel clickedRun = (RunModel) parent.getItemAtPosition(position);
                runDataBaseHelper.deleteRun(clickedRun);
                ShowRunsOnListView(runDataBaseHelper);
                Toast.makeText(RunActivity.this, "Deleted" + clickedRun.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ShowRunsOnListView (DataBaseHelper runDataBaseHelper2) {
        runArrayAdapter = new ArrayAdapter<RunModel>(RunActivity.this, android.R.layout.simple_list_item_1, runDataBaseHelper2.getAllRunWorkouts());
        lv_runList.setAdapter(runArrayAdapter);
    }
}