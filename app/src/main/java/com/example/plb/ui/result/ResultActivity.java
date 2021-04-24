package com.example.plb.ui.result;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Student;
import com.example.plb.ui.infor.InforActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mStudentsRecyclerview;
    private StudentAdapter mStudentAdapter;

    private List<Student> mStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        setupUI();


    }

    private void setupUI() {
        mStudents.add(new Student("102180131", "Nguyen Nga A", "18TCLC_DT1", false));
        mStudents.add(new Student("102180132", "Nguyen Nga B", "18TCLC_DT1", false));
        mStudents.add(new Student("102180133", "Nguyen Nga C", "18TCLC_DT1", true));
        mStudents.add(new Student("102180134", "Nguyen Nga D", "18TCLC_DT1", true));
        mStudents.add(new Student("102180135", "Nguyen Nga E", "18TCLC_DT1", true));
        mStudents.add(new Student("102180136", "Nguyen Nga F", "18TCLC_DT1", true));

        mStudentAdapter = new StudentAdapter(mStudents);
        mStudentsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mStudentsRecyclerview.setAdapter(mStudentAdapter);
        mStudentAdapter.notifyDataSetChanged();

        mStudentAdapter.setOnClickListener(new StudentAdapter.OnClickListener() {
            @Override
            public void onClick(Student student, int position) {
                Intent intent = new Intent(ResultActivity.this, InforActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        mSearchView = findViewById(R.id.studentSearchView);
        mStudentsRecyclerview = findViewById(R.id.studentsRecyclerview);
    }
}