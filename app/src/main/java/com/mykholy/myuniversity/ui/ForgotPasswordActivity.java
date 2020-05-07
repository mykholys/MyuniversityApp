package com.mykholy.myuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.ui.dialog.DialogFragment;
import com.mykholy.myuniversity.ui.dialog.ForgotPasswordDialogFragment;
import com.mykholy.myuniversity.utilities.Check;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class ForgotPasswordActivity extends AppCompatActivity implements TextWatcher,
        ForgotPasswordDialogFragment.OnFragmentInteractionListener {
    private EditText ForgotPassword_et_email;


    private String input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setUi();
        setListener();
    }

    public void ForgotPassword_btn_reset_password(View view) {

        if (!validateEmail()) {
            return;

        } else {
            if (ConnectionUtils.isConnected(ForgotPasswordActivity.this))
                showDialogDone();
            else {
                // find the CoordinatorLayout id
                View contextView = findViewById(android.R.id.content);
                // Make and display Snackbar
                Snackbar snackbar =
                        Snackbar.make(contextView, "Internet connection is lost", Snackbar.LENGTH_SHORT);
                // Set action with Retry Listener
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ConnectionUtils.isConnected(ForgotPasswordActivity.this))
                            showDialogDone();
                        else
                            DynamicToast.makeWarning(ForgotPasswordActivity.this, "no internet,check your internet :)").show();

                    }
                });

                // show the Snackbar
                snackbar.show();
            }


        }
    }

    private void showDialogDone() {
        ForgotPasswordDialogFragment fragment = ForgotPasswordDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), null);
    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setListener() {

        //for change text
        ForgotPassword_et_email.addTextChangedListener(this);


    }

    private void setUi() {

        ForgotPassword_et_email = findViewById(R.id.ForgotPassword_et_email);

    }

    private boolean validateEmail() {
        input_email = ForgotPassword_et_email.getText().toString().trim();
        if (input_email.isEmpty()) {
            ForgotPassword_et_email.setError(getString(R.string.email_required));
            ForgotPassword_et_email.setBackgroundResource(R.drawable.custom_bg_et_error);
            ForgotPassword_et_email.setFocusable(true);
            return false;
        } else {
            if (!Check.isMail(input_email)) {
                ForgotPassword_et_email.setError(getString(R.string.email_not_valid));
                ForgotPassword_et_email.setBackgroundResource(R.drawable.custom_bg_et_error);
                ForgotPassword_et_email.setFocusable(true);
                return false;
            } else {
                ForgotPassword_et_email.setError(null);
                ForgotPassword_et_email.setBackgroundResource(R.drawable.custom_bg_et);
                return true;
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == ForgotPassword_et_email.getEditableText()) {
            ForgotPassword_et_email.setBackgroundResource(R.drawable.custom_bg_et);
        }
    }

    @Override
    public void onFragmentInteraction() {
        finish();
    }
}
