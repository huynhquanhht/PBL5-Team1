package com.example.plb.ui.classroom;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.model.Student;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView serialTextView;
        private TextView idTextView;
        private TextView nameTextView;


        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Student student, int position) {

        }
    }

}
