package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
// this activity shows the status of the request, whether request placed or not
public class StatusActivity extends AppCompatActivity {
    ImageView imageView;
    TextView status,message;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Bundle extras = getIntent().getExtras();
        imageView = findViewById(R.id.status_image);
        // if status is true then request is places otherwise request failed
        status = findViewById(R.id.status);
        message = findViewById(R.id.message);
        if ((boolean)extras.get("status")){
            imageView.setImageResource(R.drawable.success);
            status.setText("Request Successful");
            message.setText("Dear "+extras.get("name")+",\nYour request for "+((RequestType)extras.get("type")).toString()+" of blood group "+BloodGroup.BG((BloodGroup)extras.get("bloodgroup"))+" is submitted to us, We will contact you soon. \nYour token/reference number is "+extras.get("token"));
            message.setVisibility(View.VISIBLE);
        }else {
            imageView.setImageResource(R.drawable.error);
            status.setText("Request Failed");
            message.setVisibility(View.VISIBLE);
            message.setText("Dear "+extras.get("name")+" there is some technical issue, we regret the invoice created");
        }
    }
}