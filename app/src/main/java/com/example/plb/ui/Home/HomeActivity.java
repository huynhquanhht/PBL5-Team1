package com.example.plb.ui.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plb.R;
import com.example.plb.model.ClassRoom;
import com.example.plb.model.Student;
import com.example.plb.ui.history.HistoryActivity;
import com.example.plb.ui.infor.InforActivity;
import com.example.plb.ui.result.ResultActivity;
import com.example.plb.ui.schedule.ScheduleActivity;
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
    private List<ClassRoom> mClassRooms = new ArrayList<>();
    private TypeClassAdapter mTypeClassAdapter;
    private Toolbar mToolbar;
    private TextView mClassTextView;
    private Spinner mBuildingSpinner;
    private Spinner mSerialSpinner;
    private ArrayAdapter mBuildingAdapter;
    private ArrayAdapter mSerialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        init();
        setupUI();

        Toast.makeText(this, "Vui lòng chọn lớp học và phòng học cần điểm danh...", Toast.LENGTH_SHORT).show();

    }

    private void setupUI() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTypeClassAdapter = new TypeClassAdapter(mClassRooms);
        mRecyclerView.setAdapter(mTypeClassAdapter);

        List<String> buildings = new ArrayList<>();
        buildings.add("A");
        buildings.add("B");
        buildings.add("C");
        buildings.add("D");
        buildings.add("E");
        buildings.add("F");

        List<String> classRooms = new ArrayList<>();
        classRooms.add("101");
        classRooms.add("102");
        classRooms.add("103");
        classRooms.add("104");
        classRooms.add("105");
        classRooms.add("201");
        classRooms.add("202");
        classRooms.add("203");

        mLoadingBar.setTitle("Điểm danh");
        mLoadingBar.setMessage("Vui lòng đợi, Hệ thông đang trong qua trình điểm danh");
        mLoadingBar.setCanceledOnTouchOutside(false);

        mBuildingAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, buildings);
        mSerialAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, classRooms);

        mSerialSpinner.setAdapter(mSerialAdapter);
        mBuildingSpinner.setAdapter(mBuildingAdapter);

        fakeData();

        mTypeClassAdapter.setOnClickListener(new TypeClassAdapter.OnClickListener() {
            @Override
            public void onClick(Student student, int position) {
                Intent intent = new Intent(HomeActivity.this, InforActivity.class);
                startActivity(intent);
            }
        });

        mAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingBar.show();

                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        mLoadingBar.dismiss();

                        Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                        startActivity(intent);

                    }
                }.start();
            }
        });


    }

    public void fakeData() {


        ClassRoom classRoom1 = new ClassRoom("Toan", "7h - 8h", "1", 32);
        ClassRoom classRoom2 = new ClassRoom("Ly", "8h -9h", "2", 32);
        ClassRoom classRoom3 = new ClassRoom("Hoa", "9h - 10h", "3", 32);
        ClassRoom classRoom4 = new ClassRoom("Anh", "10h - 11h", "4", 32);
        ClassRoom classRoom5 = new ClassRoom("Van", "11h - 12h", "5", 32);

        mClassRooms.add(classRoom1);
        mClassRooms.add(classRoom2);
        mClassRooms.add(classRoom3);
        mClassRooms.add(classRoom4);
        mClassRooms.add(classRoom5);

        mTypeClassAdapter.notifyDataSetChanged();

    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navView);
        mNavigationView = findViewById(R.id.navView);
        mAttendanceButton = findViewById(R.id.attendanceButton);
        mBuildingSpinner = findViewById(R.id.buildingSpinner);
        mSerialSpinner = findViewById(R.id.serialSpinner);
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRecyclerView = findViewById(R.id.idClassRecyclerview);

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
            case R.id.nav_class: {
                Toast.makeText(this, "bbbbb", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, ScheduleActivity.class);
                startActivity(intent);
                break;
            } case R.id.nav_history: {
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}