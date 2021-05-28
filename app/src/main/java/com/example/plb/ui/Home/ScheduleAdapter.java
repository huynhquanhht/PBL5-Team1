package com.example.plb.ui.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Schedule;
import com.example.plb.model.Student;
import com.example.plb.ui.result.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Schedule> mScheduleList = new ArrayList<>();
    private OnClickListener mOnClickListener;

    public ScheduleAdapter(List<Schedule> scheduleList) {
        mScheduleList = scheduleList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(Schedule schedule, int position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_today, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ScheduleViewHolder) holder).onBind(mScheduleList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView timeStart;
        private TextView timeEnd;
        private TextView subject;
        private TextView codeSubject;
        private TextView totalTextView;
        private TextView room;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            timeStart = itemView.findViewById(R.id.timeStartTextView);
            timeEnd = itemView.findViewById(R.id.timeEndTextView);
            subject = itemView.findViewById(R.id.subjectTextView);
            codeSubject = itemView.findViewById(R.id.codeClassTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            room = itemView.findViewById(R.id.roomTextView);
        }

        public void onBind(Schedule schedule, int position) {

            timeStart.setText(schedule.getTimeStart());
            timeEnd.setText(schedule.getTimeEnd());
            subject.setText(schedule.getSubject());
            codeSubject.setText(schedule.getCodeclass());
            totalTextView.setText("Total: " + schedule.getTotal());
            room.setText(schedule.getRoom());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(schedule, position);
                }
            });
        }
    }
}
