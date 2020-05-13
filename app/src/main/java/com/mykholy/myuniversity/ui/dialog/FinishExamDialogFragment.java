package com.mykholy.myuniversity.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mykholy.myuniversity.R;

import java.util.Locale;


public class FinishExamDialogFragment extends DialogFragment {
    TextView FinishExamDialogFragment_tv_title, FinishExamDialogFragment_tv_result;
    androidx.appcompat.widget.AppCompatButton FinishExamDialogFragment_btn_done;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SCORE = "score";
    private static final String ARG_QUESTION_TOTAL = "question_total";


    // TODO: Rename and change types of parameters
    private int mScore;
    private int mQuestionTotal;

    private OnFragmentInteractionListener mListener;

    public FinishExamDialogFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FinishExamDialogFragment newInstance(int score, int questionCounterTotal) {
        FinishExamDialogFragment fragment = new FinishExamDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        args.putInt(ARG_QUESTION_TOTAL, questionCounterTotal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScore = getArguments().getInt(ARG_SCORE);
            mQuestionTotal = getArguments().getInt(ARG_QUESTION_TOTAL);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }
        return inflater.inflate(R.layout.fragment_finish_exam_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);
        Log.i("Score_mS", String.valueOf(mScore));
        Log.i("Score_mQ", String.valueOf(mQuestionTotal));
        if (mScore < mQuestionTotal/2) setFail();
        else
            setPass();

        FinishExamDialogFragment_btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction();
                dismiss();
            }
        });
    }

    private void setUi(View view) {
        FinishExamDialogFragment_tv_title = view.findViewById(R.id.FinishExamDialogFragment_tv_title);
        FinishExamDialogFragment_tv_result = view.findViewById(R.id.FinishExamDialogFragment_tv_result);
        FinishExamDialogFragment_btn_done = view.findViewById(R.id.FinishExamDialogFragment_btn_done);
    }

    private void setFail() {
        FinishExamDialogFragment_tv_title.setText(getString(R.string.you_failed));
        FinishExamDialogFragment_tv_result.setText(String.format(Locale.getDefault(), "%s %d/%d", getString(R.string.your_result), mScore, mQuestionTotal));
    }

    private void setPass() {
        FinishExamDialogFragment_tv_title.setText(getString(R.string.you_passed));
        FinishExamDialogFragment_tv_result.setText(String.format(Locale.getDefault(), "%s %d/%d", getString(R.string.your_result), mScore, mQuestionTotal));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
