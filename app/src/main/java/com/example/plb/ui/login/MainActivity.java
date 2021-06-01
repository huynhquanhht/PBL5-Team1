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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.plb.ui.student.StudentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "http://103.151.123.96:8000/login/";
    private final String urlCheckStudent = "http://103.151.123.96:8000/student/";

    private Button mLoginButton;
    private EditText mIdEditText;
    private EditText mPassEditText;
    private ProgressDialog mLoadingBar;
    private CheckBox mRememberCheckBox;
    private TextView mStudentTextView;
    private boolean mIsStudent = false;

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
                finish();
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
        mStudentTextView = findViewById(R.id.studentTextView);

        mLoginButton.setOnClickListener(this);
        mStudentTextView.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton: {
                if (mIdEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Vui long nhap UserName", Toast.LENGTH_SHORT).show();
                } else if (mIdEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Vui long nhap PassWord", Toast.LENGTH_SHORT).show();
                } else {
                    mLoadingBar.setTitle("Login Account");
                    mLoadingBar.setMessage("Vui long doi mot chut, Dang kiem tra thong tin dang nhap");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();

                    if (mIsStudent) {
                        checkStudent(urlCheckStudent);
                    } else {
                        checkLogin(url);
                    }
                }
                break;
            }
            case R.id.studentTextView: {
                mIsStudent = !mIsStudent;

                if (mIsStudent) {
                    mStudentTextView.setText("I am teacher");
                    mLoginButton.setText("Login with student");
                    mLoginButton.setAllCaps(false);
                    mPassEditText.setHint("Mã học phần");
                } else {
                    mStudentTextView.setText("I am student");
                    mLoginButton.setText("Login");
                    mLoginButton.setAllCaps(false);
                    mPassEditText.setHint("Mật khẩu");
                }
            }
        }
    }

    private void checkStudent(String urlStudent) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String id = mIdEditText.getText().toString().trim();
        String pass = mPassEditText.getText().toString().trim();

        String link = urlStudent + id + "&" + pass + "/";
//        String link = "http://103.151.123.96:8000/student/1021801a37&18Nh13/";


        StringRequest request = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("false")) {
                            mLoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Khong co du lieu diem danh", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                            intent.putExtra("idstudent", mIdEditText.getText().toString().trim());
                            intent.putExtra("codeclass", mPassEditText.getText().toString().trim());
                            startActivity(intent);
                            mLoadingBar.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Bug", error.toString());
                    }
                });

        requestQueue.add(request);

    }

    private void checkLogin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String id = mIdEditText.getText().toString().trim();
        String pass = mPassEditText.getText().toString().trim();

        String link = url + id + "&" + pass;

        StringRequest request = new StringRequest(Request.Method.GET,
                link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("false")) {
                            mLoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Sai ten dang nhap hoac mat khau", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject explrObject = jsonArray.getJSONObject(0);

                                String id = explrObject.getString("id");
                                String idinfo = explrObject.getString("infor");
                                Prevalent.currentOnlineUser = new Account(id, mIdEditText.getText().toString().trim(),
                                        mPassEditText.getText().toString().trim(), idinfo);
                                AllowAccessToAcount(id, mIdEditText.getText().toString().trim(), mPassEditText.getText().toString().trim(), idinfo);
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mLoadingBar.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoadingBar.dismiss();
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(request);
    }

    public void AllowAccessToAcount(String id, String name, String password, String idinfo) {

        if (mRememberCheckBox.isChecked()) {


            Paper.book().write(Prevalent.UserPhoneKey, id);
            Paper.book().write(Prevalent.UserName, name);
            Paper.book().write(Prevalent.UserPasswordKey, password);
            Paper.book().write(Prevalent.UserIdInfo, idinfo);
        }

    }

}