package com.example.plb.ui.Home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plb.R;
import com.example.plb.model.ClassRoom;
import com.example.plb.model.Student;

import java.util.ArrayList;
import java.util.List;

public class TypeClassAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ClassRoom> mClassRoomList = new ArrayList<>();
    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public TypeClassAdapter(List<ClassRoom> classRoomList) {
        mClassRoomList = classRoomList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gr_choose_type_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mClassRoomList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mClassRoomList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mLinearLayout;
        TextView mClassTextView;
//        ImageView mChooseImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(ClassRoom c, int position) {
            mLinearLayout = itemView.findViewById(R.id.bgLinearLayout);
//            mChooseImageView = itemView.findViewById(R.id.chooseImageView);
            mClassTextView = itemView.findViewById(R.id.classTextView);

            mClassTextView.setText(c.getName());

            if (position == 0) {
//                 mChooseImageView.setVisibility(View.INVISIBLE);
                 mLinearLayout.setBackgroundColor(Color.parseColor("#53C1F3"));
                 mClassTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
            }

        }
    }

    public interface OnClickListener {
        void onClick(Student student, int position);
    }

}
