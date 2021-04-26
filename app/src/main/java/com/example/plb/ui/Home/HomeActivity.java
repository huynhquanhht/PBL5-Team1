package com.example.plb.ui.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.example.plb.model.Schedule;
import com.example.plb.prevalent.Prevalent;
import com.example.plb.ui.classroom.ClassRoomActivity;
import com.example.plb.ui.history.HistoryActivity;
import com.example.plb.ui.login.MainActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final String url = "https://plb5.000webhostapp.com/getSchedule.php";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView mNavButton;
    private ProgressDialog mLoadingBar;
    private Button mAttendanceButton;
    private RecyclerView mRecyclerView;
    private List<Schedule> mScheduleList = new ArrayList<>();
    private ClassAdapter mClassAdapter;
    private Toolbar mToolbar;
    private TextView mClassTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();
        setupUI();

    }

    private void setupUI() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mClassAdapter = new ClassAdapter(mScheduleList);
        mRecyclerView.setAdapter(mClassAdapter);


        mLoadingBar.setTitle("Load thời khóa biểu");
        mLoadingBar.setMessage("Vui lòng đợi");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        getSchedule(url);

        mClassAdapter.setOnClickListener(new ClassAdapter.OnClickListener() {
            @Override
            public void onClick(Schedule classRoom, int position) {
                Intent intent = new Intent(HomeActivity.this, ClassRoomActivity.class);
                intent.putExtra("class", classRoom.getId());
                startActivity(intent);
            }
        });


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
                        mScheduleList.add(new Schedule(
                                explrObject.getString("id"),
                                explrObject.getString("subject"),
                                explrObject.getString("timestart"),
                                explrObject.getString("timeend"),
                                explrObject.getString("room"),
                                explrObject.getString("idacount")
                                ));
                    }

                    mClassAdapter.notifyDataSetChanged();
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

                String id = Prevalent.currentOnlineUser.getId();

                Map<String, String> params = new HashMap<>();
                params.put("id", id);

                return params;
            }
        };

        requestQueue.add(request);
    }


    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navView);
        mNavigationView = findViewById(R.id.navView);
        mRecyclerView = findViewById(R.id.listClassRecyclerView);

        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mLoadingBar = new ProgressDialog(this);


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_history: {
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            } case R.id.logout: {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}