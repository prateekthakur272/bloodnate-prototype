package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_section);
        // there are two buttons only that opens other two activities
        Button lookDatabase = findViewById(R.id.database);
        Button requests = findViewById(R.id.requests);
        // set on click to buttons
        lookDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLookDatabase();
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPendingRequests();
            }
        });

    }
    // calling next activities by using function
    void gotoLookDatabase(){
        startActivity(new Intent(this,LookDatabase.class));
    }
    void gotoPendingRequests(){
        startActivity(new Intent(this,PendingRequests.class));
    }
}