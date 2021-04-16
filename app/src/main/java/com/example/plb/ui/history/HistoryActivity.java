package com.example.plb.ui.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.plb.R;
import com.example.plb.model.History;
import com.example.plb.ui.Home.TypeClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<History> mHistoryList = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.historyRecyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter(mHistoryList);
        mRecyclerView.setAdapter(mHistoryAdapter);

        mHistoryList.add(new History("1", "18/2/2021 8:21", "18TCLC_DT1", "E303", 32, 2));
        mHistoryList.add(new History("1", "18/2/2021 8:21", "18TCLC_DT1", "E303", 32, 2));
        mHistoryList.add(new History("1", "18/2/2021 8:21", "18TCLC_DT1", "E303", 32, 2));
        mHistoryList.add(new History("1", "18/2/2021 8:21", "18TCLC_DT1", "E303", 32, 2));

        mHistoryAdapter.notifyDataSetChanged();
    }
}