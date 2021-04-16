package com.example.plb.ui.history;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.ClassRoom;
import com.example.plb.model.History;
import com.example.plb.ui.Home.TypeClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<History> mHistories = new ArrayList<>();

    public HistoryAdapter(List<History> histories) {
        mHistories = histories;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mHistories.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeTextView, mClassTextView, mTotalTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(History history, int position) {

            mTimeTextView = itemView.findViewById(R.id.timeTextView);
            mClassTextView = itemView.findViewById(R.id.baseClassTextView);
            mTotalTextView = itemView.findViewById(R.id.totalTextView);

            mTotalTextView.setText(history.getTotal() + "");
            mClassTextView.setText(history.getBaseclass());
            mTimeTextView.setText(history.getTime());
        }
    }


}
