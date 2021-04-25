package com.example.plb.ui.Home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Schedule;
import com.example.plb.ui.classroom.ClassRoomActivity;
import com.example.plb.ui.history.HistoryActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

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


        mLoadingBar.setTitle("Điểm danh");
        mLoadingBar.setMessage("Vui lòng đợi, Hệ thông đang trong qua trình điểm danh");
        mLoadingBar.setCanceledOnTouchOutside(false);


        fakeData();

        mClassAdapter.setOnClickListener(new ClassAdapter.OnClickListener() {
            @Override
            public void onClick(Schedule classRoom, int position) {
                Intent intent = new Intent(HomeActivity.this, ClassRoomActivity.class);
                intent.putExtra("class", classRoom.getId());
                startActivity(intent);
            }
        });


    }

    public void fakeData() {

        Schedule classRoom1 = new Schedule("1813", "Giai Tich", "7h", "8h", "E301", "0919199999");
        Schedule classRoom2 = new Schedule("1813A", "Giai Tich", "7h", "8h", "E301", "0919199999");
        Schedule classRoom3 = new Schedule("1813B", "Giai Tich", "7h", "8h", "E301", "0919199999");
        Schedule classRoom4 = new Schedule("1813C", "Giai Tich", "7h", "8h", "E301", "0919199999");


        mScheduleList.add(classRoom1);
        mScheduleList.add(classRoom2);
        mScheduleList.add(classRoom3);

        mClassAdapter.notifyDataSetChanged();

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
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}