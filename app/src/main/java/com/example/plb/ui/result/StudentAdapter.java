package com.example.plb.ui.result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
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

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gr_student_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mStudentList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public interface OnClickListener {
        void onClick(Student student, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAvatarImageView;
        private TextView mNameTextView;
        private TextView mClassTextView;
        private CheckBox mStatusCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(Student student, int position) {
            mAvatarImageView = itemView.findViewById(R.id.avatarImageView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            mClassTextView = itemView.findViewById(R.id.classTextView);
            mStatusCheckbox = itemView.findViewById(R.id.statusCheckbox);

            mNameTextView.setText(student.getName());
            mClassTextView.setText(student.getBaseClass());
            mStatusCheckbox.setChecked(student.isStatus());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(student, position);
                }
            });

        }
    }
}
