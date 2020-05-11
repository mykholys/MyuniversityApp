package com.mykholy.myuniversity.ui;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.CourseAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CourseFragment extends Fragment {
    private ImageView CourseFragment_iv_profile, CourseFragment_iv_bg;
    private TextView CourseFragment_tv_name, CourseFragment_tv_academic_year, CourseFragment_tv_department, CourseFragment_tv_no_lectures;
    private RecyclerView CourseFragment_rv;
    private LoadingView loadingView;
    private List<Course> courses;
    private CourseAdapter adapter;
    private API_Interface api_interface;


    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);
        setApi();
        setData();

    }

    private void setUi(View view) {
        CourseFragment_iv_bg = view.findViewById(R.id.CourseFragment_iv_bg);
        CourseFragment_iv_profile = view.findViewById(R.id.CourseFragment_iv_profile);
        CourseFragment_tv_name = view.findViewById(R.id.CourseFragment_tv_name);
        CourseFragment_tv_academic_year = view.findViewById(R.id.CourseFragment_tv_academic_year);
        CourseFragment_tv_department = view.findViewById(R.id.CourseFragment_tv_department);
        CourseFragment_rv = view.findViewById(R.id.CourseFragment_rv);
        CourseFragment_tv_no_lectures = view.findViewById(R.id.CourseFragment_tv_no_lectures);
        loadingView = view.findViewById(R.id.loadingView);
        if (Constants.getSPreferences(getActivity()).getLanguage().equals("ar"))
            setRTL();
        else
            setLTR();


    }

    private void setData() {
        String imageProfile = AppClient.BASE_URL + "public/images/Students/" + Constants.getSPreferences(getActivity()).getSTUDENT_IMAGE();
        Log.i("image", imageProfile);
        CourseFragment_tv_name.setText(Constants.getSPreferences(getActivity()).getSTUDENT_NAME());
        CourseFragment_tv_academic_year.setText(String.format(Locale.getDefault(), "%s: %d", getString(R.string.academic_year), Constants.getSPreferences(getActivity()).getSTUDENT_ACADEMIC_YEAR()));
        CourseFragment_tv_department.setText(String.format(Locale.getDefault(), "%s: %s", getString(R.string.department), Constants.getSPreferences(getActivity()).getSTUDENT_DEPT_NAME()));

        //setImageProfile
        Glide.with(this)
                .load(imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .into(CourseFragment_iv_profile);
        loadData();


    }

    private void setLTR() {

        CourseFragment_iv_bg.setImageResource(R.drawable.custom_bg_toolbar_course_ltr);

    }

    private void setRTL() {

        CourseFragment_iv_bg.setImageResource(R.drawable.custom_bg_toolbar_course_rtl);

    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }

    private void loadData() {
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();

        courses = new ArrayList<>();

        Call<List<Course>> call = api_interface.getAllCourses(token, Constants.getSPreferences(getActivity()).getSTUDENT_ACADEMIC_YEAR(), Constants.getSPreferences(getActivity()).getSTUDENT_DEPT_ID());
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(@NonNull Call<List<Course>> call, @NonNull Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        CourseFragment_tv_no_lectures.setVisibility(View.INVISIBLE);
                        courses.addAll(response.body());
                        adapter = new CourseAdapter(getActivity(), courses, new OnRecyclerViewCourse() {
                            @Override
                            public void OnClickItemListener(Course course) {
                                Log.i("course:", course.getCId() + "\n" + course.getCName() + "\n" + course.getCImage() + "\n=============");
                                mListener.onFragmentInteraction(course);

                            }
                        });
                        CourseFragment_rv.setAdapter(adapter);
                        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 2);
                        CourseFragment_rv.setLayoutManager(lm);
                    } else
                        CourseFragment_tv_no_lectures.setVisibility(View.VISIBLE);

                }
                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<List<Course>> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });

    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    //    @Override
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
        void onFragmentInteraction(Course course);
    }
}
