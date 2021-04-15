package com.example.plb.ui.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.ClassRoom;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ClassRoom> mClassRooms = new ArrayList<>();

    public ScheduleAdapter(List<ClassRoom> classRooms) {
        mClassRooms = classRooms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_recyclerview, parent,false);
        return new ScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ScheduleHolder) holder).onBind(mClassRooms.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mClassRooms.size();
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {

        private TextView stt;
        private TextView subject;
        private TextView time;

        public ScheduleHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(ClassRoom c, int position) {
            stt = itemView.findViewById(R.id.sttTextView);
            subject = itemView.findViewById(R.id.subjectsTextView);
            time = itemView.findViewById(R.id.timeTextView);

            stt.setText(String.valueOf(c.getCountStudent()));
            subject.setText(c.getName());
            time.setText(c.getTime());
        }
    }
}
