package com.mykholy.myuniversity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView study_iv, students, logo;
    Animation from_bottom, from_top, fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();

        LanguageHelper.setLanguage(this, Constants.getSPreferences(this).getLanguage());
        setContentView(R.layout.activity_splash_screen);
        setUi();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckInternet();
            }
        }, 4000);
    }

    private void setUi() {


//set view
        study_iv = findViewById(R.id.study_iv);
        students = findViewById(R.id.students);
        logo = findViewById(R.id.logo);

//set animations
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        from_top = AnimationUtils.loadAnimation(this, R.anim.from_top);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        study_iv.setAnimation(from_bottom);
        students.setAnimation(from_top);
        logo.setAnimation(fade_in);
    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void CheckInternet() {

        if (ConnectionUtils.isConnected(this)) {
            DynamicToast.makeSuccess(this, "Yes,Internet").show();
            if (Constants.getSPreferences(this).isLoggedIn()) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
//            showUi();
//            GenerateToken();
//            CheckLogin();
        } else {

            DynamicToast.makeError(this, "No,Internet").show();
//            DynamicToast.make(this, "No,Internet",getResources().getDrawable(R.drawable.ic_launcher_background),
//                    Color.BLUE, Color.RED, 2).show();
//            UiHide();
        }
    }


}
