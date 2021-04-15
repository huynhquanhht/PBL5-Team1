package com.example.plb.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.plb.R;
import com.example.plb.model.ClassRoom;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ScheduleAdapter mScheduleAdapter;
    private List<ClassRoom> mClassRoomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        init();

    }

    private void init() {
        mRecyclerView = findViewById(R.id.scheduleRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mScheduleAdapter = new ScheduleAdapter(mClassRoomList);
        mRecyclerView.setAdapter(mScheduleAdapter);

        fakeData();
    }

    public void fakeData() {
        ClassRoom classRoom1 = new ClassRoom("Toan", "7h - 8h", "1", 32);
        ClassRoom classRoom2 = new ClassRoom("Ly", "8h -9h", "2", 32);
        ClassRoom classRoom3 = new ClassRoom("Hoa", "9h - 10h", "3", 32);
        ClassRoom classRoom4 = new ClassRoom("Anh", "10h - 11h", "4", 32);
        ClassRoom classRoom5 = new ClassRoom("Van", "11h - 12h", "5", 32);

        mClassRoomList.add(classRoom1);
        mClassRoomList.add(classRoom2);
        mClassRoomList.add(classRoom3);
        mClassRoomList.add(classRoom4);
        mClassRoomList.add(classRoom5);

        mScheduleAdapter.notifyDataSetChanged();

    }

}