package com.mykholy.myuniversity.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import com.mykholy.myuniversity.R;

public class SortDialogFragment extends androidx.fragment.app.DialogFragment {

    private androidx.appcompat.widget.AppCompatButton SortDialogFragment_btn_done;
    private RadioButton SortDialogFragment_rb_unsolved, SortDialogFragment_rb_solved;
    private String type_exams;

    private String mType_exmas;

    private static final String ARG_TYPE_EXAMS = "type_exams";

    private OnFragmentInteractionListener mListener;

    public SortDialogFragment() {
        // Required empty public constructor
    }


    public static SortDialogFragment newInstance(String type_exams) {
        SortDialogFragment fragment = new SortDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE_EXAMS, type_exams);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType_exmas = getArguments().getString(ARG_TYPE_EXAMS);
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
        return inflater.inflate(R.layout.fragment_sort_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUi(view);
        if (mType_exmas.equals("unsolved"))
            setUnSolved();
        else
            setSolved();

        SortDialogFragment_btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(type_exams);
                dismiss();
            }
        });
        SortDialogFragment_rb_unsolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUnSolved();
            }
        });
        SortDialogFragment_rb_solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSolved();
            }
        });

    }

    private void setUi(View view) {
        SortDialogFragment_btn_done = view.findViewById(R.id.SortDialogFragment_btn_done);
        SortDialogFragment_rb_unsolved = view.findViewById(R.id.SortDialogFragment_rb_unsolved);
        SortDialogFragment_rb_solved = view.findViewById(R.id.SortDialogFragment_rb_solved);
    }

    private void setUnSolved() {
        SortDialogFragment_rb_unsolved.setChecked(true);
        SortDialogFragment_rb_solved.setChecked(false);
        type_exams = "unsolved";

    }

    private void setSolved() {
        SortDialogFragment_rb_unsolved.setChecked(false);
        SortDialogFragment_rb_solved.setChecked(true);
        type_exams = "solved";
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String type_exam);
    }
}
