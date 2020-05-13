package com.mykholy.myuniversity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Department;
import com.mykholy.myuniversity.model.Dialog;
import com.mykholy.myuniversity.model.ErrorStudentRegister;
import com.mykholy.myuniversity.model.Student;
import com.mykholy.myuniversity.ui.dialog.DialogFragment;
import com.mykholy.myuniversity.utilities.Check;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.mykholy.myuniversity.utilities.ErrorUtils;
import com.mykholy.myuniversity.utilities.LanguageHelper;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RegisterActivity extends AppCompatActivity implements DialogFragment.OnFragmentInteractionListener, View.OnClickListener, TextWatcher {


    private EditText Register_et_full_name, Register_et_email, Register_et_password, Register_et_national_id,
            Register_et_phone, Register_et_birthdate, Register_et_state, Register_et_gender,
            Register_et_academic_year, Register_et_department;


    private String input_full_name, input_email, input_password, input_national_id, input_phone,
            input_bdate, input_state, input_gender, input_academic_year, input_department;

    private LoadingView loadingView;

    private API_Interface api_interface;
    ArrayList<Dialog> mdialogs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_register);

        setUi();
        setApi();
        setListener();
        AllDepartment();


    }

    public void Register_btn_sing_up(View view) {
        if (
                !validateFullName() | !validateEmail() | !validatePass()
                        | !validateNationalId() | !validatePhone() | !validateBirthdate()
                        | !validateState() | !validateGender() | !validateAcademicYear() | !validateDepartment()
        ) {
            return;

        } else {
            if (ConnectionUtils.isConnected(RegisterActivity.this)) {
                loadingView.start();
                RegisterApi();

            } else
                DynamicToast.makeWarning(this, getString(R.string.no_internet)).show();

        }
    }

    private void RegisterApi() {
        if ((Integer) Register_et_gender.getTag() == 1) input_gender = "m";
        else input_gender = "f";
        Call<Student> call = api_interface.Register(new Student(input_full_name, input_email, input_password, input_national_id, input_phone, input_bdate, input_gender, input_state, Integer.parseInt(input_academic_year), (Integer) Register_et_department.getTag()));
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                loadingView.stop();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    DynamicToast.makeSuccess(RegisterActivity.this, getString(R.string.account_registerd), 5).show();
                    finish();
                } else if (response.code() == 400) {
                    assert response.body() != null;
                    ErrorStudentRegister errorStudentRegister = ErrorUtils.parseError(response);

                    if (errorStudentRegister.getEmail() != null) {
                        Register_et_email.setError(errorStudentRegister.getEmail().get(0));
                        Register_et_email.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_email.setFocusable(true);
                        Log.i("error_email:", String.valueOf(errorStudentRegister.getEmail().size()));
                    }
                    if (errorStudentRegister.getNIdN() != null) {
                        Register_et_national_id.setError(errorStudentRegister.getNIdN().get(0));
                        Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_national_id.setFocusable(true);
                        Log.i("error_national_id:", String.valueOf(errorStudentRegister.getNIdN().size()));
                    }
                    if (errorStudentRegister.getMsg() != null) {
                        Register_et_national_id.setError(errorStudentRegister.getMsg());
                        Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_national_id.setFocusable(true);

                    }
                    if (errorStudentRegister.getAcademicYear() != null) {
                        Register_et_academic_year.setError(errorStudentRegister.getAcademicYear().get(0));
                        Register_et_academic_year.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_academic_year.setFocusable(true);
                        Log.i("error_academic_year:", String.valueOf(errorStudentRegister.getAcademicYear().size()));

                    }
                    if (errorStudentRegister.getBirthdate() != null) {
                        Register_et_birthdate.setError(errorStudentRegister.getBirthdate().get(0));
                        Register_et_birthdate.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_birthdate.setFocusable(true);
                        DynamicToast.makeWarning(RegisterActivity.this, errorStudentRegister.getBirthdate().get(0)).show();
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getBirthdate().size()));
                    }
                    if (errorStudentRegister.getDeptID() != null) {
                        Register_et_department.setError(errorStudentRegister.getDeptID().get(0));
                        Register_et_department.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_department.setFocusable(true);
                        DynamicToast.makeWarning(RegisterActivity.this, errorStudentRegister.getDeptID().get(0)).show();

                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getDeptID().size()));
                    }
                    if (errorStudentRegister.getGender() != null) {
                        Register_et_gender.setError(errorStudentRegister.getGender().get(0));
                        Register_et_gender.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_gender.setFocusable(true);
                        DynamicToast.makeWarning(RegisterActivity.this, errorStudentRegister.getGender().get(0)).show();
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getGender().size()));

                    }
                    if (errorStudentRegister.getPassword() != null) {
                        Register_et_password.setError(errorStudentRegister.getPassword().get(0));
                        Register_et_password.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_password.setFocusable(true);
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getPassword().size()));

                    }
                    if (errorStudentRegister.getPhone() != null) {
                        Register_et_phone.setError(errorStudentRegister.getPhone().get(0));
                        Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_phone.setFocusable(true);
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getPhone().size()));

                    }
                    if (errorStudentRegister.getSFullName() != null) {
                        Register_et_full_name.setError(errorStudentRegister.getSFullName().get(0));
                        Register_et_full_name.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_full_name.setFocusable(true);
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getSFullName().size()));

                    }
                    if (errorStudentRegister.getState() != null) {
                        Register_et_state.setError(errorStudentRegister.getState().get(0));
                        Register_et_state.setBackgroundResource(R.drawable.custom_bg_et_error);
                        Register_et_state.setFocusable(true);
                        DynamicToast.makeWarning(RegisterActivity.this, errorStudentRegister.getState().get(0)).show();
                        Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister.getState().size()));
                    }
                    Log.i("errorStudentRegister:", String.valueOf(errorStudentRegister));

                }
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());

            }
        });
    }


    @Override
    public void onFragmentInteraction(Dialog dialog, String title) {

        if (title.equals(getString(R.string.state))) {
            Register_et_state.setError(null);
            Register_et_state.setBackgroundResource(R.drawable.custom_bg_et);
            Register_et_state.setText(dialog.getName());
        } else if (title.equals(getString(R.string.gender))) {
            Register_et_gender.setError(null);
            Register_et_gender.setBackgroundResource(R.drawable.custom_bg_et);
            Register_et_gender.setText(dialog.getName());
            Register_et_gender.setTag(dialog.getId());
        } else if (title.equals(getString(R.string.academic_year))) {
            Register_et_academic_year.setError(null);
            Register_et_academic_year.setBackgroundResource(R.drawable.custom_bg_et);
            Register_et_academic_year.setText(dialog.getName());
        } else if (title.equals(getString(R.string.department))) {
            Register_et_department.setError(null);
            Register_et_department.setBackgroundResource(R.drawable.custom_bg_et);
            Register_et_department.setText(dialog.getName());
            Register_et_department.setTag(dialog.getId());
        }
    }

    @Override
    public void onClick(View v) {
        DialogFragment fragment;
        switch (v.getId()) {
            case R.id.Register_et_department:
                if (Register_et_academic_year.getText().toString().isEmpty()) {
                    DynamicToast.makeWarning(this, getString(R.string.academy_year_chosen)).show();
                } else {
                    fragment = DialogFragment.newInstance(getString(R.string.department), getString(R.string.choose_department), mdialogs);
                    fragment.show(getSupportFragmentManager(), null);
                }
                break;
            case R.id.Register_et_academic_year:
                fragment = DialogFragment.newInstance(getString(R.string.academic_year), getString(R.string.choose_academic_year));
                fragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.Register_et_gender:
                fragment = DialogFragment.newInstance(getString(R.string.gender), getString(R.string.choose_gender));
                fragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.Register_et_state:
                fragment = DialogFragment.newInstance(getString(R.string.state), getString(R.string.choose_state));
                fragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.Register_et_birthdate:
                showDatePicker();
                break;


        }

    }

private void AllDepartment(){

    Call<List<Department>> call = api_interface.getAllDepartments();
    call.enqueue(new Callback<List<Department>>() {
        @Override
        public void onResponse(@NonNull Call<List<Department>> call, @NonNull Response<List<Department>> response) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                List<Department> departments = new ArrayList<>(response.body());
                for (int i = 0; i < departments.size(); i++) {
                    mdialogs.add(new Dialog(departments.get(i).getDeptID(), departments.get(i).getName()));
                }
                Log.i("department:", "Successful");

            }

        }

        @Override
        public void onFailure(@NonNull Call<List<Department>> call, @NonNull Throwable t) {
            Log.i("onFailure:", t.getMessage());
        }
    });
}
    public void go_to_sing_in(View view) {
        finish();
    }

    private void setFullScreen() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setListener() {
        Register_et_department.setOnClickListener(this);
        Register_et_academic_year.setOnClickListener(this);
        Register_et_gender.setOnClickListener(this);
        Register_et_state.setOnClickListener(this);
        Register_et_birthdate.setOnClickListener(this);

        //for change text
        Register_et_full_name.addTextChangedListener(this);
        Register_et_email.addTextChangedListener(this);
        Register_et_password.addTextChangedListener(this);
        Register_et_national_id.addTextChangedListener(this);
        Register_et_phone.addTextChangedListener(this);

    }

    private void setUi() {
        Register_et_full_name = findViewById(R.id.Register_et_full_name);
        Register_et_email = findViewById(R.id.Register_et_email);
        Register_et_password = findViewById(R.id.Register_et_password);
        Register_et_national_id = findViewById(R.id.Register_et_national_id);
        Register_et_phone = findViewById(R.id.Register_et_phone);
        Register_et_birthdate = findViewById(R.id.Register_et_birthdate);
        Register_et_state = findViewById(R.id.Register_et_state);
        Register_et_gender = findViewById(R.id.Register_et_gender);
        Register_et_academic_year = findViewById(R.id.Register_et_academic_year);
        Register_et_department = findViewById(R.id.Register_et_department);
        loadingView = findViewById(R.id.loadingView);
        loadingView.stop();
    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }


    private void showDatePicker() {

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint({"DefaultLocale", "SimpleDateFormat"})
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);


                String oldstring = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
                Log.i("Register_et_birthdate:", LanguageHelper.arabicToDecimal(newstring)); //to convert english
                Register_et_birthdate.setText(LanguageHelper.arabicToDecimal(newstring));
                Register_et_birthdate.setError(null);
                Register_et_birthdate.setBackgroundResource(R.drawable.custom_bg_et);


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    //For Register and Validate

    private boolean validateFullName() {
        input_full_name = Register_et_full_name.getText().toString().trim();
        if (input_full_name.isEmpty()) {
            Register_et_full_name.setError(getString(R.string.full_name_required));
            Register_et_full_name.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_full_name.setFocusable(true);
            return false;
        } else {
            if (input_full_name.length() < 6) {
                Register_et_full_name.setError(getString(R.string.fullname_short));
                Register_et_full_name.setBackgroundResource(R.drawable.custom_bg_et_error);
                Register_et_full_name.setFocusable(true);
                return false;
            }
            Register_et_full_name.setError(null);
            Register_et_full_name.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
        }

    }

    private boolean validateEmail() {
        input_email = Register_et_email.getText().toString().trim();
        if (input_email.isEmpty()) {
            Register_et_email.setError(getString(R.string.email_required));
            Register_et_email.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_email.setFocusable(true);
            return false;
        } else {
            if (!Check.isMail(input_email)) {
                Register_et_email.setError(getString(R.string.email_not_valid));
                Register_et_email.setBackgroundResource(R.drawable.custom_bg_et_error);
                Register_et_email.setFocusable(true);
                return false;
            } else {
                Register_et_email.setError(null);
                Register_et_email.setBackgroundResource(R.drawable.custom_bg_et);
                return true;
            }
        }

    }

    private boolean validatePass() {
        input_password = Register_et_password.getText().toString().trim();
        if (input_password.isEmpty()) {
            Register_et_password.setError(getString(R.string.password_required));
            Register_et_password.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_password.setFocusable(true);
            return false;
        } else {
            if (input_password.length() < 6) {
                Register_et_password.setError(getString(R.string.password_short));
                Register_et_password.setBackgroundResource(R.drawable.custom_bg_et_error);
                Register_et_password.setFocusable(true);
                return false;
            }
            Register_et_password.setError(null);
            Register_et_password.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }

    private boolean validateNationalId() {
        input_national_id = Register_et_national_id.getText().toString().trim();
        if (input_national_id.isEmpty()) {
            Register_et_national_id.setError(getString(R.string.national_id_required));
            Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_national_id.setFocusable(true);
            return false;
        } else {
            if (input_national_id.length() != 14) {
                Register_et_national_id.setError(getString(R.string.national_id_14_number));
                Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et_error);
                Register_et_national_id.setFocusable(true);
                return false;
            }
            Register_et_national_id.setError(null);
            Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }

    private boolean validatePhone() {
        input_phone = Register_et_phone.getText().toString().trim();

        if (input_phone.isEmpty()) {
            Register_et_phone.setError(getString(R.string.phone_required));
            Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_phone.setFocusable(true);
            return false;
        } else {
            if (input_phone.length() != 11) {
                Register_et_phone.setError(getString(R.string.phone_11_number));
                Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et_error);
                Register_et_phone.setFocusable(true);
                return false;

            } else {

                if (!String.valueOf(input_phone.toCharArray()[0]).equals("0") | !String.valueOf(input_phone.toCharArray()[1]).equals("1")) {
                    Register_et_phone.setError(getString(R.string.phone_not_valid));
                    Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et_error);
                    Register_et_phone.setFocusable(true);
                    return false;

                }

                Register_et_phone.setError(null);
                Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et);
                return true;
            }


        }

    }

    private boolean validateBirthdate() {
        input_bdate = Register_et_birthdate.getText().toString().trim();
        if (input_bdate.isEmpty()) {
            Register_et_birthdate.setError(getString(R.string.birthdate_required));
            Register_et_birthdate.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_birthdate.setFocusable(true);
            return false;
        } else {

            Register_et_birthdate.setError(null);
            Register_et_birthdate.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }

    private boolean validateState() {
        input_state = Register_et_state.getText().toString().trim();
        if (input_state.isEmpty()) {
            Register_et_state.setError(getString(R.string.state_required));
            Register_et_state.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_state.setFocusable(true);
            return false;
        } else {
            Register_et_state.setError(null);
            Register_et_state.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
        }

    }

    private boolean validateGender() {
        input_gender = Register_et_gender.getText().toString().trim();
        if (input_gender.isEmpty()) {
            Register_et_gender.setError(getString(R.string.gender_required));
            Register_et_gender.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_gender.setFocusable(true);
            return false;
        } else {
            Register_et_gender.setError(null);
            Register_et_gender.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
        }

    }

    private boolean validateAcademicYear() {
        input_academic_year = Register_et_academic_year.getText().toString().trim();
        if (input_academic_year.isEmpty()) {
            Register_et_academic_year.setError(getString(R.string.academic_year_required));
            Register_et_academic_year.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_academic_year.setFocusable(true);
            return false;
        } else {
            Register_et_academic_year.setError(null);
            Register_et_academic_year.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
        }

    }

    private boolean validateDepartment() {
        input_department = Register_et_department.getText().toString().trim();
        if (input_department.isEmpty()) {
            Register_et_department.setError(getString(R.string.department_required));
            Register_et_department.setBackgroundResource(R.drawable.custom_bg_et_error);
            Register_et_department.setFocusable(true);
            return false;
        } else {
            Register_et_department.setError(null);
            Register_et_department.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
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
        if (s == Register_et_full_name.getEditableText()) {
            Register_et_full_name.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == Register_et_email.getEditableText()) {
            Register_et_email.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == Register_et_password.getEditableText()) {
            Register_et_password.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == Register_et_national_id.getEditableText()) {
            Register_et_national_id.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == Register_et_phone.getEditableText()) {
            Register_et_phone.setBackgroundResource(R.drawable.custom_bg_et);
        }


    }
}
