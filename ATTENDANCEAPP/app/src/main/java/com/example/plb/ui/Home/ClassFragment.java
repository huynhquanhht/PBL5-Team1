package com.example.plb.ui.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.plb.R;

import org.jetbrains.annotations.NotNull;

public class ClassFragment extends DialogFragment {

    private NoticeDialogListener mNoticeDialogListener;

    private EditText mTimeStart;
    private EditText mTimeEnd;
    private EditText mRoom;
    private Button mCancelButton;
    private Button mApplyButton;



    public void setNoticeDialogListener(NoticeDialogListener noticeDialogListener) {
        mNoticeDialogListener = noticeDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.classfragmentlayout, container);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCancelButton = view.findViewById(R.id.cancelButton);
        mApplyButton = view.findViewById(R.id.applyButton);
        mTimeStart = view.findViewById(R.id.timeStartEditText);
        mTimeEnd = view.findViewById(R.id.timeEndEditText);
        mRoom = view.findViewById(R.id.roomEditText);

        mCancelButton.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        mApplyButton.setOnClickListener(v -> {
            String timestart = mTimeStart.getText().toString().trim();
            String timeend = mTimeEnd.getText().toString().trim();
            String room = mRoom.getText().toString().trim();

            if (timestart.isEmpty() || timeend.isEmpty() || room.isEmpty()) {
                Toast.makeText(getActivity(), "Please Enter Data!!", Toast.LENGTH_SHORT).show();
            } else {
                mNoticeDialogListener.onClick(timestart, timeend, room);
                getDialog().dismiss();
            }
        });

    }

    @Override
    public void onCancel(@NonNull @NotNull DialogInterface dialog) {
        super.onCancel(dialog);

        mNoticeDialogListener.resetSchedule();

    }

    public interface NoticeDialogListener {
        void applyFile(String timeStart, String timeend, String room);
        void onClick(String timeStart, String timeEnd, String room);
        void resetSchedule();
    }


}
