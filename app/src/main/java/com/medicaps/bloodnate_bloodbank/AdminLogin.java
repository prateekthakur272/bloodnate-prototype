package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AdminLogin extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        EditText adminID = findViewById(R.id.adminid);
        EditText adminPassword = findViewById(R.id.adminpassword);
        TextView capcha = findViewById(R.id.captcha);
        EditText solution = findViewById(R.id.solution);
        Button login = findViewById(R.id.login);
        // generating two random numbers for captcha verification
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10); //0 to 9
        int z = 0;
        // an array of operators
        char arr[] = {'+','-','x',};
        // choosing a random operator
        char operation = arr[random.nextInt(3)];
        capcha.setText(x +" "+operation+" "+y);
        // evaluating captcha
        switch (operation){
            case '+': z = x+y;break;
            case '-': z = x-y;break;
            case 'x': z = x*y;break;
        }
        // added some functionality to login button
        int finalZ = z;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to check whether the inputs are passed or not
                if (adminID.getText().toString().length() == 0 || adminPassword.getText().toString().length() == 0||solution.getText().toString().length()==0)
                    Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_LONG).show();
                else if(finalZ != Integer.parseInt(solution.getText().toString()))
                    // captcha validation
                    Toast.makeText(getApplicationContext(),"Incorrect Captcha",Toast.LENGTH_LONG).show();
                // username password verification
                else if(!adminID.getText().toString().equals("admin") || !adminPassword.getText().toString().equals("admin"))
                    Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_LONG).show();
                else gotoAdminSection();
            }
        });
    }
    // a function to call the next activity
    void gotoAdminSection(){
        startActivity(new Intent(this, AdminSection.class));
        finish();
    }
}