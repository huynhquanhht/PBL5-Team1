package com.example.plb.ui.schedule;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        ClassRoom classRoom = new ClassRoom("1813", "Giai Tich");
        ClassRoom classRoom1 = new ClassRoom("1814", "Giai Tich");
        ClassRoom classRoom2 = new ClassRoom("1813B", "Giai Tich");
        ClassRoom classRoom3 = new ClassRoom("1814B", "Giai Tich");

        mClassRoomList.add(classRoom);
        mClassRoomList.add(classRoom1);
        mClassRoomList.add(classRoom2);
        mClassRoomList.add(classRoom3);

        mScheduleAdapter.notifyDataSetChanged();

    }

}