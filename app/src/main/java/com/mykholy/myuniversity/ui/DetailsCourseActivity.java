package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.google.android.material.tabs.TabLayout;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.MyPagerAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.MyTab;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.ui.dialog.SortDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsCourseActivity extends AppCompatActivity implements SortDialogFragment.OnFragmentInteractionListener, ExamFragment.OnFragmentInteractionListener {
    Toolbar DetailsCourseActivity_toolbar;
    TabLayout DetailsCourseActivity_tab_layout;
    ViewPager main_pager;
    Course course;
    private String type_exams = "unsolved";
    MyPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_details_course);
        setUi();
        setToolbar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            course = (Course) getIntent().getSerializableExtra("course"); //Obtaining data
            Log.i("course", course.getCName());
            setTitle(course.getCName());
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }


        DetailsCourseActivity_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setPager();


    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setUi() {
        DetailsCourseActivity_toolbar = findViewById(R.id.DetailsCourseActivity_toolbar);
        DetailsCourseActivity_tab_layout = findViewById(R.id.DetailsCourseActivity_tab_layout);
        main_pager = findViewById(R.id.main_pager);

    }

    private void setToolbar() {
        setSupportActionBar(DetailsCourseActivity_toolbar);
    }

    private void setPager() {

        DetailsCourseActivity_tab_layout.setupWithViewPager(main_pager);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        adapter.addTab(new MyTab(getString(R.string.Lectures), LectureFragment.newInstance(course)));
        adapter.addTab(new MyTab(getString(R.string.Exams), ExamFragment.newInstance(course, type_exams)));
        main_pager.setAdapter(adapter);


    }


    @Override
    public void onFragmentInteraction(String type_exam) {
        type_exams = type_exam;
        adapter = null;
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        adapter.addTab(new MyTab(getString(R.string.Lectures), LectureFragment.newInstance(course)));
        adapter.addTab(new MyTab(getString(R.string.Exams), ExamFragment.newInstance(course, type_exams)));
        main_pager.setAdapter(adapter);
        DetailsCourseActivity_tab_layout.selectTab(DetailsCourseActivity_tab_layout.getTabAt(1));
    }

    @Override
    public void onFragmentInteraction(Exam exam, String type_exam) {
        if (type_exam.equals("unsolved")) {
            Intent SolveExamIntent = new Intent(this, SolveExamActivity.class);
            SolveExamIntent.putExtra("exam", exam);
            SolveExamIntent.putExtra("question", (Serializable) getAllQuestion());
            startActivity(SolveExamIntent);
        }

    }

    private List<Question> getAllQuestion() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1,"What is 5+2","10","22","7","11","c",1,4,3));
        questions.add(new Question(1,"What is 5-2","3","22","7","11","a",1,4,3));
        questions.add(new Question(1,"What is 5+10","10","15","7","11","b",1,4,3));
        questions.add(new Question(1,"What is 8+2","10","22","7","11","a",1,4,3));
        questions.add(new Question(1,"What is 5*3","10","22","7","15","d",1,4,3));

        return questions;
    }
}
