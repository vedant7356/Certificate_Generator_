package com.example.certificate_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_Login extends AppCompatActivity {

    Button login,main_login;
    EditText et1,et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin__login);
        //getActionBar().hide();
        login=(Button)findViewById(R.id.bt_login_admin);
        main_login=(Button)findViewById(R.id.bt_login_main_page_admin);
        et1=(EditText)findViewById(R.id.login_email);
        et2=(EditText)findViewById(R.id.login_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et1.getText().toString().trim();
                String password = et2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    et1.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    et2.setError("Password is Required.");
                    return;
                }

                if(email.equals("admin@123") && password.equals("12345")){
                    startActivity(new Intent(Admin_Login.this,Admin_Activity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_LONG).show();
                }

            }
        });

        main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Login.this,MainActivity.class));
            }
        });
    }
}