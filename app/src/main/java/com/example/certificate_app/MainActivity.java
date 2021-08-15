package com.example.certificate_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button bt,bt_admin;
    TextView t1;
    FirebaseAuth fAuth;
    EditText username,userpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.login_email);
        userpassword=(EditText)findViewById(R.id.login_password);
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        bt=(Button)findViewById(R.id.log_bt);
        bt_admin=(Button)findViewById(R.id.log_bt_admin);
        t1=(TextView)findViewById(R.id.reg_here);
        bt_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Admin_Login.class));
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register_Screen.class));
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(MainActivity.this,Main_Page.class));
                String email = username.getText().toString().trim();
                String password = userpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    username.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    userpassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    userpassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, " Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),User_dashboard.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "You are not REGISTERED!, Please do register in our APP first ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // startActivity(new Intent(MainActivity.this,User_Main_Activity.class));
            }
        });
    }
}