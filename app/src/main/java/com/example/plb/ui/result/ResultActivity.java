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
        mStudents.add(new Student("1", "102180138", "Nguyen Van A", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2",true, "3", "1", "1"));
        mStudents.add(new Student("1", "102180139", "Nguyen Van B", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2", true,"3", "1", "1"));
        mStudents.add(new Student("1", "102180137", "Nguyen Van C", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2", true,"3", "1", "1"));
        mStudents.add(new Student("1", "102180136", "Nguyen Van D", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2", true,"3", "1", "1"));
        mStudents.add(new Student("1", "102180135", "Nguyen Van E", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2", true,"3", "1", "1"));
        mStudents.add(new Student("1", "102180134", "Nguyen Van F", "0918199743", "18/2/2000", false, "18TCLC-DT1", "url1", "url2", true,"3", "1", "1"));

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