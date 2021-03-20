package com.example.plb.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.plb.R;
import com.example.plb.model.Class;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ScheduleAdapter mScheduleAdapter;
    private List<Class> mClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        init();

    }

    private void init() {
        mRecyclerView = findViewById(R.id.scheduleRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mScheduleAdapter = new ScheduleAdapter(mClassList);
        mRecyclerView.setAdapter(mScheduleAdapter);

        fakeData();
    }

    public void fakeData() {
        Class class1 = new Class("Toan", "7h - 8h", "1");
        Class class2 = new Class("Ly", "8h -9h", "2");
        Class class3 = new Class("Hoa", "9h - 10h", "3");
        Class class4 = new Class("Anh", "10h - 11h", "4");
        Class class5 = new Class("Van", "11h - 12h", "5");

        mClassList.add(class1);
        mClassList.add(class2);
        mClassList.add(class3);
        mClassList.add(class4);
        mClassList.add(class5);

        mScheduleAdapter.notifyDataSetChanged();

    }

}