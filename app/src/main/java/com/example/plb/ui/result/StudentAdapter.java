package com.example.plb.ui.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plb.R;
import com.example.plb.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> mStudentList = new ArrayList<>();
    private Context mContext;
    private OnClickListener mOnClickListener;
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked() {
        this.checked = !checked;
    }

    public StudentAdapter(List<Student> studentList, Context context) {
        mStudentList = studentList;
        mContext = context;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gr_result_student, parent, false);
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

        void changeStatus(Student student, boolean status);
    }

    public void filterList(List<Student> filterllist) {
        mStudentList = filterllist;
        notifyDataSetChanged();
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
            mStatusCheckbox = itemView.findViewById(R.id.statusCheckBox);

            mNameTextView.setText(student.getName());
            mClassTextView.setText(student.getBaseClass());



            if (student.getStatus() == 0) {
                mStatusCheckbox.setChecked(true);
            } else {
                mStatusCheckbox.setChecked(false);
            }

            Glide.with(mContext).load(student.getUrlAttend())
                    .placeholder(R.drawable.loading)
                    .into(mAvatarImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(student, position);
                }
            });

            mStatusCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean status = mStatusCheckbox.isChecked();

                    if (status) {
                        student.setStatus(0);
                        Toast.makeText(mContext, "0", Toast.LENGTH_SHORT).show();
                    } else {
                        student.setStatus(1);
                        Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            if (!checked) {
                mStatusCheckbox.setEnabled(false);
            } else {
                mStatusCheckbox.setEnabled(true);
            }
        }
    }

    public List<Student> getStudentList() {
        return mStudentList;
    }

    public int getTotal(){
        int count = 0;
        for(Student student : mStudentList) {
            if (student.getStatus() == 1) {
                count++;
            }
        }
        return count;
    }
}
