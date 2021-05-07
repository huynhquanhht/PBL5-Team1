package com.example.plb.ui.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
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
import com.example.plb.ui.infor.InforActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private final String url = "https://plb5.000webhostapp.com/getStudentResult.php";

    private SearchView mSearchView;
    private TextView subjectTextView, codeTextView, totalTextView, absentTextView;
    private RecyclerView mStudentsRecyclerview;
    private StudentAdapter mStudentAdapter;
    private ProgressDialog mLoadingBar;
    private String idClass, idBaseClass, subject, totalStudent, absent;
    private SwitchCompat mSwitch;

    private List<Student> mStudentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idClass = getIntent().getExtras().getString("idattendance");
        idBaseClass = getIntent().getExtras().getString("idclass");
        subject = getIntent().getExtras().getString("subject");
        totalStudent = getIntent().getExtras().getString("totalstudent");
        absent = getIntent().getExtras().getString("absent");

        mLoadingBar = new ProgressDialog(this);

        mLoadingBar.setTitle("Loading");
        mLoadingBar.setMessage("Please wait");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        init();
        setupUI();

        getStudent(url);

    }

    private void setupUI() {

        subjectTextView.setText(subject);
        codeTextView.setText(idBaseClass);
        totalTextView.setText("Total: " + totalStudent);
        absentTextView.setText("Absent: " + absent);

        mStudentAdapter = new StudentAdapter(mStudentList, this);
        mStudentsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mStudentsRecyclerview.setAdapter(mStudentAdapter);
        mStudentAdapter.notifyDataSetChanged();

        mStudentAdapter.setOnClickListener(new StudentAdapter.OnClickListener() {
            @Override
            public void onClick(Student student, int position) {
                Intent intent = new Intent(ResultActivity.this, InforActivity.class);
                intent.putExtra("student", student);
                startActivity(intent);
            }
        });

    }

    private void init() {
        mStudentsRecyclerview = findViewById(R.id.studentRecyclerview);
        subjectTextView = findViewById(R.id.baseClassTextView);
        codeTextView = findViewById(R.id.phoneTextView);
        totalTextView = findViewById(R.id.birthDayTextView);
        absentTextView = findViewById(R.id.totalAbsentTextView);
        mSwitch = findViewById(R.id.manualAttendSwitch);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mStudentAdapter.setChecked();
                mStudentAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem mSearch = menu.findItem(R.id.search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search...");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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

    private void filter(String newText) {
        List<Student> users = new ArrayList<>();

        for (Student item : mStudentList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                users.add(item);
            }
        }

        if (users.isEmpty()) {
        } else {
            mStudentAdapter.filterList(users);
        }
    }


    public void getStudent(String url) {
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