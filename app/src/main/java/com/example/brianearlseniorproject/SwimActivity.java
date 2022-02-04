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

public class SwimActivity extends AppCompatActivity {
    //references to buttons and other controls on the layout
    Button btn_addSwim;
    ImageButton ib_swimHome;
    EditText et_swimDate, et_swimTime, et_swimLaps, et_lapDistance, et_swimDistance;
    ListView lv_swimList;
    ArrayAdapter swimArrayAdapter;
    DataBaseHelper swimDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swim);

        btn_addSwim = findViewById(R.id.btn_addSwim);
        ib_swimHome = findViewById(R.id.ib_swimHome);
        et_swimDate = findViewById(R.id.et_swimDate);
        et_swimTime = findViewById(R.id.et_swimTime);
        et_swimLaps = findViewById(R.id.et_swimLaps);
        et_lapDistance = findViewById(R.id.et_lapDistance);
        et_swimDistance = findViewById(R.id.et_swimDistance);
        lv_swimList = findViewById(R.id.lv_runList);
        swimDataBaseHelper = new DataBaseHelper(SwimActivity.this);
        ShowSwimsOnListView(swimDataBaseHelper);

        //button Listeners for the home, add and view all buttons
        ib_swimHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SwimActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_addSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwimModel swimModel;
                try {
                    swimModel = new SwimModel(-1, et_swimDate.getText().toString(), et_swimTime.getText().toString(), Float.parseFloat(et_swimLaps.getText().toString()), Float.parseFloat(et_lapDistance.getText().toString()), Float.parseFloat(et_swimDistance.getText().toString()));
                    Toast.makeText(SwimActivity.this, swimModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(SwimActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    swimModel = new SwimModel(-1, "01-01-2000", "00:00", 0, 0, 0);
                }
                DataBaseHelper swimDataBaseHelper = new DataBaseHelper(SwimActivity.this);
                boolean success = swimDataBaseHelper.addSwimWorkout(swimModel);
                Toast.makeText(SwimActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowSwimsOnListView(swimDataBaseHelper);
            }
        });

        lv_swimList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SwimModel clickedSwim = (SwimModel) parent.getItemAtPosition(position);
                swimDataBaseHelper.deleteSwim(clickedSwim);
                ShowSwimsOnListView(swimDataBaseHelper);
                Toast.makeText(SwimActivity.this, "Deleted" + clickedSwim.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowSwimsOnListView (DataBaseHelper swimDataBaseHelper2) {
        swimArrayAdapter = new ArrayAdapter<SwimModel>(SwimActivity.this, android.R.layout.simple_list_item_1, swimDataBaseHelper2.getAllSwimWorkouts());
        lv_swimList.setAdapter(swimArrayAdapter);
    }
}