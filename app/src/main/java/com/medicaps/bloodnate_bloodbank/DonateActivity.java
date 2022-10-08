package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DonateActivity extends AppCompatActivity {
    BloodDatabaseHelper bloodDatabaseHelper = new BloodDatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText address = findViewById(R.id.address);
        Spinner donorBloodGroup = findViewById(R.id.donor_blood_group);
        // spinner initialized : adding values to the drop down menu
        String[] items = new String[]{"A+","B+","O+","AB+","A-","B-","O-","AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        donorBloodGroup.setAdapter(adapter);

        Button submitDonate = findViewById(R.id.submit_donate);
        // set button click
        submitDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length()==0||email.getText().toString().length()==0||address.getText().toString().length()==0)
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
                else if (name.getText().toString().length()<3||email.getText().toString().length()<3||address.getText().toString().length()<3)
                    Toast.makeText(getApplicationContext(), "Please enter valid details", Toast.LENGTH_LONG).show();
                else {
                    Request request = new Request(name.getText().toString(),email.getText().toString(),address.getText().toString(),null,0,BloodGroup.BG(donorBloodGroup.getSelectedItem().toString()));
                    RequestDatabaseHelper databaseHelper = new RequestDatabaseHelper(getApplicationContext());
                    boolean success = databaseHelper.addRequest(request);
                    System.out.println(success);

                    //For testing
                    System.out.println(request.getName());
                    System.out.println(request.email);
                    System.out.println(request.getAddress());
                    System.out.println(request.getRequestBloodGroup());
                    System.out.println(request.getDonorBloodGroup());
                    System.out.println(request.token);
                    System.out.println(request.volume);
                    System.out.println(request.requestType);
                    // put some extras to track the request status
                    Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                    intent.putExtra("type",request.requestType);
                    intent.putExtra("status",success);
                    intent.putExtra("name",request.name);
                    intent.putExtra("address",request.address);
                    intent.putExtra("token",request.token);
                    intent.putExtra("bloodgroup",request.DonorBloodGroup);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}