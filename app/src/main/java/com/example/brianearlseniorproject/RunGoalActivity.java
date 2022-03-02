package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RunGoalActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addRunGoal;
    ImageButton ib_runHome3;
    EditText et_runTime3, et_runDistance3;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_goal);

        btn_addRunGoal = findViewById(R.id.btn_addRunGoal);
        ib_runHome3 = findViewById(R.id.ib_runHome);
        et_runTime3 = findViewById(R.id.et_runTime);
        et_runDistance3 = findViewById(R.id.et_runDistance);
        dataBaseHelper = new DataBaseHelper(RunGoalActivity.this);

        //button Listeners for the home, add and view all buttons
        ib_runHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RunGoalActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_addRunGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RunGoalModel runGoalModel;
                try {
                    runGoalModel = new RunGoalModel(-1, et_runTime3.getText().toString(), Float.parseFloat(et_runDistance3.getText().toString()));
                    Toast.makeText(RunGoalActivity.this, runGoalModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RunGoalActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    runGoalModel = new RunGoalModel(-1, "01-01-2000", "00:00", 0);
                }
                DataBaseHelper runDataBaseHelper = new DataBaseHelper(RunGoalActivity.this);
                boolean success = runDataBaseHelper.addRunWorkout(runGoalModel);
                Toast.makeText(RunGoalActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
