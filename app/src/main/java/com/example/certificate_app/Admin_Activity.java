package com.example.certificate_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_Activity extends AppCompatActivity {

    CardView cd,cd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);

        cd=(CardView)findViewById(R.id.user_record);
        cd2=(CardView)findViewById(R.id.Managecert);

        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Activity.this, Generating_Certificate.class));
            }
        });


        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Admin_Activity.this, Registered_Users.class));

            }
        });


    }
}