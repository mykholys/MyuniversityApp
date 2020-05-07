package com.mykholy.myuniversity.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Dialog;
import com.mykholy.myuniversity.ui.dialog.DialogFragment;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogFragment.OnFragmentInteractionListener, CourseFragment.OnFragmentInteractionListener {
    private MeowBottomNavigation meo;
    public static final int ID_NOTIFICATION = 1;
    public static final int ID_COURSE = 2;
    public static final int ID_PERSON = 3;
    private Advance3DDrawerLayout drawerLayout;
    TextView menu_table, menu_location, menu_contact_us, menu_developers, menu_settings, menu_logout;
    Toolbar toolbar;

    NavigationView nav_view_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.nav_drawer);


        setUi();
        setListener();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                Fragment select_fragment = null;
                switch (item.getId()) {
                    case ID_NOTIFICATION:
                        select_fragment = new NotificationFragment();
                        break;
                    case ID_COURSE:
                        select_fragment = new CourseFragment();
                        break;
                    case ID_PERSON:
                        select_fragment = new ProfileFragment();
                        meo.clearCount(ID_NOTIFICATION);
                        break;

                }

                assert select_fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select_fragment).commit();
            }
        });


        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Log.i("meo", "setOnShowListener");
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Log.i("meo", "onReselectItem");
            }
        });


        meo.setCount(ID_NOTIFICATION, "115");

        meo.show(ID_COURSE, false);

    }


    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setUi() {
        setUiDrawLayout();
        setUiMeo();
        if (Constants.getSPreferences(this).getLanguage().equals("ar"))
            setRTL();
        else
            setLTR();

    }

    private void setUiDrawLayout() {
        setUiMenu();
        drawerLayout = findViewById(R.id.drawer_layout);
        nav_view_notification = findViewById(R.id.nav_view_notification);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void setLTR() {

        setDrawerLayoutBackgroundLTR();
        nav_view_notification.setBackgroundResource(R.drawable.custom_bg_draw_layout_ltr);

    }

    private void setRTL() {
        setDrawerLayoutBackgroundRTL();
        // imageView.setImageResource(R.drawable.custom_bg_toolbar_course_rtl);
        nav_view_notification.setBackgroundResource(R.drawable.custom_bg_draw_layout_rtl);
    }

    private void setDrawerLayoutBackgroundRTL() {

        drawerLayout.setViewRotation(GravityCompat.END, 5); // set degree of Y-rotation ( value : 0 -> 45)
        drawerLayout.setRadius(GravityCompat.END, 40);
        drawerLayout.useCustomBehavior(GravityCompat.END); //assign custom behavior for "Right" drawer
        drawerLayout.setViewElevation(GravityCompat.END, 20); //set main view elevation when drawer open (dimension)
        //drawerLayout.setDrawerElevation(GravityCompat.END, 20); //set drawer elevation (dimension)

    }

    private void setDrawerLayoutBackgroundLTR() {


        drawerLayout.setViewRotation(GravityCompat.START, 5); // set degree of Y-rotation ( value : 0 -> 45)
        drawerLayout.setRadius(GravityCompat.START, 40);
        drawerLayout.useCustomBehavior(GravityCompat.START); //assign custom behavior for "Right" drawer
        drawerLayout.setViewElevation(GravityCompat.START, 20); //set main view elevation when drawer open (dimension)
        // drawerLayout.setDrawerElevation(GravityCompat.START, 20); //set drawer elevation (dimension)

    }

    private void setUiMenu() {
        menu_table = findViewById(R.id.menu_table);
        menu_location = findViewById(R.id.menu_location);
        menu_contact_us = findViewById(R.id.menu_contact_us);
        menu_developers = findViewById(R.id.menu_developers);
        menu_settings = findViewById(R.id.menu_settings);
        menu_logout = findViewById(R.id.menu_logout);
    }

    private void setListener() {
        menu_table.setOnClickListener(this);
        menu_location.setOnClickListener(this);
        menu_contact_us.setOnClickListener(this);
        menu_developers.setOnClickListener(this);
        menu_settings.setOnClickListener(this);
        menu_logout.setOnClickListener(this);
    }

    private void setUiMeo() {

        meo = findViewById(R.id.meo);
        meo.add(new MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.ic_notification));
        meo.add(new MeowBottomNavigation.Model(ID_COURSE, R.drawable.ic_course));
        meo.add(new MeowBottomNavigation.Model(ID_PERSON, R.drawable.ic_person));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CourseFragment()).commit();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_table:
                Toast.makeText(MainActivity.this, "menu_table Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_location:
                Toast.makeText(MainActivity.this, "menu_location Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_contact_us:
                Toast.makeText(MainActivity.this, "menu_contact_us Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_developers:
                Toast.makeText(MainActivity.this, "menu_developers Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_settings:
                DialogFragment fragment = DialogFragment.newInstance(getString(R.string.menu_settings), getString(R.string.change_language));
                fragment.show(getSupportFragmentManager(), null);
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_logout:
                Constants.getSPreferences(this).setLogIn(false);
                drawerLayout.closeDrawers();
                Intent LoginIntent = new Intent(this, LoginActivity.class);
                startActivity(LoginIntent);
                finish();
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Dialog dialog, String title) {
        if (title.equals(getString(R.string.menu_settings))) {
            if (dialog.getName().equals("العربية")) LanguageHelper.setLanguage(this, "ar");
            if (dialog.getName().equals("English")) LanguageHelper.setLanguage(this, "en");
            Intent SplashScreenIntent = new Intent(this, SplashScreenActivity.class);
            startActivity(SplashScreenIntent);
            finish();
        }
    }

    @Override
    public void onFragmentInteraction(Course course) {
        Intent DetailsCourseIntent = new Intent(this, DetailsCourseActivity.class);
        DetailsCourseIntent.putExtra("course",  course);
        startActivity(DetailsCourseIntent);
    }
}
