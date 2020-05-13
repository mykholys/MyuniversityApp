package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Dialog;
import com.mykholy.myuniversity.model.Notification;
import com.mykholy.myuniversity.model.Student;
import com.mykholy.myuniversity.ui.dialog.DialogDeveloperFragment;
import com.mykholy.myuniversity.ui.dialog.DialogFragment;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;
import com.mykholy.myuniversity.utilities.NotificationHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, DialogFragment.OnFragmentInteractionListener, CourseFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener {
    private MeowBottomNavigation meo;
    public static final int ID_NOTIFICATION = 1;
    public static final int ID_COURSE = 2;
    public static final int ID_PERSON = 3;
    private Advance3DDrawerLayout drawerLayout;
    TextView menu_table, menu_location, menu_contact_us, menu_developers, menu_settings, menu_logout;
    Toolbar toolbar;

    NavigationView nav_view_notification;
    private API_Interface api_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        LanguageHelper.setLanguage(this, Constants.getSPreferences(this).getLanguage());
        setContentView(R.layout.nav_drawer);


        setUi();
        setApi();
        setListener();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                Fragment select_fragment = null;
                switch (item.getId()) {
                    case ID_NOTIFICATION:
                        select_fragment = new NotificationFragment();
                        meo.clearCount(ID_NOTIFICATION);
                        Constants.getSPreferences(MainActivity.this).setNotificationCount(0);
                        break;
                    case ID_COURSE:
                        select_fragment = new CourseFragment();
                        break;
                    case ID_PERSON:
                        select_fragment = new ProfileFragment();
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

        Log.i("NotificationCount_main:", String.valueOf(Constants.getSPreferences(this).getNotificationCount()));
        if (Constants.getSPreferences(this).getNotificationCount() != 0)
            meo.setCount(ID_NOTIFICATION, String.valueOf(Constants.getSPreferences(this).getNotificationCount()));

        meo.show(ID_COURSE, false);


        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    Log.i("fcm_token:", task.getResult().getToken());
                    saveToken(task.getResult().getToken());
                } else {
                    Log.i("fcm_token:", task.getException().getMessage());

                }
            }
        });

    }

    private void saveToken(String Fcm_Token) {
        String token = Constants.getSPreferences(this).getType_Token() + " " + Constants.getSPreferences(this).getToken();

        Call<Student> call = api_interface.updateTokenStudent(token, Constants.getSPreferences(this).getSTUDENT_ID(), new Student(Fcm_Token, "put"));
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                if (response.isSuccessful()) {
                    Log.i("fcm_token", "token saved");
                }

            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                Log.i("onFailure:", t.getMessage());
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("subscribeToTopic", "done");
                else
                    Log.d("subscribeToTopic", "not done");

            }
        });

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

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
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
                NotificationHelper.displayNotification(getApplicationContext(), "Tile menu_table", "body menu_table");
                break;
            case R.id.menu_location:
                Toast.makeText(MainActivity.this, "menu_location Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                String uri = "http://maps.google.com/maps?daddr=" + 30.575632 + "," + 31.008551 + " (" + "Menoufia University - FCI" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    DynamicToast.makeWarning(this, "Google map app not installed").show();

                }


                break;
            case R.id.menu_contact_us:
                Toast.makeText(MainActivity.this, "menu_contact_us Clicked", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"mykholys30@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_email));
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.my_name) + Constants.getSPreferences(this).getSTUDENT_NAME() + "\n" + getString(R.string.academic_year) + ": " + Constants.getSPreferences(this).getSTUDENT_ACADEMIC_YEAR() + "\n" + getString(R.string.department) + ": " + Constants.getSPreferences(this).getSTUDENT_DEPT_NAME() + "\n" + getString(R.string.email) + ": " + Constants.getSPreferences(this).getUserName() + "\n\n" + getString(R.string.my_problem));
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_developers:
                DialogDeveloperFragment dialogDeveloperFragment = new DialogDeveloperFragment();
                dialogDeveloperFragment.show(getSupportFragmentManager(), null);
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_settings:
                DialogFragment fragment = DialogFragment.newInstance(getString(R.string.menu_settings), getString(R.string.change_language));
                fragment.show(getSupportFragmentManager(), null);
                drawerLayout.closeDrawers();
                break;
            case R.id.menu_logout:
                Constants.getSPreferences(this).setClearAll();
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
        DetailsCourseIntent.putExtra("course", course);
        startActivity(DetailsCourseIntent);
    }

    @Override
    public void onFragmentInteraction(Notification notification) {
        Intent DetailsCourseIntent = new Intent(this, DetailsCourseActivity.class);
        Course course = new Course(notification.getCId(), notification.getCName(), notification.getCImage(), notification.getAcademicYear(), notification.getTerm(), notification.getDId(), notification.getDeptID());
        DetailsCourseIntent.putExtra("course", course);
        startActivity(DetailsCourseIntent);
    }
}
