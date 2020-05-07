package com.mykholy.myuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.utilities.Check;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText login_et_email, login_et_password;

    String inputEmail;
    String inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();

        setContentView(R.layout.activity_login);
        setUi();

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


    }

    public void login_btn_On_Click(View view) {

        if (!validateEmail() | !validatePass()) {
            return;

        } else {
            if (ConnectionUtils.isConnected(LoginActivity.this)) {
                Constants.getSPreferences(this).setLogIn(true);
                Intent MainIntent = new Intent(this, MainActivity.class);
                startActivity(MainIntent);
                finish();
            }

        }

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

    private void setLocal(String lang, Activity activity) {

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);


    }


}
