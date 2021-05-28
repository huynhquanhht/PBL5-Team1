package com.example.plb.ui.history;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.plb.R;
import com.example.plb.model.Attendance;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.result.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private final String url = "http://103.151.123.96:8000/class/";
    private final String url1 = "http://103.151.123.96:8000/class/gv/";


    private RecyclerView mRecyclerView;
    private List<Attendance> mHistoryList = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;
    private String idClass;
    private String subject;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idClass = getIntent().getExtras().getString("idclass");
        subject = getIntent().getExtras().getString("subject");

        mLoadingBar = new ProgressDialog(this);
        mLoadingBar.setTitle("Loading");
        mLoadingBar.setMessage("Please wait");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        mRecyclerView = findViewById(R.id.historyRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter(mHistoryList, subject);
        mRecyclerView.setAdapter(mHistoryAdapter);

        mHistoryAdapter.notifyDataSetChanged();

        setupUI();

        mHistoryAdapter.setOnClickListener(new HistoryAdapter.OnClickListener() {
            @Override
            public void onClick(Attendance attendance, int position) {
                Intent intent = new Intent(HistoryActivity.this, ResultActivity.class);
                intent.putExtra("idattendance", attendance.getId());
                intent.putExtra("subject", subject);
                intent.putExtra("idclass", idClass);
                intent.putExtra("totalstudent", attendance.getTotal());
                intent.putExtra("absent", attendance.getAbsent());
                startActivity(intent);
            }
        });

    }

    private void setupUI() {

        if (idClass.isEmpty()) {
            getAllClassHistory(url1);
        } else {
            getClassHistory(url);
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }

        }
        return true;
    }

    public void getAllClassHistory(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + Prevalent.currentOnlineUser.getId() + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("false")) {
                    mLoadingBar.dismiss();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<Attendance> attendances = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            String id = explrObject.getString("id");
                            String codeclass = explrObject.getString("codeclass");
                            String subject = explrObject.getString("subject");
                            String timeattend = explrObject.getString("timeattend");
                            String idschedule = explrObject.getString("schedule");
                            String absent = explrObject.getString("status");
                            String total = explrObject.getString("total");

                            Attendance attendance = new Attendance(id,subject, codeclass, timeattend, idschedule ,absent, total);
                            attendances.add(attendance);
                        }

                        mHistoryList.clear();
                        mHistoryList.addAll(attendances);
                        mHistoryAdapter.notifyDataSetChanged();
                        mLoadingBar.dismiss();
                    } catch (JSONException e) {
                        Log.d("Bug", e.toString());
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Bug", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue.add(request);
    }


    public void getClassHistory(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + idClass + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("false")) {
                    mLoadingBar.dismiss();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            String id = explrObject.getString("id");
                            String codeclass = explrObject.getString("codeclass");
                            String subject = explrObject.getString("subject");
                            String timeattend = explrObject.getString("timeattend");
                            String idschedule = explrObject.getString("schedule");
                            String absent = explrObject.getString("status");
                            String total = explrObject.getString("total");

                            Attendance attendance = new Attendance(id,subject, codeclass, timeattend, idschedule ,absent, total);
                            mHistoryList.add(attendance);
                        }
                        mHistoryAdapter.notifyDataSetChanged();
                        mLoadingBar.dismiss();
                    } catch (JSONException e) {
                        Log.d("Bug", e.toString());
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Bug", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue.add(request);
    }

}