package com.example.plb.ui.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleOverallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Schedule> mScheduleList = new ArrayList<>();
    private OnClickListener mOnClickListener;

    public ScheduleOverallAdapter(List<Schedule> scheduleList) {
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
        private TextView room;
        private TextView total;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            timeStart = itemView.findViewById(R.id.timeStartTextView);
            timeEnd = itemView.findViewById(R.id.timeEndTextView);
            subject = itemView.findViewById(R.id.subjectTextView);
            codeSubject = itemView.findViewById(R.id.codeClassTextView);
            total = itemView.findViewById(R.id.totalTextView);
            room = itemView.findViewById(R.id.roomTextView);
        }

        public void onBind(Schedule schedule, int position) {

            String serial = schedule.getSerial();

            if (serial.equals("1")) {
                timeStart.setText("Sunday");
            } else if (serial.equals("2")) {
                timeStart.setText("Monday");
            } else if (serial.equals("3")) {
                timeStart.setText("Tuesday");
            } else if (serial.equals("4")) {
                timeStart.setText("Wednesday");
            } else if (serial.equals("5")) {
                timeStart.setText("Thursday");
            } else if (serial.equals("6")) {
                timeStart.setText("Friday");
            } else if (serial.equals("7")) {
                timeStart.setText("Saturday");
            }

            timeEnd.setVisibility(View.GONE);
            subject.setText(schedule.getSubject());
            codeSubject.setText(schedule.getId());
            room.setText(schedule.getRoom());
            total.setText("Total: " + schedule.getTotal());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(schedule, position);
                }
            });
        }
    }
}
