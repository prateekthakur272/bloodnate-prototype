package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
// activity request options let us to choose what to do with the request, like delete or approve it
public class RequestOptions extends AppCompatActivity {
    RequestDatabaseHelper requestDatabaseHelper = new RequestDatabaseHelper(this);
    BloodDatabaseHelper bloodDatabaseHelper = new BloodDatabaseHelper(this);
    TextView details;
    Button approve,delete;
    EditText editText;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_options);
        // fetching extras from intent bundle
        Bundle extras = getIntent().getExtras();
        Request currentRequest = requestDatabaseHelper.getRequestList().get((int)extras.get("requestPosition"));
        details = findViewById(R.id.details);
        delete = findViewById(R.id.delete);
        approve = findViewById(R.id.approve);
        editText = findViewById(R.id.editText);
        // show details of request in a Text view according to its request type
        if (currentRequest.requestType == RequestType.COLLECTION_REQUEST)
        message = "Token: " + currentRequest.token + "\nName: " +currentRequest.name + "\nEmail: " + currentRequest.email + "\nAddress: " + currentRequest.address + "\nRequest type: " + currentRequest.requestType.toString() + "\nBlood: " + BloodGroup.BG(currentRequest.RequestBloodGroup) + "\nVolume: " + currentRequest.volume + (currentRequest.DonorBloodGroup == null?"":"\nDonating: "+BloodGroup.BG(currentRequest.DonorBloodGroup));
        else
            message = "Token: " + currentRequest.token + "\nName: " +currentRequest.name + "\nEmail: " + currentRequest.email + "\nAddress: " + currentRequest.address + "\nRequest type: " + currentRequest.requestType.toString() + "\nBlood: " + BloodGroup.BG(currentRequest.DonorBloodGroup);
        details.setText(message);
        // set function of button APPROVE
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentRequest.requestType == RequestType.DONATION_REQUEST) {
                    if (editText.getVisibility() == View.INVISIBLE)
                        editText.setVisibility(View.VISIBLE);
                    else if (editText.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Enter a valid value", Toast.LENGTH_LONG).show();
                    } else {
                        bloodDatabaseHelper.updateValueAvail(BloodGroup.BG(currentRequest.DonorBloodGroup),bloodDatabaseHelper.getAvail(BloodGroup.BG(currentRequest.DonorBloodGroup))+Integer.parseInt(editText.getText().toString()));
                        requestDatabaseHelper.deleteRequest(currentRequest);
                        finish();
                    }
                }else {
                    if(bloodDatabaseHelper.getAvail(BloodGroup.BG(currentRequest.RequestBloodGroup))<currentRequest.volume)
                        Toast.makeText(getApplicationContext(), "Requested amount of blood not available", Toast.LENGTH_LONG).show();
                    else if(currentRequest.DonorBloodGroup!=null && editText.getVisibility() == View.INVISIBLE)
                        editText.setVisibility(View.VISIBLE);
                    else if(editText.getText().toString().length()==0 && editText.getVisibility() == View.VISIBLE)
                        Toast.makeText(getApplicationContext(), "Enter a valid value", Toast.LENGTH_LONG).show();
                    else{   // upgrade values in database and delete the request from RequestDatabase
                            bloodDatabaseHelper.updateValueUncleared(BloodGroup.BG(currentRequest.RequestBloodGroup),bloodDatabaseHelper.getUnclear(BloodGroup.BG(currentRequest.RequestBloodGroup))+currentRequest.volume);
                            bloodDatabaseHelper.updateValueAvail(BloodGroup.BG(currentRequest.RequestBloodGroup),bloodDatabaseHelper.getAvail(BloodGroup.BG(currentRequest.RequestBloodGroup))-currentRequest.volume);
                            if(currentRequest.DonorBloodGroup!=null)
                                bloodDatabaseHelper.updateValueAvail(BloodGroup.BG(currentRequest.DonorBloodGroup),bloodDatabaseHelper.getAvail(BloodGroup.BG(currentRequest.DonorBloodGroup))+Integer.parseInt(editText.getText().toString()));
                            requestDatabaseHelper.deleteRequest(currentRequest);
                            finish();
                        }
                    }
                }
        });
        // set function of button DELETE
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodDatabaseHelper.updateValueUncleared(BloodGroup.BG(currentRequest.RequestBloodGroup),bloodDatabaseHelper.getUnclear(BloodGroup.BG(currentRequest.RequestBloodGroup))+currentRequest.volume);
                boolean success = requestDatabaseHelper.deleteRequest(currentRequest);
                if(success)
                    Toast.makeText(getApplicationContext(),"Request deleted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),PendingRequests.class));
                finish();
            }
        });
    }
}