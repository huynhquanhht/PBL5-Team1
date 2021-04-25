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

public class ClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Schedule> mScheduleList = new ArrayList<>();

    private OnClickListener mOnClickListener;

    public ClassAdapter(List<Schedule> scheduleList) {
        mScheduleList = scheduleList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item_recyclerview, parent, false);
        return new ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ClassHolder) holder).onBind(mScheduleList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    public interface OnClickListener {
        void onClick(Schedule classRoom, int position);
    }

    class ClassHolder extends RecyclerView.ViewHolder {

        private TextView serialTextView;
        private TextView idClassTextView;
        private TextView nameClassTextView;
        private TextView attendTextView;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Schedule schedule, int position) {
            serialTextView = itemView.findViewById(R.id.serialTextView);
            idClassTextView = itemView.findViewById(R.id.idClassTextView);
            nameClassTextView = itemView.findViewById(R.id.nameClassTextView);
            attendTextView = itemView.findViewById(R.id.attendTextView);

            serialTextView.setText(++position + "");
            idClassTextView.setText(schedule.getId());
            nameClassTextView.setText(schedule.getSubject());

            attendTextView.setOnClickListener(v -> mOnClickListener.onClick(schedule, getAdapterPosition()));

        }

    }
}
