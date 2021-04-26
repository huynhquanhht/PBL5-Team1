package com.example.plb.ui.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.plb.R;
import com.example.plb.model.Account;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.Home.HomeActivity;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "https://plb5.000webhostapp.com/login.php";

    private Button mLoginButton;
    private EditText mIdEditText;
    private EditText mPassEditText;
    private ProgressDialog mLoadingBar;
    private CheckBox mRememberCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        String UserInfo = Paper.book().read(Prevalent.UserIdInfo);

        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                Prevalent.currentOnlineUser = new Account(UserPhoneKey, UserPhoneKey, UserPasswordKey, UserInfo);

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }

        init();

    }

    private void init() {
        mLoginButton = findViewById(R.id.loginButton);
        mIdEditText = findViewById(R.id.usernameEditText);
        mPassEditText = findViewById(R.id.passEditText);
        mLoadingBar = new ProgressDialog(this);
        mRememberCheckBox = findViewById(R.id.rememberCheckBox);

        mLoginButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton: {
                if (mIdEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Vui long nhap UserName", Toast.LENGTH_SHORT).show();
                } else if (mIdEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(this, "Vui long nhap PassWord", Toast.LENGTH_SHORT).show();
                } else {
                    mLoadingBar.setTitle("Login Account");
                    mLoadingBar.setMessage("Vui long doi mot chut, Dang kiem tra thong tin dang nhap");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                    checkLogin(url);
                }
                break;
            }
        }
    }

    private void checkLogin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")) {
                            mLoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Sai ten dang nhap hoac mat khau", Toast.LENGTH_SHORT).show();
                        } else {
                            mLoadingBar.dismiss();
                            Prevalent.currentOnlineUser = new Account(mIdEditText.getText().toString().trim(), mIdEditText.getText().toString().trim(),
                                    mPassEditText.getText().toString().trim(), response.trim());
                            AllowAccessToAcount(mIdEditText.getText().toString().trim(), mPassEditText.getText().toString().trim(), response.trim());
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Bug", error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id = mIdEditText.getText().toString().trim();
                String pass = mPassEditText.getText().toString().trim();

                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("pass", pass);

                return params;
            }
        };

        requestQueue.add(request);
    }

    public void AllowAccessToAcount(String phone, String password, String idinfo) {

        if (mRememberCheckBox.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
            Paper.book().write(Prevalent.UserIdInfo, idinfo);
        }

    }

}