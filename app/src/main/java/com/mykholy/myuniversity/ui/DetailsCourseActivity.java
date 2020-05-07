package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.MyPagerAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.MyTab;

import java.util.Objects;

public class DetailsCourseActivity extends AppCompatActivity {
    Toolbar DetailsCourseActivity_toolbar;
    TabLayout DetailsCourseActivity_tab_layout;
    ViewPager main_pager;
    Course course;

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
            Log.i("course",course.getCName());
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

    private void setPager(){

        DetailsCourseActivity_tab_layout.setupWithViewPager(main_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addTab(new MyTab("Lectures",LectureFragment.newInstance(course)));
        adapter.addTab(new MyTab("Exams",ExamFragment.newInstance(course)));
        main_pager.setAdapter(adapter);

    }


}
