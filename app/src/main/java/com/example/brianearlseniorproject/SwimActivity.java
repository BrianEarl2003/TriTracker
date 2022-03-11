package com.example.brianearlseniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    ArrayAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swim);

        btn_addSwim = findViewById(R.id.btn_addSwim);
        ib_swimHome = findViewById(R.id.ib_swimHome);
        et_swimDate = findViewById(R.id.et_swimDate);
        et_swimTime = findViewById(R.id.et_swimTime);
        et_swimLaps = findViewById(R.id.et_swimLaps);
        //et_swimLaps.setText(0);
        et_lapDistance = findViewById(R.id.et_lapDistance);
        //et_lapDistance.setText(0);
        et_swimDistance = findViewById(R.id.et_swimDistance);
        lv_swimList = findViewById(R.id.lv_runList3);
        dataBaseHelper = new DataBaseHelper(SwimActivity.this);
        ShowSwimsOnListView(dataBaseHelper);

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
                    String time = et_swimTime.getText().toString();
                    String tArr[] = time.split(":");
                    float h, mm, ss;
                    mm = Float.parseFloat(tArr[0]);
                    ss = Float.parseFloat(tArr[1]);
                    h = ((ss / 60) + mm)/60;
                    float distance = Float.parseFloat(et_swimDistance.getText().toString());
                    float speed = distance / h;
                    swimModel = new SwimModel(-1, et_swimDate.getText().toString(), et_swimTime.getText().toString(), Float.parseFloat(et_swimLaps.getText().toString()),
                            Float.parseFloat(et_lapDistance.getText().toString()), distance, speed);
                    Toast.makeText(SwimActivity.this, swimModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(SwimActivity.this, "Error adding workout", Toast.LENGTH_SHORT).show();
                    swimModel = new SwimModel(-1, "01-01-2000", "00:00", 0.0F, 0.0F, 0.0F, 0.0F);
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SwimActivity.this);
                boolean success = dataBaseHelper.addSwimWorkout(swimModel);
                Toast.makeText(SwimActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowSwimsOnListView(dataBaseHelper);
            }
        });

        lv_swimList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SwimModel clickedSwim = (SwimModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteSwim(clickedSwim);
                ShowSwimsOnListView(dataBaseHelper);
                Toast.makeText(SwimActivity.this, "Deleted" + clickedSwim.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!et_swimLaps.getText().toString().isEmpty() && !et_lapDistance.getText().toString().isEmpty())
                    et_swimDistance.setText(Float.toString((Float.parseFloat(et_swimLaps.getText().toString()) * Float.parseFloat(et_lapDistance.getText().toString())) / 1760));
                else
                    et_swimDistance.setText("");
            }
        };

        et_swimLaps.addTextChangedListener(textWatcher);

        et_lapDistance.addTextChangedListener(textWatcher);
    }

    private void ShowSwimsOnListView (DataBaseHelper dataBaseHelper2) {
        arrayAdapter = new ArrayAdapter<SwimModel>(SwimActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAllSwimWorkouts());
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//            // Get the Item from ListView
//            View view = super.getView(position, convertView, parent);
//
//            // Initialize a TextView for ListView each Item
//            TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//            // Set the text color of TextView (ListView Item)
//            tv.setTextColor(Color.BLACK);
//
//            // Generate ListView Item using TextView
//            return view;
//        }
//    };
        lv_swimList.setAdapter(arrayAdapter);
    }
}