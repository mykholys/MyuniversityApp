package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.EditText;
import android.widget.ImageView;

import com.github.loadingview.LoadingDialog;

import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Login;
import com.mykholy.myuniversity.model.Student;
import com.mykholy.myuniversity.utilities.Check;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.mykholy.myuniversity.utilities.Constants;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;


public class LoginActivity extends AppCompatActivity {
    private EditText login_et_email, login_et_password;
    LoadingView loadingView;


    String inputEmail;
    String inputPass;

    private API_Interface api_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();

        setContentView(R.layout.activity_login);
        setUi();
        setApi();

    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setUi() {

        //EditText
        login_et_email = findViewById(R.id.login_et_email);
        login_et_password = findViewById(R.id.login_et_password);
        loadingView = findViewById(R.id.loadingView);
        loadingView.stop();


    }


    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }

    public void login_btn_On_Click(View view) {

        if (!validateEmail() | !validatePass()) {
            return;

        } else {
            if (ConnectionUtils.isConnected(LoginActivity.this)) {
                loadingView.start();
                loginApi();

            } else
                DynamicToast.makeWarning(this, getString(R.string.no_internet)).show();

        }

    }

    private void loginApi() {
        Login login = new Login(inputEmail, inputPass, "password", 2, "J3kBZblIgnwviJpdN9NY9YMcXtc572xCwaCG3EXu", "student-api");
        Call<Login> call = api_interface.Login(login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                Log.i("login_loginApi", "onResponse:" + response);
                Log.i("login_loginApi", "before if onResponseBody:" + response.body());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Constants.getSPreferences(LoginActivity.this).setType_Token(response.body().getTokenType());
                    Constants.getSPreferences(LoginActivity.this).setToken(response.body().getAccessToken());
                    Constants.getSPreferences(LoginActivity.this).setRefreshToken(response.body().getRefreshToken());
                    Constants.getSPreferences(LoginActivity.this).setRefreshToken(response.body().getRefreshToken());
                    Constants.getSPreferences(LoginActivity.this).setLogIn(true);
                    Log.i("login_loginApi", "in if onResponseBody:" + response.body());
                    getStudentInformation();


                } else if (response.code() == 401)
                    DynamicToast.makeWarning(LoginActivity.this, getString(R.string.email_pass_incorrect)).show();
                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });
    }

    private void getStudentInformation() {
        String token = Constants.getSPreferences(LoginActivity.this).getType_Token() + " " + Constants.getSPreferences(LoginActivity.this).getToken();
        Log.i("login_getStudent", "token:" + token);

        Call<Student> call = api_interface.getInfoStudent(token, inputEmail);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                Log.i("login_getStudent", "before if onResponseBody:" + response.body());
                Log.i("login_getStudent", "before if onResponse:" + response);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.i("login_getStudent", "in if onResponse:" + response);
                    Log.i("login_getStudent", "in if onResponseBody:" + response.body());
                    Constants.getSPreferences(LoginActivity.this).setSTUDENT_ID(response.body().getId());
                    Constants.getSPreferences(LoginActivity.this).setSTUDENT_NAME(response.body().getSFullName());
                    Constants.getSPreferences(LoginActivity.this).setUserName(response.body().getEmail());
                    Constants.getSPreferences(LoginActivity.this).setSTUDENT_IMAGE(response.body().getImage());
                    Constants.getSPreferences(LoginActivity.this).setSTUDENT_ACADEMIC_YEAR(response.body().getAcademicYear());
                    Constants.getSPreferences(LoginActivity.this).setSTUDENT_DEPT_ID(response.body().getDeptID());
                    Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    loadingView.stop();
                    startActivity(MainIntent);
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });

    }


    //For Login and Validate
    private boolean validateEmail() {
        inputEmail = login_et_email.getText().toString().trim();
        if (inputEmail.isEmpty()) {
            login_et_email.setError(getString(R.string.email_required));
            login_et_email.setFocusable(true);
            return false;
        } else {
            if (!Check.isMail(inputEmail)) {
                login_et_email.setError(getString(R.string.email_not_valid));
                login_et_email.setFocusable(true);
                return false;
            } else {
                login_et_email.setError(null);
                return true;
            }
        }

    }

    private boolean validatePass() {
        inputPass = login_et_password.getText().toString().trim();
        if (inputPass.isEmpty()) {
            login_et_password.setError(getString(R.string.password_required));
            login_et_password.setFocusable(true);
            return false;
        } else {
            login_et_password.setError(null);
            return true;
        }

    }

    public void go_to_forgot_password(View view) {
        Intent ForgotPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(ForgotPasswordIntent);
    }

    public void go_to_sing_up(View view) {
        Intent RegisterIntent = new Intent(this, RegisterActivity.class);
        startActivity(RegisterIntent);
    }


}
