package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SwimGoalActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addSwimGoal;
    ImageButton ib_swimHome3;
    EditText et_swimTime3, et_swimDistance3;
    DataBaseHelper swimGoalDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swim_goal);

        btn_addSwimGoal = findViewById(R.id.btn_addSwimGoal);
        ib_swimHome3 = findViewById(R.id.ib_swimHome3);
        et_swimTime3 = findViewById(R.id.et_swimTime3);
        et_swimDistance3 = findViewById(R.id.et_swimDistance3);
        swimGoalDataBaseHelper = new DataBaseHelper(SwimGoalActivity.this);

        //button Listeners for the home, add and view all buttons
        ib_swimHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_addSwimGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwimGoalModel swimGoalModel;// = new SwimGoalModel(-1, "", 0.0F);
                try {
                    swimGoalModel = new SwimGoalModel(-1, et_swimTime3.getText().toString(), Float.parseFloat(et_swimDistance3.getText().toString()));
                    Toast.makeText(SwimGoalActivity.this, swimGoalModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(SwimGoalActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    swimGoalModel = new SwimGoalModel(-1, "00:00", 0);
                }
                DataBaseHelper swimGoalDataBaseHelper = new DataBaseHelper(SwimGoalActivity.this);
                boolean success = swimGoalDataBaseHelper.addSwimGoal(swimGoalModel);
                Toast.makeText(SwimGoalActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
