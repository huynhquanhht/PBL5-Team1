package com.example.plb.ui.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> mStudentList = new ArrayList<>();
    private OnClickListener mOnClickListener;


    public StudentAdapter(List<Student> studentList) {
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_detail, parent, false);
        return new ViewHolder(view);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mStudentList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView name;
        private TextView baseClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Student student, int position) {
            id = itemView.findViewById(R.id.mssvTextView);
            name = itemView.findViewById(R.id.nameTextView);
            baseClass = itemView.findViewById(R.id.baseClassTextView);

            id.setText(student.getId());
            name.setText(student.getName());
            baseClass.setText(student.getBaseClass());

            itemView.setOnClickListener(v -> mOnClickListener.onClick(student, position));


        }
    }

    public interface OnClickListener {
        String a = "";
        void onClick(Student student, int position);
    }

}
