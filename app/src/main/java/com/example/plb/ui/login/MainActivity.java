package com.example.plb.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plb.R;
import com.example.plb.ui.Home.HomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        mLoginButton = findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton: {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}