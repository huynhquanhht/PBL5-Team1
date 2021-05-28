package com.example.plb.ui.infor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Info;
import com.example.plb.model.Student;
import com.example.plb.prevalent.Prevalent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class InforActivity extends AppCompatActivity {

    private final String infoUrl = "http://103.151.123.96:8000/info/";

    private EditText mNameEditText, mEmailEditText, mPhoneEditText, mDateEditText;
    private CircleImageView mCircleImageView;
    private ProgressDialog mLoadingBar;
    private ImageView mBackArrowImageView;
    private String idInfo;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);

        idInfo = getIntent().getExtras().getString("idInfo");

        mEmailTextView = findViewById(R.id.emailTextView);
        mNameEditText = findViewById(R.id.nameEditText);
        mEmailEditText = findViewById(R.id.emailEditText);
        mPhoneEditText = findViewById(R.id.phoneEditText);
        mDateEditText = findViewById(R.id.dateEditText);
        mBackArrowImageView = findViewById(R.id.backArrowImageView);

        mCircleImageView = findViewById(R.id.avatartInfoImageView);

        mLoadingBar = new ProgressDialog(this);

        mLoadingBar.setTitle("Loading");
        mLoadingBar.setMessage("Please wait");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        if (idInfo == null) {
            Student student = (Student) getIntent().getSerializableExtra("student");

            Glide.with(getApplicationContext())
                    .load(student.getUrlAvatar())
                    .placeholder(R.drawable.loading)
                    .into(mCircleImageView);

            mEmailTextView.setText("Base class");

            mNameEditText.setText(student.getName());
            mEmailEditText.setText(student.getBaseClass());
            mPhoneEditText.setText(student.getPhone());
            mDateEditText.setText(student.getBirthDay());
            mLoadingBar.dismiss();


        } else {
            getInfo(infoUrl);
        }

        mBackArrowImageView.setOnClickListener(v -> finish());

    }

    public void getInfo(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + Prevalent.currentOnlineUser.getIdInfo() + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject explrObject = jsonArray.getJSONObject(0);

                    String id = explrObject.getString("id");
                    String name = explrObject.getString("name");
                    String email = explrObject.getString("email");
                    String phone = explrObject.getString("phone");
                    String sex = explrObject.getString("sex");
                    String birthday = explrObject.getString("birthday");
                    String url = explrObject.getString("url");

                    Glide.with(getApplicationContext())
                            .load(url)
                            .placeholder(R.drawable.loading)
                            .into(mCircleImageView);
                    mNameEditText.setText(name);
                    mEmailEditText.setText(email);
                    mPhoneEditText.setText(phone);
                    mDateEditText.setText(birthday);

                    mLoadingBar.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Bug", error.toString());
            }
        });

        requestQueue.add(request);
    }

}