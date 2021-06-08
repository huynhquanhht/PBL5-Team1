package com.example.plb.ui.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Attendance;
import com.example.plb.model.StudentAttend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StudentAttend> mAttendacnces = new ArrayList<>();
    private OnClickListener mOnClickListener;

    public HistoryAdapter(List<StudentAttend> attendacnces) {
        mAttendacnces = attendacnces;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gr_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mAttendacnces.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mAttendacnces.size();
    }

    public interface OnClickListener {
        void onClick(Attendance attendance, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView, timeTextView, classTextView, codeTextView, absentTextView, totalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            classTextView = itemView.findViewById(R.id.subjectTextView);
            codeTextView = itemView.findViewById(R.id.codeClassTextView);
            absentTextView = itemView.findViewById(R.id.totalAbsentTextView);
            totalTextView = itemView.findViewById(R.id.totalStudentTextView);
        }

        public void onBind(StudentAttend history, int position) {

            SimpleDateFormat formatter5 = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy");
            try {
                Date date = formatter5.parse(history.getTimeattend());
                String Date = date.getDate() + ":" + (date.getMonth() + 1) + ":" + (date.getYear() + 1900);
                String time = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

                dateTextView.setText(Date);
                timeTextView.setText(time);
                codeTextView.setText(history.getBaseclass());
                absentTextView.setText("Room: " + history.getRoom());
                classTextView.setText(history.getSubject());

                if (history.getStatus().equals("0")) {
                    totalTextView.setText("Status: Vang");
                } else {
                    totalTextView.setText("Status: Co mat");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

}
