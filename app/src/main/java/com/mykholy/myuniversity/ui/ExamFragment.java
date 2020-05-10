package com.mykholy.myuniversity.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewExam;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.ExamAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.ui.dialog.SortDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class ExamFragment extends Fragment {
    RecyclerView ExamFragment_rv;
    List<Exam> examList;
    ExamAdapter adapter;
    FloatingActionButton ExamFragment_fab;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";
    private static final String ARG_COURSE = "course";
    private static final String ARG_TYPE_EXAMS = "type_exams";


    // TODO: Rename and change types of parameters
    private Course mCourse;
    private String mType_exmas;
    private int mId;

    private OnFragmentInteractionListener mListener;

    public ExamFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(Course course, String type_exam) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        args.putString(ARG_TYPE_EXAMS, type_exam);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_COURSE);
            mType_exmas = getArguments().getString(ARG_TYPE_EXAMS);
//            mId = getArguments().getInt(ARG_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);

        if (mType_exmas.equals("unsolved"))
            setDataUnSolvedExam();
        else
            setDataSolvedExam();


        ExamFragment_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialogFragment fragment = SortDialogFragment.newInstance(mType_exmas);
                fragment.show(getActivity().getSupportFragmentManager(), null);
            }
        });
    }

    private void setUi(View view) {
        ExamFragment_rv = view.findViewById(R.id.ExamFragment_rv);
        ExamFragment_fab = view.findViewById(R.id.ExamFragment_fab);
    }

    private void setDataUnSolvedExam() {

        loadDataUnSolvedExam();
        adapter = new ExamAdapter(getActivity(), mType_exmas, examList, new OnRecyclerViewExam() {
            @Override
            public void OnClickItemListener(Exam exam, String exam_type) {
                mListener.onFragmentInteraction(exam,exam_type);

            }
        });
        ExamFragment_rv.setAdapter(adapter);

        ExamFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void setDataSolvedExam() {

        loadDataSolvedExam();
        adapter = new ExamAdapter(getActivity(), mType_exmas, examList, new OnRecyclerViewExam() {
            @Override
            public void OnClickItemListener(Exam exam, String exam_type) {

                mListener.onFragmentInteraction(exam,exam_type);


            }
        });
        ExamFragment_rv.setAdapter(adapter);

        ExamFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void loadDataUnSolvedExam() {
        examList = new ArrayList<>();
        examList.add(new Exam(1, "Exam 1", "2020-04-10 17:50:00", "2020-04-11 13:59:00", 30, 3, 4, "2020-04-18", "finished",30));
        examList.add(new Exam(2, "Exam 2", "2020-04-11 08:50:00", "2020-04-12 10:00:00", 30, 3, 4, "2020-04-18", "pending",40));
        examList.add(new Exam(3, "Exam 3", "2020-04-12 20:50:00", "2020-04-13 08:59:00", 30, 3, 4, "2020-04-18", "finished",50));
        examList.add(new Exam(4, "Exam 4", "2020-04-13 17:50:00", "2020-04-14 09:59:00", 30, 3, 4, "2020-04-18", "finished",40));
        examList.add(new Exam(5, "Exam 5", "2020-04-14 02:50:00", "2020-04-15 17:59:00", 30, 3, 4, "2020-04-18", "active",20));
        examList.add(new Exam(6, "Exam 6", "2020-04-15 17:50:00", "2020-04-16 06:59:00", 30, 3, 4, "2020-04-18", "active",30));
        examList.add(new Exam(7, "Exam 7", "2020-04-16 09:00:00", "2020-04-17 12:59:00", 30, 3, 4, "2020-04-18", "active",40));
        examList.add(new Exam(8, "Exam 8", "2020-04-17 12:00:00", "2020-04-18 00:59:00", 30, 3, 4, "2020-04-18", "finished",50));
        examList.add(new Exam(9, "Exam 9", "2020-04-18 00:00:00", "2020-04-19 11:59:00", 30, 3, 4, "2020-04-18", "finished",20));
        examList.add(new Exam(10, "Exam 10", "2020-04-19 17:50:00", "2020-04-20 17:59:00", 30, 3, 4, "2020-04-18", "pending",10));

    }

    private void loadDataSolvedExam() {
        examList = new ArrayList<>();
        examList.add(new Exam(1, 4, 15, "00:01:20", "Exam 1", "2020-04-10 17:50:00", "2020-04-11 13:59:00", 30, 3, 4, "2020-04-18", "finished", 30));
        examList.add(new Exam(2, 4, 20, "00:10:15", "Exam 2", "2020-04-11 08:50:00", "2020-04-12 10:00:00", 30, 3, 4, "2020-04-18", "pending", 35));
        examList.add(new Exam(3, 4, 10, "00:20:00", "Exam 3", "2020-04-12 20:50:00", "2020-04-13 08:59:00", 30, 3, 4, "2020-04-18", "finished", 40));
        examList.add(new Exam(4, 4, 30, "00:05:17", "Exam 4", "2020-04-13 17:50:00", "2020-04-14 09:59:00", 30, 3, 4, "2020-04-18", "finished", 20));
        examList.add(new Exam(5, 4, 17, "00:18:12", "Exam 5", "2020-04-14 02:50:00", "2020-04-15 17:59:00", 30, 3, 4, "2020-04-18", "active", 20));
        examList.add(new Exam(6, 4, 19, "00:19:20", "Exam 6", "2020-04-15 17:50:00", "2020-04-16 06:59:00", 30, 3, 4, "2020-04-18", "active", 20));
        examList.add(new Exam(7, 4, 12, "00:17:20", "Exam 7", "2020-04-16 09:00:00", "2020-04-17 12:59:00", 30, 3, 4, "2020-04-18", "active", 30));
        examList.add(new Exam(8, 4, 5, "00:18:20", "Exam 8", "2020-04-17 12:00:00", "2020-04-18 00:59:00", 30, 3, 4, "2020-04-18", "finished", 40));
        examList.add(new Exam(9, 4, 9, "00:19:20", "Exam 9", "2020-04-18 00:00:00", "2020-04-19 11:59:00", 30, 3, 4, "2020-04-18", "finished", 20));
        examList.add(new Exam(10, 4, 20, "00:07:20", "Exam 10", "2020-04-19 17:50:00", "2020-04-20 17:59:00", 30, 3, 4, "2020-04-18", "pending", 20));

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
        void onFragmentInteraction(Exam exam,String type_exam);
    }
}
