package com.example.plb.ui.classroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Student> mStudentList;
    private OnClickListener mOnClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public StudentAdapter(List<Student> studentList) {
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((StudentViewHolder) holder).onBind(mStudentList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView serialTextView;
        private TextView idTextView;
        private TextView nameTextView;
        private TextView totalAbsentTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            serialTextView = itemView.findViewById(R.id.serialTextView);
            idTextView = itemView.findViewById(R.id.idStudentTextView);
            nameTextView = itemView.findViewById(R.id.nameStudentTextView);
            totalAbsentTextView = itemView.findViewById(R.id.totalAbsentTextView);
        }

        public void onBind(Student student, int position) {
            serialTextView.setText((position + 1) + "");
            idTextView.setText(student.getCodeStudent());
            nameTextView.setText(student.getName());
            totalAbsentTextView.setText(student.getTotalAbsent());
        }
    }

    public interface OnClickListener {
        void onClick(Student student, int position);
    }

}
