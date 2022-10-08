package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class LookDatabase extends AppCompatActivity {
    BloodDatabaseHelper bloodDatabaseHelper = new BloodDatabaseHelper(this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_database);
        //a list view to view all the record of database BGDatabase
        ListView viewer = findViewById(R.id.viewer);
        String[] items = new String[]{"A+","B+","O+","AB+","A-","B-","O-","AB-"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,items);
        //set adapter to viewer
        viewer.setAdapter(adapter);
        Intent intent = new Intent(getApplicationContext(), Data.class);
        viewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("clicked",items[position]);
                System.out.println("clicked "+items[position]);
                startActivity(intent);
            }
        });
    }
}