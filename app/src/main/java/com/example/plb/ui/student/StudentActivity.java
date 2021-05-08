package com.example.plb.ui.student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Attendance;
import com.example.plb.model.Info;
import com.example.plb.model.StudentAttend;
import com.example.plb.prevalent.Prevalent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    private final String url = "https://plb5.000webhostapp.com/getStudentLoginAttend.php";

    private ImageView mAvatarImageView;
    private TextView mNameTextView;
    private TextView mBirthDayTextView;
    private TextView mBaseClassTextView;
    private RecyclerView mRecyclerView;
    private String idStudent, codeClass;
    private List<StudentAttend> mStudentAttendList = new ArrayList<>();
    private HistoryAdapter mStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        idStudent = getIntent().getExtras().getString("idstudent");
        codeClass = getIntent().getExtras().getString("codeclass");

        mAvatarImageView = findViewById(R.id.avatarMainImageView);
        mNameTextView = findViewById(R.id.nameMainTextView);
        mBirthDayTextView = findViewById(R.id.birthDayTextView);
        mBaseClassTextView = findViewById(R.id.phoneTextView);
        mRecyclerView = findViewById(R.id.studentRecyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentAdapter = new HistoryAdapter(mStudentAttendList);
        mRecyclerView.setAdapter(mStudentAdapter);

        getInfo(url);
    }

    public void getInfo(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);
                        String name = explrObject.getString("name");
                        String urlavatar = explrObject.getString("urlavatar");
                        String baseclass = explrObject.getString("baseclass");
                        String subject = explrObject.getString("subject");
                        String room = explrObject.getString("room");
                        String id = explrObject.getString("id");
                        String timeattend = explrObject.getString("timeattend");
                        String status = explrObject.getString("status");

                        StudentAttend studentAttend = new StudentAttend(name, urlavatar, baseclass, subject, room, id, timeattend, status);
                        mStudentAttendList.add(studentAttend);
                    }
                    mStudentAdapter.notifyDataSetChanged();

                    Glide.with(getApplicationContext())
                            .load(mStudentAttendList.get(0).getUrlAvatar())
                            .placeholder(R.drawable.loading)
                            .into(mAvatarImageView);

                    mNameTextView.setText(mStudentAttendList.get(0).getName());
                    mBaseClassTextView.setText(mStudentAttendList.get(0).getBaseclass());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("bug", e.toString());
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
                params.put("id", idStudent);
                params.put("codeclass", codeClass);

                return params;
            }
        };

        requestQueue.add(request);
    }


}