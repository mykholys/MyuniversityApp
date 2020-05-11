package com.mykholy.myuniversity.ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.github.loadingview.LoadingView;
import com.google.android.material.tabs.TabLayout;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.MyPagerAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.MyTab;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.ui.dialog.SortDialogFragment;
import com.mykholy.myuniversity.utilities.Constants;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsCourseActivity extends AppCompatActivity implements SortDialogFragment.OnFragmentInteractionListener, ExamFragment.OnFragmentInteractionListener {
    private Toolbar DetailsCourseActivity_toolbar;
    private TabLayout DetailsCourseActivity_tab_layout;
    private ViewPager main_pager;
    private Course course;
    private String type_exams = "unsolved";
    private MyPagerAdapter adapter;
    private API_Interface api_interface;
    private List<Question> questions;
    private LoadingView loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_details_course);
        setUi();
        setApi();
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
        loadingView = findViewById(R.id.loadingView);
        loadingView.stop();

    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
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
        refreshAdapter(type_exam);
    }

    private void refreshAdapter(String type_exam) {
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
            if (exam.getStatus().equals("active")) {
                goExam(exam);
            } else if (exam.getStatus().equals("pending")) {
                DynamicToast.makeWarning(this, getString(R.string.can_not_enter_exam_before_time), 50).show();
                DynamicToast.makeWarning(this, getString(R.string.start_time_exam) + "\n" + exam.getStartDate(), 50).show();
            } else {
                DynamicToast.makeWarning(this, getString(R.string.exam_expired), 50).show();

            }
        }

    }

    private void goExam(final Exam exam) {
        questions = new ArrayList<>();
        loadingView.start();
        String token = Constants.getSPreferences(this).getType_Token() + " " + Constants.getSPreferences(this).getToken();

        Call<List<Question>> call = api_interface.getAllQuestionForExam(token, exam.geteId(), exam.getcId());
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(@NonNull Call<List<Question>> call, @NonNull Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        questions.addAll(response.body());
                        Intent SolveExamIntent = new Intent(getApplicationContext(), SolveExamActivity.class);
                        SolveExamIntent.putExtra("exam", exam);
                        SolveExamIntent.putExtra("question", (Serializable) questions);
                        startActivityForResult(SolveExamIntent, 1);
                        loadingView.stop();
                    }
                }

                loadingView.stop();
            }

            @Override
            public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
            refreshAdapter("solved");
    }

}
