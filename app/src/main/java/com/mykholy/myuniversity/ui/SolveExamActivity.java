package com.mykholy.myuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.ui.dialog.FinishExamDialogFragment;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SolveExamActivity extends AppCompatActivity implements FinishExamDialogFragment.OnFragmentInteractionListener {
    TextView SolveExamActivity_txt_total_take_time, SolveExamActivity_tv_exam_title, SolveExamActivity_tv_exam_num_questions,
            SolveExamActivity_txt_time_per_question, SolveExamActivity_tv_question;
    RadioButton SolveExamActivity_rb_a, SolveExamActivity_rb_b, SolveExamActivity_rb_c, SolveExamActivity_rb_d;

    androidx.appcompat.widget.AppCompatButton SolveExamActivity_btn_Next;
    String student_answered = "0";
    MyCounterUp ct;

    private long mTotalTimeTaken;
    private long DefaultTotalTimeTaken;
    private int TimePerQuestion;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private Exam exam;
    private List<Question> questionList;

    private int questionCounter;
    private int questionCounterTotal;
    private Question cuurentQuestion;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_solve_exam);
        setUi();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            exam = (Exam) getIntent().getSerializableExtra("exam"); //Obtaining data
            questionList = (List<Question>) getIntent().getSerializableExtra("question"); //Obtaining data
            Log.i("questionList.size():", String.valueOf(questionList.size()));

            setDataUi();
            startTime();


        }

        SolveExamActivity_rb_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setA();
            }
        });
        SolveExamActivity_rb_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setB();
            }
        });
        SolveExamActivity_rb_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setC();
            }
        });
        SolveExamActivity_rb_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setD();
            }
        });

        SolveExamActivity_btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SolveExamActivity_rb_a.isChecked() || SolveExamActivity_rb_b.isChecked() || SolveExamActivity_rb_c.isChecked() || SolveExamActivity_rb_d.isChecked()) {
                    checkAnswer();
                } else {
                    DynamicToast.makeWarning(SolveExamActivity.this, "Please select an answer").show();
                }

            }
        });
    }

    private void checkAnswer() {
        countDownTimer.cancel();
        if (cuurentQuestion.getAns().equals(student_answered))
            score++;
        saveAnswer(student_answered);


    }

    @Override
    public void onBackPressed() {
        DynamicToast.makeWarning(SolveExamActivity.this, getString(R.string.please_complete_exam)).show();
    }

    private void saveAnswer(String ans) {
        //save answer in db

        //show nex question
        showNextQuestion();
    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setUi() {
        SolveExamActivity_txt_total_take_time = findViewById(R.id.SolveExamActivity_txt_total_take_time);
        SolveExamActivity_tv_exam_title = findViewById(R.id.SolveExamActivity_tv_exam_title);
        SolveExamActivity_tv_exam_num_questions = findViewById(R.id.SolveExamActivity_tv_exam_num_questions);
        SolveExamActivity_txt_time_per_question = findViewById(R.id.SolveExamActivity_txt_time_per_question);
        SolveExamActivity_tv_question = findViewById(R.id.SolveExamActivity_tv_question);

        SolveExamActivity_rb_a = findViewById(R.id.SolveExamActivity_rb_a);
        SolveExamActivity_rb_b = findViewById(R.id.SolveExamActivity_rb_b);
        SolveExamActivity_rb_c = findViewById(R.id.SolveExamActivity_rb_c);
        SolveExamActivity_rb_d = findViewById(R.id.SolveExamActivity_rb_d);
        SolveExamActivity_btn_Next = findViewById(R.id.SolveExamActivity_btn_Next);


    }

    private void setA() {
        SolveExamActivity_rb_a.setChecked(true);
        SolveExamActivity_rb_b.setChecked(false);
        SolveExamActivity_rb_c.setChecked(false);
        SolveExamActivity_rb_d.setChecked(false);
        student_answered = "a";
    }

    private void setB() {
        SolveExamActivity_rb_a.setChecked(false);
        SolveExamActivity_rb_b.setChecked(true);
        SolveExamActivity_rb_c.setChecked(false);
        SolveExamActivity_rb_d.setChecked(false);
        student_answered = "b";
    }

    private void setC() {
        SolveExamActivity_rb_a.setChecked(false);
        SolveExamActivity_rb_b.setChecked(false);
        SolveExamActivity_rb_c.setChecked(true);
        SolveExamActivity_rb_d.setChecked(false);
        student_answered = "c";
    }

    private void setD() {
        SolveExamActivity_rb_a.setChecked(false);
        SolveExamActivity_rb_b.setChecked(false);
        SolveExamActivity_rb_c.setChecked(false);
        SolveExamActivity_rb_d.setChecked(true);
        student_answered = "d";
    }

    private void setDataUi() {
        TimePerQuestion = exam.getTimer();
        questionCounterTotal = questionList.size();
        DefaultTotalTimeTaken = (TimePerQuestion * questionCounterTotal) * 1000; //in milSec
        timeLeftInMillis = TimePerQuestion * 1000;
        Log.i("timeLeftInMillis", String.valueOf(timeLeftInMillis));
        SolveExamActivity_tv_exam_title.setText(exam.getTitle());
        SolveExamActivity_tv_exam_num_questions.setText(String.format(Locale.getDefault(), "%s1/%d", getString(R.string.question), questionCounterTotal));
        SolveExamActivity_txt_time_per_question.setText(String.valueOf(timeLeftInMillis));
        Collections.shuffle(questionList);
        startCountDown();
        showNextQuestion();

    }

    private void showNextQuestion() {
        clearCheck();
        if (questionCounter < questionCounterTotal) {
            cuurentQuestion = questionList.get(questionCounter);
            SolveExamActivity_tv_question.setText(cuurentQuestion.getTitle());
            SolveExamActivity_rb_a.setText(cuurentQuestion.getOptionA());
            SolveExamActivity_rb_b.setText(cuurentQuestion.getOptionB());
            SolveExamActivity_rb_c.setText(cuurentQuestion.getOptionC());
            SolveExamActivity_rb_d.setText(cuurentQuestion.getOptionD());

            questionCounter++;
            SolveExamActivity_tv_exam_num_questions.setText(String.format("%s%d /%d", getString(R.string.question), questionCounter, questionCounterTotal));

            student_answered = "0";
            if (questionCounter == questionCounterTotal)
                SolveExamActivity_btn_Next.setText(getString(R.string.submit));
            else
                SolveExamActivity_btn_Next.setText(getString(R.string.next));

            countDownTimer.start();

        } else {
            finishExam();
        }

    }

    private void finishExam() {
        int hour = (int) ((mTotalTimeTaken) / 60 / 60);
        int minutes = (int) ((mTotalTimeTaken) / 60);
        int seconds = (int) ((mTotalTimeTaken) % 60);
        FinishExamDialogFragment finishExamDialogFragment = FinishExamDialogFragment.newInstance(score, questionCounterTotal);
        finishExamDialogFragment.setCancelable(false);
        finishExamDialogFragment.show(getSupportFragmentManager(), null);

        //save exam in db
        Log.i("score:", String.valueOf(score));
        Log.i("totaltime:", String.valueOf(mTotalTimeTaken));
//        DynamicToast.makeSuccess(SolveExamActivity.this, "Your Score:" + score);
//        DynamicToast.makeSuccess(SolveExamActivity.this, "total time:" + String.format("%02d:%02d:%02d", hour, minutes, seconds));

    }

    private void clearCheck() {
        SolveExamActivity_rb_a.setChecked(false);
        SolveExamActivity_rb_b.setChecked(false);
        SolveExamActivity_rb_c.setChecked(false);
        SolveExamActivity_rb_d.setChecked(false);

    }

    @Override
    public void onFragmentInteraction() {
        countDownTimer.cancel();
        ct.cancel();

        finish();
    }

    public class MyCounterUp extends CountDownTimer {

        MyCounterUp(long milSec, long IntervalMilSec) {
            super(milSec, IntervalMilSec);


        }

        @Override
        public void onTick(long millisUntilFinished) {
            updateCounterUp();
            mTotalTimeTaken++;
        }

        @Override
        public void onFinish() {
            ct.cancel();
        }
    }

    private void startTime() {
        ct = new MyCounterUp(DefaultTotalTimeTaken, 1000);

        ct.start();

    }

    private void updateCounterUp() {
        int hour = (int) ((mTotalTimeTaken) / 60 / 60);
        int minutes = (int) ((mTotalTimeTaken) / 60);
        int seconds = (int) ((mTotalTimeTaken) % 60);
        SolveExamActivity_txt_total_take_time.setText(String.format("%02d:%02d:%02d", hour, minutes, seconds));
    }

    private void startCountDown() {

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCounterDown();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCounterDown();
                checkAnswer();
            }
        };
    }

    private void updateCounterDown() {
        int minutes = (int) (((timeLeftInMillis) / 1000) / 60);
        int seconds = (int) (((timeLeftInMillis) / 1000) % 60);
        SolveExamActivity_txt_time_per_question.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
        if (timeLeftInMillis < 10000)
            SolveExamActivity_txt_time_per_question.setTextColor(Color.RED);
        else
            SolveExamActivity_txt_time_per_question.setTextColor(Color.BLACK);

    }
}
