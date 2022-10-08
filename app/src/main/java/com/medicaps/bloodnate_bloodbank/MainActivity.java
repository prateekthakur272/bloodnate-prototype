/*
android sdk tools version 31.0
android version 8.0 to 12.0 supported
API level 31

This is the main activity of the application or the first activity to be executed when application the application executed
*/
package com.medicaps.bloodnate_bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
// class main activity inheriting properties of class AppCompactActivity to add some new features of newer android sdk to
// the activity.
//new features include automatic switching to dark mode, using the action bar, navigation modes etc.
public class MainActivity extends AppCompatActivity {
    // onCreate method is executed when activity executed
    // it is equivalent to the main method of a console or terminal based application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // adding some functionality to Button
        Button donate = (Button) findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoDonateActivity();
            }
        });

        Button request = (Button) findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoRequestActivity();
            }
        });

        Button admin_login = (Button) findViewById(R.id.admin_login);
        admin_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoAdminLogin();
            }
        });

    }
    void gotoRequestActivity(){
        // calling other activity from current activity
        startActivity(new Intent(this, RequestActivity.class));
    }
    void gotoDonateActivity(){
        startActivity(new Intent(this, DonateActivity.class));
    }
    void gotoAdminLogin(){
        startActivity(new Intent(this, AdminLogin.class));
    }
}