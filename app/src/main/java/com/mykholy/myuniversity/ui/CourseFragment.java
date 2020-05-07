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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.CourseAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.utilities.Constants;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends Fragment {
    ImageView CourseFragment_iv_profile, CourseFragment_iv_bg;
    TextView CourseFragment_tv_name, CourseFragment_tv_academic_year, CourseFragment_tv_department;
    RecyclerView CourseFragment_rv;
    List<Course> courses;
    CourseAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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
        setData();

    }

    private void setUi(View view) {
        CourseFragment_iv_bg = view.findViewById(R.id.CourseFragment_iv_bg);
        CourseFragment_iv_profile = view.findViewById(R.id.CourseFragment_iv_profile);
        CourseFragment_tv_name = view.findViewById(R.id.CourseFragment_tv_name);
        CourseFragment_tv_academic_year = view.findViewById(R.id.CourseFragment_tv_academic_year);
        CourseFragment_tv_department = view.findViewById(R.id.CourseFragment_tv_department);
        CourseFragment_rv = view.findViewById(R.id.CourseFragment_rv);
        if (Constants.getSPreferences(getActivity()).getLanguage().equals("ar"))
            setRTL();
        else
            setLTR();


    }

    private void setData() {
        CourseFragment_tv_academic_year.setText(String.format("%s:1", getString(R.string.academic_year)));
        CourseFragment_tv_department.setText(String.format("%s:CS", getString(R.string.department)));

        //setImageProfile
        Glide.with(this)
                .load("https://i.pinimg.com/736x/da/3b/4b/da3b4b2d4e6bcb5b4551b3de06ee93d2.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(CourseFragment_iv_profile);
        loadData();
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


    }

    private void setLTR() {

        CourseFragment_iv_bg.setImageResource(R.drawable.custom_bg_toolbar_course_ltr);

    }

    private void setRTL() {

        CourseFragment_iv_bg.setImageResource(R.drawable.custom_bg_toolbar_course_rtl);

    }

    private void loadData() {
        courses = new ArrayList<>();
        courses.add(new Course(1, "Android", "https://pngimg.com/uploads/android_logo/android_logo_PNG22.png"));
        courses.add(new Course(2, "Java", "https://www.edlibre.com/wp-content/uploads/java-2.jpg"));
        courses.add(new Course(2, "programming phpphpphpphpphpphpphpphp programmingphp phpphpphpphpphpphpphpphp programmingphp", "https://www.edlibre.com/wp-content/uploads/java-2.jpg"));
        courses.add(new Course(3, "Java fx", "https://cms-assets.tutsplus.com/uploads/users/861/posts/23835/preview_image/javafx.jpg"));
        courses.add(new Course(4, "Payton", "https://datawider.com/wp-content/uploads/2019/11/How-to-Learn-Python.jpg"));
        courses.add(new Course(5, "C++", "https://books-library.online/files/download-pdf-ebooks.org-03160255Bu0K4.jpg"));
        courses.add(new Course(6, "Mat lab", "https://www.electronicsforu.com/wp-contents/uploads/2016/04/967_MATLAB-logo.jpg"));
        courses.add(new Course(7, "Php programming", "https://php.traitech.net/images/logo.jpg"));
        courses.add(new Course(8, "MySql", "https://blog.dc.net.sa/wp-content/2019/03/mysqlhero.jpg"));
        courses.add(new Course(9, "HTML language", "https://1.bp.blogspot.com/-dlbb-GYg3mc/W1TQLJD7O5I/AAAAAAAAArQ/krwcOhlPdlA19Am4dTiXw2aouGh6V7n2gCPcBGAYYCw/s640/html.jpg"));
        courses.add(new Course(10, "CSS language", "https://html5hive.org/wp-content/uploads/2014/03/css-beginners-tutorial.jpg"));
        courses.add(new Course(11, "JavaScript language", "https://4.bp.blogspot.com/-PQHNOWFNS9o/XAkNsyPerCI/AAAAAAAALks/ONXxkKH3lRwskA3cfiqPa-cGKlt8u-l6wCLcBGAs/s1600/javascript.jpg"));
        courses.add(new Course(12, "Jquery language", "https://1stwebdesigner.com/wp-content/uploads/2016/07/jquery.jpg"));
        courses.add(new Course(12, "programming phpphpphpphpphpphpphpphp programmingphp phpphpphpphpphpphpphpphp programmingphp", "https://1stwebdesigner.com/wp-content/uploads/2016/07/jquery.jpg"));

    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
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
        void onFragmentInteraction(Course course);
    }
}
