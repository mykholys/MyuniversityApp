package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
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

import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.ui.dialog.FinishExamDialogFragment;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;
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
    private API_Interface api_interface;
    private LoadingView loadingView;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        LanguageHelper.setLanguage(this, Constants.getSPreferences(this).getLanguage());

        setContentView(R.layout.activity_solve_exam);
        setUi();
        setApi();


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
        saveAnswer(student_answered);


    }

    @Override
    public void onBackPressed() {
        DynamicToast.makeWarning(SolveExamActivity.this, getString(R.string.please_complete_exam)).show();
    }

    private void saveAnswer(String ans) {
        loadingView.start();

        Call<Question> call = api_interface.saveMyAnswer(token, new Question(cuurentQuestion.getQId(), ans, Constants.getSPreferences(this).getSTUDENT_ID()));
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(@NonNull Call<Question> call, @NonNull Response<Question> response) {
                if (response.isSuccessful()) {
                    Log.i("student_answered", "in 200:" + student_answered);
                    if (cuurentQuestion.getAns().equals(student_answered))
                        score++;
                    loadingView.stop();
                    showNextQuestion();
                } else {
                    Log.i("student_answered", "in else:" + student_answered + "\n" + response.toString());
                }

                loadingView.stop();
            }

            @Override
            public void onFailure(@NonNull Call<Question> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure_call", call.request().toString());
                Log.i("onFailure:", t.toString());
            }
        });

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
        loadingView = findViewById(R.id.loadingView);
        loadingView.stop();


    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
        token = Constants.getSPreferences(this).getType_Token() + " " + Constants.getSPreferences(this).getToken();

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
            SolveExamActivity_tv_exam_num_questions.setText(String.format(Locale.getDefault(), "%s%d /%d", getString(R.string.question), questionCounter, questionCounterTotal));

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
        String engStrTotalTime=LanguageHelper.arabicToDecimal(String.valueOf(mTotalTimeTaken));
        long myTotalTimeTaken= Long.parseLong(engStrTotalTime);
        Log.i("TotalTimeTaken_en", engStrTotalTime);
        Log.i("TotalTimeTaken_long", String.valueOf(myTotalTimeTaken));
        int hour = (int) ((myTotalTimeTaken) / 60 / 60);
        int minutes = (int) ((myTotalTimeTaken) / 60);
        int seconds = (int) ((myTotalTimeTaken) % 60);
        loadingView.start();
        String strTotalTime=String.format(Locale.US, "%02d:%02d:%02d", hour, minutes, seconds);
        Log.i("TotalTimeTaken_strTime", strTotalTime);
        Log.i("TotalTimeTaken_score", String.valueOf(score));
        Call<Exam> call = api_interface.saveExam(token, new Exam(exam.geteId(), Constants.getSPreferences(this).getSTUDENT_ID(), score,strTotalTime ));
        if (countDownTimer != null)
            countDownTimer.cancel();
        if (ct != null)
            ct.cancel();
        call.enqueue(new Callback<Exam>() {
            @Override
            public void onResponse(@NonNull Call<Exam> call, @NonNull Response<Exam> response) {
                Log.i("finishExam", "before if");
                Log.i("finishExam", "before if" + response.toString());
                loadingView.stop();
                if (response.isSuccessful()) {
                    Log.i("finishExam", "in if 200");
                    Log.i("finishExam", "in if 200" + response.toString());
                    FinishExamDialogFragment finishExamDialogFragment = FinishExamDialogFragment.newInstance(score, questionCounterTotal);
                    finishExamDialogFragment.setCancelable(false);
                    finishExamDialogFragment.show(getSupportFragmentManager(), null);
                } else {
                    Log.i("finishExam", "in else");
                    Log.i("finishExam", "in else" + response.toString());
                    Log.i("finishExam", "in else" + call.request().toString());
                    Log.i("finishExam", "in else" + Objects.requireNonNull(call.request().body()).toString());

                    //finishExam();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Exam> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });


    }

    private void clearCheck() {
        SolveExamActivity_rb_a.setChecked(false);
        SolveExamActivity_rb_b.setChecked(false);
        SolveExamActivity_rb_c.setChecked(false);
        SolveExamActivity_rb_d.setChecked(false);

    }

    @Override
    public void onFragmentInteraction() {

        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    protected void onStart() {
        super.onStart();
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
        SolveExamActivity_txt_total_take_time.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d", hour, minutes, seconds));
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
