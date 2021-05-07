package com.example.plb.ui.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Student;
import com.example.plb.ui.Home.ScheduleAdapter;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Student> mStudentList;
    private Context mContext;
    private OnClickListener mOnClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public StudentAdapter(List<Student> studentList, Context context) {
        mStudentList = studentList;
        mContext = context;
    }

    public void filterList(List<Student> filterllist) {
        mStudentList = filterllist;
        notifyDataSetChanged();
    }

    public List<Student> getStudentList() {
        return mStudentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gr_student_recyclerview, parent, false);
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

        private ImageView avatarImageView;
        private TextView nameTextView;
        private TextView codeTextView;
        private TextView absentTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            codeTextView = itemView.findViewById(R.id.classTextView);
            absentTextView = itemView.findViewById(R.id.totalAbsentTextView);
        }

        public void onBind(Student student, int position) {

            Glide.with(mContext).load(student.getUrlAvatar())
                    .placeholder(R.drawable.loading)
                    .into(avatarImageView);

            nameTextView.setText(student.getName());
            codeTextView.setText(student.getCodeStudent());
            absentTextView.setText("Total absent: " + student.getTotalAbsent());

        }
    }

    public interface OnClickListener {
        void onClick(Student student, int position);
    }

}
