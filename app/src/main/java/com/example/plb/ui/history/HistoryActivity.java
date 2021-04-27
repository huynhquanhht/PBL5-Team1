package com.example.plb.ui.history;

import android.os.Bundle;
import android.util.Log;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private final String url = "https://plb5.000webhostapp.com/getHistoryClass.php";

    private RecyclerView mRecyclerView;
    private List<Attendance> mHistoryList = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;
    private String idClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        idClass = getIntent().getExtras().getString("idschedule");

        mRecyclerView = findViewById(R.id.historyRecyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter(mHistoryList);
        mRecyclerView.setAdapter(mHistoryAdapter);

        mHistoryAdapter.notifyDataSetChanged();

        setupUI();

    }

    private void setupUI() {

        getClassHistory(url);


    }

    public void getClassHistory(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        String id = explrObject.getString("id");
                        String timeattend = explrObject.getString("timeatend");
                        String idschedule = explrObject.getString("idschedule");
                        String absent = explrObject.getString("absent");

                        Attendance attendance = new Attendance(id, timeattend, idschedule, absent);
                        mHistoryList.add(attendance);


                    }
                    mHistoryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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

                Map<String, String> params = new HashMap<>();
                params.put("id", idClass);

                return params;
            }
        };

        requestQueue.add(request);
    }

}