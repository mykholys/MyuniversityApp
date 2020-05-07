package com.mykholy.myuniversity.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewLecture;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.CourseAdapter;
import com.mykholy.myuniversity.adapter.LectureAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Lecture;

import java.util.ArrayList;
import java.util.List;


public class LectureFragment extends Fragment {

    RecyclerView LectureFragment_rv;
    List<Lecture> lectures;
    LectureAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_ID = "id";
    private static final String ARG_COURSE = "course";


    // TODO: Rename and change types of parameters
    private Course mCourse;
    private int mId;


    private OnFragmentInteractionListener mListener;

    public LectureFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LectureFragment newInstance(Course course) {
        LectureFragment fragment = new LectureFragment();
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
        return inflater.inflate(R.layout.fragment_lecture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LectureFragment_rv = view.findViewById(R.id.LectureFragment_rv);
        setData();
    }

    private void setData() {

        loadData();
        adapter = new LectureAdapter(getActivity(), lectures, new OnRecyclerViewLecture() {
            @Override
            public void OnClickItemListener(Lecture lecture) {

                Log.i("lecture:", lecture.getLecId()+ "\n" + lecture.getName() + "\n" + "\n" + lecture.getFile() + "\n" + "\n" + lecture.getCId() + "\n"  + "\n" + lecture.getDId() + "\n" + lecture.getCreatedAt()+ "\n=============");
//                mListener.onFragmentInteraction(lectures);
            }
        });
        LectureFragment_rv.setAdapter(adapter);

        LectureFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void loadData() {
        lectures = new ArrayList<>();
        lectures.add(new Lecture(1, "Android", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(2, "Android2", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(3, "Android3", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(4, "Android4", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(5, "Android5", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(6, "Android6", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(7, "Android7", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(8, "Android8", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(9, "Android9", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(10, "Android10", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));
        lectures.add(new Lecture(11, "Android11", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png",1,2,"2020-01-02"));

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
