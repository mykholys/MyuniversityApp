package com.mykholy.myuniversity.ui;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.github.loadingview.LoadingView;
import com.google.android.material.tabs.TabLayout;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.MyPagerAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Lecture;
import com.mykholy.myuniversity.model.MyTab;
import com.mykholy.myuniversity.model.Question;
import com.mykholy.myuniversity.ui.dialog.SortDialogFragment;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.LanguageHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsCourseActivity extends AppCompatActivity implements SortDialogFragment.OnFragmentInteractionListener, ExamFragment.OnFragmentInteractionListener, LectureFragment.OnFragmentInteractionListener {
    private static final String TAG = "DownloadFile";
    private Toolbar DetailsCourseActivity_toolbar;
    private TabLayout DetailsCourseActivity_tab_layout;
    private ViewPager main_pager;
    private Course course;
    private String type_exams = "unsolved";
    private MyPagerAdapter adapter;
    private API_Interface api_interface;
    private List<Question> questions;
    private LoadingView loadingView;
    public static final int WRITE_EX_REQ_CODE = 1;
    ProgressDialog progressDialog;
    Lecture lecture;
    private String urlLecFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        LanguageHelper.setLanguage(this, Constants.getSPreferences(this).getLanguage());
        setContentView(R.layout.activity_details_course);
        setUi();
        setApi();
        setToolbar();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

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

    @Override
    public void onFragmentInteraction(Lecture lecture) {
        this.lecture = lecture;
        urlLecFile = AppClient.BASE_URL + "public/images/Lecture/" + lecture.getFile();
        Log.i("url:", urlLecFile);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (!checkIsFileExist(urlLecFile))
                ben_downloadsFile();
            else {

                openFileFromApp(urlLecFile);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EX_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // DynamicToast.makeSuccess(this, "Permission generated").show();
                if (!checkIsFileExist(urlLecFile))
                    ben_downloadsFile();
                else {

                    openFileFromApp(urlLecFile);
                }

            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void ben_downloadsFile() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.download_file));
        progressDialog.setMessage(getString(R.string.downloading_file));
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        // Toast.makeText(this, "isExternalStorageReadable:" + isExternalStorageReadable(), Toast.LENGTH_LONG).show();
        MyDownloadTask myDownloadTask = new MyDownloadTask(this);
        myDownloadTask.execute(urlLecFile);


//        new AsyncTask<Void, Long, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                Call<ResponseBody> call = api_interface.downloadFileWithDynamicUrlAsync(urlLecFile);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                        Log.d(TAG, "Response:"+response.toString());
//                        if (response.isSuccessful()) {
//
//                            assert response.body() != null;
//                            Log.d(TAG, "isSuccessfulBody:"+response.body().toString());
//                            Log.d(TAG, "server contacted and has file");
//
//                            boolean writtenToDisk = writeResponseBodyToDisk(response.body());
//
//                            Log.d(TAG, "file download was a success? " + writtenToDisk);
//                        }
//                        else {
//                            Log.d(TAG, "server contact failed");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                        Log.d(TAG, "onFailure"+t.getMessage());
//                        Log.d(TAG, "onFailure"+call.request().toString());
//                        Log.d(TAG, "onFailure"+ Objects.requireNonNull(call.request().body()).toString());
//                    }
//                });
//
//                return null;
//            }
//        }.execute();


    }

    private boolean checkIsFileExist(String... sUrl) {
        String[] fileName = sUrl[0].split("/");
        Log.i("url_file", fileName[fileName.length - 1]);
        File myfile = new File(getExternalFilesDir("Download"), fileName[fileName.length - 1]);
        return myfile.exists();
    }

    @SuppressLint("StaticFieldLeak")
    private class MyDownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock wakeLock;
        String path;


        public MyDownloadTask(Context context) {
            this.context = context;

        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream in = null;
            OutputStream out = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    return "Server return http " + connection.getResponseCode() + " --" + connection.getResponseMessage();

                int fileLength = connection.getContentLength();

                String[] file = sUrl[0].split("/");

                in = connection.getInputStream();
                String DirName = "MyAppName";
                File dir = new File(Environment.getExternalStorageDirectory(), DirName);
                File dir2 = new File(getExternalFilesDir(null), "Download");
                if (!dir2.exists()) {
                    dir2.mkdirs();
                }

                /*
                ****
                for more detials go to

                https://stackoverflow.com/questions/51565897/saving-files-in-android-for-beginners-internal-external-storage
                *****
                */
                //file in internal Storage
                File myfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "myfile_" + file[file.length - 1]);

                //file in Custom Folder in internal Storage
                File myfileinfolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DirName + "/", "myfileinfolder_" + file[file.length - 1]);
                //Toast.makeText(context, "myfileinfolder:" + myfileinfolder.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();

                //file in package name
                File myfile2 = new File(getExternalFilesDir(null), "myfile2_" + file[file.length - 1]);

                File myfile3 = new File(getExternalFilesDir("Download"), file[file.length - 1]);
                Log.i("download:", file[file.length - 1]);
                Log.i("download:", String.valueOf(file.length));
                Log.i("download:", file[0]);

                out = new FileOutputStream(myfile3);
                byte[] data = new byte[4096];

                long total = 0;
                int count;
                while ((count = in.read(data)) != -1) {


                    if (isCancelled()) {
                        in.close();
                        return null;
                    }
                    total += count;

                    if (fileLength > 0) {
                        publishProgress((int) (total * 100 / fileLength));

                    }
                    out.write(data, 0, count);

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) out.close();
                    if (in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (connection != null) connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            wakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);

            progressDialog.show();
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            //  Toast.makeText(context, "path:" + path, Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            wakeLock.release();
            Toast.makeText(context, "Downloaded", Toast.LENGTH_LONG).show();

        }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void openFileFromApp(String... sUrl) {
        String[] fileName = sUrl[0].split("/");
        File myfile = new File(getExternalFilesDir("Download"), fileName[fileName.length - 1]);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(Uri.fromFile(myfile), "application/vnd.ms-powerpoint");

        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.i("NoApp", e.getMessage());
        }


    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            String[] file = urlLecFile.split("/");
            // todo change the file location/name according to your needs
            //File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
            File futureStudioIconFile = new File(getExternalFilesDir("Download"), file[file.length - 1]);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
