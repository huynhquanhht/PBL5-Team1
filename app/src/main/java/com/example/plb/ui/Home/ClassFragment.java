package com.example.plb.ui.Home;

import android.content.Context;
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

    private EditText mNameEditText;
    private EditText mCodeClassEditText;
    private EditText mTimeStart;
    private EditText mTimeEnd;
    private EditText mRoom;
    private Button mCancelButton;
    private Button mApplyButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof NoticeDialogListener) {
            mNoticeDialogListener = (NoticeDialogListener) context;
        }

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
        mNameEditText = view.findViewById(R.id.nameEditText);
        mCodeClassEditText = view.findViewById(R.id.codeClassEditText);
        mTimeStart = view.findViewById(R.id.timeStartEditText);
        mTimeEnd = view.findViewById(R.id.timeEndEditText);
        mRoom = view.findViewById(R.id.roomEditText);

        mCancelButton.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        mApplyButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString().trim();
            String code = mCodeClassEditText.getText().toString().trim();
            String timestart = mTimeStart.getText().toString().trim();
            String timeend = mTimeEnd.getText().toString().trim();
            String room = mRoom.getText().toString().trim();

            if (name.isEmpty() || code.isEmpty() || timestart.isEmpty()
            ||timeend.isEmpty() || room.isEmpty()) {
                Toast.makeText(getActivity(), "Please Enter Data!!", Toast.LENGTH_SHORT).show();
            } else {
                mNoticeDialogListener.applyFile(name, code, timestart, timeend, room);
                getDialog().dismiss();
            }


        });

    }

    public interface NoticeDialogListener {
        void applyFile(String name, String code, String timeStart, String timeend, String room);
    }

}
