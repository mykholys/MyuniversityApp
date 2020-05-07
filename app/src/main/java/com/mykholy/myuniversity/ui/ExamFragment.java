package com.mykholy.myuniversity.ui;

import android.content.Context;
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

import com.mykholy.myuniversity.MyInterface.OnRecyclerViewExam;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.ExamAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;

import java.util.ArrayList;
import java.util.List;


public class ExamFragment extends Fragment {
    RecyclerView ExamFragment_rv;
    List<Exam> examList;
   ExamAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";
    private static final String ARG_COURSE = "course";




    // TODO: Rename and change types of parameters
    private Course mCourse;
    private int mId;

    private OnFragmentInteractionListener mListener;

    public ExamFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(Course course) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
//        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_COURSE);
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
        ExamFragment_rv = view.findViewById(R.id.ExamFragment_rv);
        setData();
    }

    private void setData() {

        loadData();
        adapter = new ExamAdapter(getActivity(), examList, new OnRecyclerViewExam() {
            @Override
            public void OnClickItemListener(Exam exam) {

                Log.i("exam:", exam.getEId()+ "\n" + exam.getTitle() + "\n" + "\n" + exam.getStartDate() + "\n" + "\n" + exam.getEndDate() + "\n"  + "\n" + exam.getTimer()+ "\n" + exam.getStatus()+ "\n=============");
//                mListener.onFragmentInteraction(lectures);
            }
        });
        ExamFragment_rv.setAdapter(adapter);

        ExamFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void loadData() {
        examList = new ArrayList<>();
        examList.add(new Exam(1, "Exam 1", "2020-04-10 17:50:00","2020-04-11 13:59:00",30,3,4,"2020-04-18","finished"));
        examList.add(new Exam(2, "Exam 2", "2020-04-11 08:50:00","2020-04-12 10:00:00",30,3,4,"2020-04-18","pending"));
        examList.add(new Exam(3, "Exam 3", "2020-04-12 20:50:00","2020-04-13 08:59:00",30,3,4,"2020-04-18","finished"));
        examList.add(new Exam(4, "Exam 4", "2020-04-13 17:50:00","2020-04-14 09:59:00",30,3,4,"2020-04-18","finished"));
        examList.add(new Exam(5, "Exam 5", "2020-04-14 02:50:00","2020-04-15 17:59:00",30,3,4,"2020-04-18","active"));
        examList.add(new Exam(6, "Exam 6", "2020-04-15 17:50:00","2020-04-16 06:59:00",30,3,4,"2020-04-18","active"));
        examList.add(new Exam(7, "Exam 7", "2020-04-16 09:00:00","2020-04-17 12:59:00",30,3,4,"2020-04-18","active"));
        examList.add(new Exam(8, "Exam 8", "2020-04-17 12:00:00","2020-04-18 00:59:00",30,3,4,"2020-04-18","finished"));
        examList.add(new Exam(9, "Exam 9", "2020-04-18 00:00:00","2020-04-19 11:59:00",30,3,4,"2020-04-18","finished"));
        examList.add(new Exam(10, "Exam 10", "2020-04-19 17:50:00","2020-04-20 17:59:00",30,3,4,"2020-04-18","pending"));

    }




//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
