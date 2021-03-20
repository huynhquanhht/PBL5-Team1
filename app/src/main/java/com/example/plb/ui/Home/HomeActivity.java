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
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plb.R;
import com.example.plb.model.Student;
import com.example.plb.ui.infor.InforActivity;
import com.example.plb.ui.login.MainActivity;
import com.example.plb.ui.schedule.ScheduleActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView mNavButton;
    private RecyclerView mRecyclerView;
    private List<Student> mStudentList = new ArrayList<>();
    private StudentAdapter mStudentAdapter;
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
        mStudentAdapter = new StudentAdapter(mStudentList);
        mRecyclerView.setAdapter(mStudentAdapter);

        fakeData();

        mStudentAdapter.setOnClickListener(new StudentAdapter.OnClickListener() {
            @Override
            public void onClick(Student student, int position) {
                Intent intent = new Intent(HomeActivity.this, InforActivity.class);
                startActivity(intent);
            }
        });
    }

    public void fakeData() {
        Student student1 = new Student("102180138", "Le Truong Sanh", "18TCLC-DT1");
        Student student2 = new Student("102180139", "Huynh Van Quan", "18TCLC-DT1");
        Student student3 = new Student("102180137", "Phan Anh Tuan", "18TCLC-DT1");
        Student student4 = new Student("102180136", "Trinh Xuan Phuc", "18TCLC-DT1");
        Student student5 = new Student("102180135", "Nguu Ma Vuong", "18TCLC-DT1");
        Student student6 = new Student("102180134", "Ton Ngo Khong", "18TCLC-DT1");

        mStudentList.add(student1);
        mStudentList.add(student2);
        mStudentList.add(student3);
        mStudentList.add(student4);
        mStudentList.add(student5);
        mStudentList.add(student6);

        mStudentAdapter.notifyDataSetChanged();

    }

    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navView);
        mNavigationView = findViewById(R.id.navView);
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mRecyclerView = findViewById(R.id.recyclerview);


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
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}