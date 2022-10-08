package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RequestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText address = findViewById(R.id.address);
        EditText volume = findViewById(R.id.volume);
        // A spinner provide us facility to select one item out of available options
        Spinner bloodGroup = findViewById(R.id.blood_group);
        String[] items = new String[]{"A+","B+","O+","AB+","A-","B-","O-","AB-"};
        // ArrayAdapter is an array of Views/widgets in android sdk
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        bloodGroup.setAdapter(adapter);

        Spinner donorBloodGroup = findViewById(R.id.donor_blood_group);
        ArrayAdapter<String> donorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        donorBloodGroup.setAdapter(donorAdapter);
        // A checkbox let us to check it for some condition and returns true if checked, false otherwise
        CheckBox willDonate = findViewById(R.id.will_donate);
        willDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (willDonate.isChecked())
                    donorBloodGroup.setVisibility(View.VISIBLE);
                else
                    donorBloodGroup.setVisibility(View.INVISIBLE);
            }
        });

        Button submitRequest = findViewById(R.id.submit_request);
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking whether the fields are non-empty or not to short to be accepted
                if (name.getText().toString().length()==0||email.getText().toString().length()==0||address.getText().toString().length()==0||volume.getText().toString().length()==0)
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_LONG).show();
                else if (name.getText().toString().length()<3||email.getText().toString().length()<3||address.getText().toString().length()<3||Integer.parseInt(volume.getText().toString())<=0)
                    Toast.makeText(getApplicationContext(), "Please enter valid details", Toast.LENGTH_LONG).show();
                else {
                    // Creating an instance of Request as per the details entered
                    Request request = new Request(name.getText().toString(),email.getText().toString(),address.getText().toString(),BloodGroup.BG(bloodGroup.getSelectedItem().toString()),Integer.parseInt(volume.getText().toString()),willDonate.isChecked()?BloodGroup.BG(donorBloodGroup.getSelectedItem().toString()):null);
                    RequestDatabaseHelper databaseHelper = new RequestDatabaseHelper(getApplicationContext());
                    BloodDatabaseHelper bloodDatabaseHelper = new BloodDatabaseHelper(getApplicationContext());
                    boolean success = databaseHelper.addRequest(request);
                    // updating uncleared value in database as per requested quantity
                    if(success){
                        bloodDatabaseHelper.updateValueUncleared(BloodGroup.BG(request.RequestBloodGroup),bloodDatabaseHelper.getUnclear(BloodGroup.BG(request.RequestBloodGroup))-request.volume);
                        System.out.println("Uncleared updated");
                    }
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
                    // storing some data to bundle of next activity so that the data can be accessed in the next activity
                    // intent in android is reference to an activity
                    Intent intent = new Intent(getApplicationContext(),StatusActivity.class);
                    // extras can be accessed in the next activity bye using a key
                    intent.putExtra("type",request.requestType);
                    intent.putExtra("status",success);
                    intent.putExtra("name",request.name);
                    intent.putExtra("address",request.address);
                    intent.putExtra("token",request.token);
                    intent.putExtra("bloodgroup",request.RequestBloodGroup);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}