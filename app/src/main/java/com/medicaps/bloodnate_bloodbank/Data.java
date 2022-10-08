package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Data extends AppCompatActivity {
    Button done;
    EditText newValue;
    TextView data;
    BloodDatabaseHelper bloodDatabaseHelper = new BloodDatabaseHelper(this);
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_database);
        Bundle extras = getIntent().getExtras();
        String click = (String) extras.get("clicked");
        data = findViewById(R.id.data);
        data.setText("BLOOD:           "+click+
                "\nAVAILABLE:    "+bloodDatabaseHelper.getAvail(click)+"ml"+
                "\nUNCLEARED:  "+bloodDatabaseHelper.getUnclear(click)+"ml");
        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}