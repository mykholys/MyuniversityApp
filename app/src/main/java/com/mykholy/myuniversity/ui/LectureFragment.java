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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewLecture;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.CourseAdapter;
import com.mykholy.myuniversity.adapter.LectureAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Lecture;
import com.mykholy.myuniversity.utilities.Constants;

import java.util.ArrayList;
import java.util.List;


public class LectureFragment extends Fragment {

    private RecyclerView LectureFragment_rv;
    private List<Lecture> lectures;
    private LectureAdapter adapter;
    private API_Interface api_interface;
    private LoadingView loadingView;
    private TextView LectureFragment_tv_no_lectures;
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
        loadingView = view.findViewById(R.id.loadingView);
        LectureFragment_tv_no_lectures = view.findViewById(R.id.LectureFragment_tv_no_lectures);
        setApi();
        setData();
    }

    private void setData() {

        loadData();


    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }


    private void loadData() {
        lectures = new ArrayList<>();
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();


        Call<List<Lecture>> call = api_interface.getAllLectures(token, mCourse.getCId());
        call.enqueue(new Callback<List<Lecture>>() {
            @Override
            public void onResponse(@NonNull Call<List<Lecture>> call, @NonNull Response<List<Lecture>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        LectureFragment_tv_no_lectures.setVisibility(View.INVISIBLE);
                        lectures.addAll(response.body());
                        adapter = new LectureAdapter(getActivity(), lectures, new OnRecyclerViewLecture() {
                            @Override
                            public void OnClickItemListener(Lecture lecture) {

                                Log.i("lecture:", lecture.getLecId() + "\n" + lecture.getName() + "\n" + "\n" + lecture.getFile() + "\n" + "\n" + lecture.getCId() + "\n" + "\n" + lecture.getDId() + "\n" + lecture.getCreatedAt() + "\n=============");
                                               mListener.onFragmentInteraction(lecture);
                            }
                        });
                        LectureFragment_rv.setAdapter(adapter);

                        LectureFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else
                        LectureFragment_tv_no_lectures.setVisibility(View.VISIBLE);

                }
                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<List<Lecture>> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });
    }

        @Override
    public void onAttach(@NonNull Context context) {
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
        void onFragmentInteraction(Lecture lecture);
    }
}
