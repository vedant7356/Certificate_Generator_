package com.example.certificate_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class User_dashboard extends AppCompatActivity {
    Button log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        log_out=(Button)findViewById(R.id.bt_log_out);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                Toast.makeText(User_dashboard.this, "You have been logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}