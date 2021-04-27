package com.example.plb.ui.classroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.example.plb.model.Student;
import com.example.plb.ui.history.HistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRoomActivity extends AppCompatActivity {

    private final String url = "https://plb5.000webhostapp.com/getSutdentAttend.php";

    private RecyclerView mStudentRecyclerView;
    private StudentAdapter mStudentAdapter;
    private List<Student> mStudentList = new ArrayList<>();
    private String idClass;
    private Button mHistoryButton;
    private Button mAttendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);

        idClass = getIntent().getExtras().getString("class");

        init();
        setupUI();

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, HistoryActivity.class);
                intent.putExtra("idschedule", idClass);
                startActivity(intent);
            }
        });


    }

    private void setupUI() {
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentAdapter = new StudentAdapter(mStudentList);
        mStudentRecyclerView.setAdapter(mStudentAdapter);

        getSchedule(url);


    }

    private void init() {

        mStudentRecyclerView = findViewById(R.id.studentsRecyclerview);
        mHistoryButton = findViewById(R.id.historyButton);
        mAttendButton = findViewById(R.id.attendButton);

    }

    public void getSchedule(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                                String id = explrObject.getString("id");
                                String codestudent = explrObject.getString("codestudent");
                                String name = explrObject.getString("name");
                                String phone = explrObject.getString("phone");
                                String bir = explrObject.getString("birthday");
                                int sex = explrObject.getInt("sex");
                                String baseclass = explrObject.getString("baseclass");
                                String urlava = explrObject.getString("urlavatar");
                                String urlaten = explrObject.getString("urlattend");
                                int sta = explrObject.getInt("status");
                                String total = explrObject.getString("total");
                                String idsch = explrObject.getString("idschedule");
                                String idatend = explrObject.getString("idattendance");

                                Student student = new Student(id, codestudent, name, phone, bir, sex, baseclass, urlava, urlaten, sta, total, idsch, idatend);
                                mStudentList.add(student);


                    }
                    mStudentAdapter.notifyDataSetChanged();
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