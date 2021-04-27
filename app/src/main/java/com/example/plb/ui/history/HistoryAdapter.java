package com.example.plb.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Attendance;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Attendance> mAttendacnces = new ArrayList<>();

    public HistoryAdapter(List<Attendance> attendacnces) {
        mAttendacnces = attendacnces;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mSerialTextView, mClassTextView, mTimeTextView, mTotalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Attendance history, int position) {

            mSerialTextView = itemView.findViewById(R.id.serialTextView);
            mClassTextView = itemView.findViewById(R.id.idClassTextView);
            mTimeTextView = itemView.findViewById(R.id.timeTextView);
            mTotalTextView = itemView.findViewById(R.id.absentTextView);

            mSerialTextView.setText(position + 1 + "");
            mClassTextView.setText(history.getIdschedule());
            mTimeTextView.setText(history.getTimeattend());
            mTotalTextView.setText(history.getAbsent());
        }
    }


}
