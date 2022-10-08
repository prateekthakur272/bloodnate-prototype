package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PendingRequests extends AppCompatActivity {
    ListView requestsList;
    List<Request> requestList;
    List<String> adapterString;
    ArrayAdapter requestArrayAdapter;
    RequestDatabaseHelper requestDatabaseHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        requestsList = findViewById(R.id.requests_view);
        requestDatabaseHelper = new RequestDatabaseHelper(this);
        requestList = requestDatabaseHelper.getRequestList();
        adapterString = new ArrayList<>();
        for(Request request:requestList){
            adapterString.add(request.toString());
        }
        // if adapterString is empty that means no requests pending
        if (adapterString.isEmpty())
            Toast.makeText(this,"No requests pending",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,adapterString.size()+" requests pending",Toast.LENGTH_SHORT).show();
        // declaring, initializing and setting array adapter for ListView requestList
        requestArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, adapterString);
        requestsList.setAdapter(requestArrayAdapter);
        for(Request request:requestList){
            System.out.println(request.toString());
        }
        // set what to do when an item of ListView is selected
        requestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String token = requestList.get(position).token;
                Request clickedRequest = requestList.get(position);
                clickedRequest.display();
                Intent intent = new Intent(getApplicationContext(),RequestOptions.class);
                intent.putExtra("requestPosition",position);
                startActivity(intent);
                finish();
            }
        });
    }
}