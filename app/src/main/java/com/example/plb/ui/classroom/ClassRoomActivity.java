package com.example.plb.ui.classroom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Attendance;
import com.example.plb.model.Student;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.Home.HomeActivity;
import com.example.plb.ui.history.HistoryActivity;
import com.example.plb.ui.result.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClassRoomActivity extends AppCompatActivity {

    private final String url = "http://103.151.123.96:8000/student/attend/";
    private final String urlAttend = "http://103.151.123.96:8000/attend/create/";
    private final String urlStudent = "http://103.151.123.96:8000/student/create/";
    private final String urlRequest = "http://103.151.123.96:8000/attendance/";
    private final String urlCheck = "http://103.151.123.96:8000/checkattend/";

    private RecyclerView mStudentRecyclerView;
    private StudentAdapter mStudentAdapter;
    private List<Student> mStudentList = new ArrayList<>();
    private String idClass;
    private TextView mHistoryButton;
    private TextView mAttendButton;
    private Toolbar mToolbar;
    private String subject;
    private ProgressDialog mLoadingBar;
    private AlertDialog mAlertDialog;
    private Button mSubjectButton;
    private Button mTotalButton;
    private String codeclass;
    private String total;
    private String idAttend;
    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSubjectButton = findViewById(R.id.subjectButton);
        mTotalButton = findViewById(R.id.totalButton);

        idClass = getIntent().getExtras().getString("class");
        subject = getIntent().getExtras().getString("subject");
        total = getIntent().getExtras().getString("total");
        codeclass = getIntent().getExtras().getString("codeclass");

        mSubjectButton.setText("Subject: " + subject);
        mTotalButton.setText("Total Student: " + total);

        mLoadingBar = new ProgressDialog(this);

        mLoadingBar.setTitle("Loading");
        mLoadingBar.setMessage("Please wait");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        init();
        setupUI();

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Attendance")
                .setMessage("Do you want to roll call class " + subject)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mLoadingBar.show();

                        updateAttend();


                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
        mAlertDialog = b.create();

        mStudentList = mStudentAdapter.getStudentList();

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, HistoryActivity.class);
                intent.putExtra("idclass", idClass);
                intent.putExtra("subject", subject);
                startActivity(intent);
            }
        });

        mAttendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
    }

    private void updateStudent() {
        List<Student> students = new ArrayList<>();
        students.addAll(mStudentAdapter.getStudentList());

        for (int i = 0; i < students.size(); i++) {
            addStudent(students.get(i).getCodeStudent(), students.get(i).getName(), students.get(i).getPhone(),
                    students.get(i).getIdSchedule(), students.get(i).getUrlAttend(), students.get(i).getUrlAvatar(), students.get(i).getBirthDay());
        }


    }

    public void getChecked(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url;

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("false")) {
                    Log.d("BugChecked", "false");
                    Toast.makeText(ClassRoomActivity.this, "false", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClassRoomActivity.this, "True", Toast.LENGTH_SHORT).show();
                    Log.d("BugChecked", "true");


                    mLoadingBar.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BugRequest", error.toString());
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


    public void getRequestAttend(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + idClass + "&" + idAttend + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(ClassRoomActivity.this, ResultActivity.class);
                intent.putExtra("idattendance", idAttend);
                intent.putExtra("subject", subject);
                intent.putExtra("codeclass", codeclass);
                intent.putExtra("idclass", idClass);
                intent.putExtra("totalstudent", total);
                intent.putExtra("absent", response.trim());

                startActivity(intent);

                mLoadingBar.dismiss();
                Log.d("BugGetRequestAttend", "Finish");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("BugRequestAttend", error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        int socketTimeout = 1200000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        requestQueue.add(request);
    }


    private void addStudent(String codestudent, String name, String phone, String idschedule, String urlAttend, String urlAvater, String birthday) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, urlStudent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                Map<String, String> params = new HashMap<>();

                params.put("codestudent", codestudent);
                params.put("name", name);
                params.put("phone", phone);
                params.put("birthday", birthday);
                params.put("sex", "true");
                params.put("baseclass", codeclass);
                params.put("status", "1");
                params.put("urlavatar", urlAvater);
                params.put("urlattend", "");
                params.put("schedule", idschedule);
                params.put("attendance", idAttend);

                return params;
            }
        };

        requestQueue.add(request);
    }


    private void updateAttend() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, urlAttend,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        idAttend = response.trim();
                        updateStudent();
                        getRequestAttend(urlRequest);
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
                Map<String, String> params = new HashMap<>();

//                HH:mm:ss dd:MM:yyyy

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd:MM:yyyy");
                String saveCurrentDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
                String saveCurrentTime = currentTime.format(calendar.getTime());

                String time = saveCurrentTime + " " + saveCurrentDate;

                params.put("timeattend", time);
                params.put("total", total);
                params.put("status", "");
                params.put("codeclass", codeclass);
                params.put("subject", subject);
                params.put("schedule", idClass);

                return params;
            }
        };

        requestQueue.add(request);
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

            private void setupUI() {
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentAdapter = new StudentAdapter(mStudentList, this);
        mStudentRecyclerView.setAdapter(mStudentAdapter);

        getSchedule(url);


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

    private void init() {
        mStudentRecyclerView = findViewById(R.id.studentsRecyclerview);
        mToolbar = findViewById(R.id.toolbar);
        mHistoryButton = findViewById(R.id.historyOfClassTextView);
        mAttendButton = findViewById(R.id.attendTextView);
    }

    public void getSchedule(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = url + idClass + "/";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("false")) {
                    Toast.makeText(ClassRoomActivity.this, "No data attend", Toast.LENGTH_SHORT).show();
                    mLoadingBar.dismiss();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            String id = explrObject.getString("id");
                            String name = new String(explrObject.getString("name").getBytes("ISO-8859-1"), "UTF-8");
                            String codestudent = explrObject.getString("codestudent");
//                            String name = explrObject.getString("name");
                            String phone = explrObject.getString("phone");
                            String bir = explrObject.getString("birthday");
                            boolean sex = Boolean.parseBoolean(explrObject.getString("sex"));
                            String baseclass = explrObject.getString("baseclass");
                            String urlava = explrObject.getString("urlavatar");
                            String urlaten = explrObject.getString("urlattend");
                            int sta = Integer.parseInt(explrObject.getString("status"));
                            String idsch = explrObject.getString("schedule");
                            String idatend = explrObject.getString("schedule");

                            Student student = new Student(id, codestudent, name, phone, bir, sex, baseclass, urlava, urlaten, sta, "0", idsch, idatend);


                            if (sta == 0) {
                                student.setTotalAbsent("1");
                            } else if (sta == 1) {
                                student.setTotalAbsent("0");
                            }

                            mStudentList.add(student);
                        }

                        List<Student> temp = new ArrayList<>();

                        for (int i = 0; i < mStudentList.size(); i++) {
                            Student s = mStudentList.get(i);
                            for (int j = 0; j < mStudentList.size(); j++) {
                                if (mStudentList.get(i).getCodeStudent().equals(mStudentList.get(j).getCodeStudent()) && i != j) {
                                    s.setTotalAbsent(Integer.parseInt(mStudentList.get(j).getTotalAbsent()) + Integer.parseInt(s.getTotalAbsent()) + "");
                                    mStudentList.remove(j);
                                    j--;
                                }
                            }
                            temp.add(s);
                        }

                        mStudentList.clear();
                        mStudentList.addAll(temp);

                        mStudentAdapter.notifyDataSetChanged();
                        mLoadingBar.dismiss();
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.d("ClassRomBug", e.toString());
                    }
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