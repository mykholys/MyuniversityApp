package com.mykholy.myuniversity.ui;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.loadingview.LoadingView;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.ErrorStudentRegister;
import com.mykholy.myuniversity.model.Student;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.mykholy.myuniversity.utilities.Constants;
import com.mykholy.myuniversity.utilities.ErrorUtils;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;


public class ProfileFragment extends Fragment implements TextWatcher {
    private ImageView ProfileFragment_iv;
    private TextView ProfileFragment_tv_name, ProfileFragment_tv_email;
    private EditText ProfileFragment_et_full_name, ProfileFragment_et_current_password,
            ProfileFragment_et_new_password, ProfileFragment_et_confirm_new_password;
    private androidx.appcompat.widget.AppCompatButton ProfileFragment_btn_update;

    private String input_full_name, input_current_password, input_new_password, input_confirm_new_password;

    private API_Interface api_interface;
    private LoadingView loadingView;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);
        setApi();
        setData();
        setListener();
        ProfileFragment_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() | !validateCurrentPass() | !validateNewPass() | !validateConfirmNewPass()) {
                    return;

                } else {
                    if (ConnectionUtils.isConnected(Objects.requireNonNull(getActivity()))) {
                        updateStudentInfo();
                    }

                }
            }
        });
    }

    private void updateStudentInfo() {
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();

        Call<Student> call = api_interface.updateStudent(token, Constants.getSPreferences(getActivity()).getSTUDENT_ID(), new Student(input_full_name, input_new_password,input_current_password, "put"));
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Constants.getSPreferences(getActivity()).setSTUDENT_NAME(response.body().getSFullName());
                    ProfileFragment_tv_name.setText(Constants.getSPreferences(getActivity()).getSTUDENT_NAME());
                    ProfileFragment_et_full_name.setText(Constants.getSPreferences(getActivity()).getSTUDENT_NAME());
                    ProfileFragment_et_current_password.setText("");
                    ProfileFragment_et_new_password.setText("");
                    ProfileFragment_et_confirm_new_password.setText("");
                    DynamicToast.make(Objects.requireNonNull(getActivity()), getString(R.string.info_updated), getResources().getDrawable(R.drawable.ic_check), Color.WHITE, Color.parseColor("#28A745"), 5).show();

                } else if (response.code() == 400) {
                    assert response.body() != null;
                    ErrorStudentRegister errorStudentUpdateInfo = ErrorUtils.parseError(response);
                    Log.i("errorStudentUpdateInfo",errorStudentUpdateInfo.getMessage());
                    Log.i("errorStudent_c_pass",input_current_password);
                    Log.i("errorStudent_new_pass",input_new_password);
                    if (errorStudentUpdateInfo.getMessage().contains("current password incorrect"))
                        DynamicToast.makeWarning(Objects.requireNonNull(getActivity()), getString(R.string.current_password_incorrect)).show();
                }

                loadingView.stop();
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {

                loadingView.stop();
                Log.i("onFailure:", t.getMessage());

            }
        });

    }

    private void setUi(View view) {
        ProfileFragment_iv = view.findViewById(R.id.ProfileFragment_iv);
        ProfileFragment_tv_name = view.findViewById(R.id.ProfileFragment_tv_name);
        ProfileFragment_tv_email = view.findViewById(R.id.ProfileFragment_tv_email);
        ProfileFragment_et_full_name = view.findViewById(R.id.ProfileFragment_et_full_name);
        ProfileFragment_et_current_password = view.findViewById(R.id.ProfileFragment_et_current_password);
        ProfileFragment_et_new_password = view.findViewById(R.id.ProfileFragment_et_new_password);
        ProfileFragment_et_confirm_new_password = view.findViewById(R.id.ProfileFragment_et_confirm_new_password);
        ProfileFragment_btn_update = view.findViewById(R.id.ProfileFragment_btn_update);
        loadingView = view.findViewById(R.id.loadingView);
        loadingView.stop();
    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }

    private void setData() {
        String imageProfile = AppClient.BASE_URL + "public/images/Students/" + Constants.getSPreferences(getActivity()).getSTUDENT_IMAGE();

        //setImageProfile
        Glide.with(this)
                .load(imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .into(ProfileFragment_iv);

        ProfileFragment_tv_name.setText(Constants.getSPreferences(getActivity()).getSTUDENT_NAME());
        ProfileFragment_et_full_name.setText(Constants.getSPreferences(getActivity()).getSTUDENT_NAME());
        ProfileFragment_tv_email.setText(Constants.getSPreferences(getActivity()).getUserName());

    }

    private void setListener() {
        //for change text
        ProfileFragment_et_full_name.addTextChangedListener(this);
        ProfileFragment_et_current_password.addTextChangedListener(this);
        ProfileFragment_et_new_password.addTextChangedListener(this);
        ProfileFragment_et_confirm_new_password.addTextChangedListener(this);


    }

    //For Profile and Validate
    private boolean validateFullName() {
        input_full_name = ProfileFragment_et_full_name.getText().toString().trim();
        if (input_full_name.isEmpty()) {
            ProfileFragment_et_full_name.setError(getString(R.string.full_name_required));
            ProfileFragment_et_full_name.setBackgroundResource(R.drawable.custom_bg_et_error);
            ProfileFragment_et_full_name.setFocusable(true);
            return false;
        } else {
            if (input_full_name.length() < 6) {
                ProfileFragment_et_full_name.setError(getString(R.string.fullname_short));
                ProfileFragment_et_full_name.setBackgroundResource(R.drawable.custom_bg_et_error);
                ProfileFragment_et_full_name.setFocusable(true);
                return false;
            }
            ProfileFragment_et_full_name.setError(null);
            ProfileFragment_et_full_name.setBackgroundResource(R.drawable.custom_bg_et);
            return true;
        }

    }

    private boolean validateCurrentPass() {
        input_current_password = ProfileFragment_et_current_password.getText().toString().trim();
        if (input_current_password.isEmpty()) {
            ProfileFragment_et_current_password.setError(getString(R.string.password_current_required));
            ProfileFragment_et_current_password.setBackgroundResource(R.drawable.custom_bg_et_error);
            ProfileFragment_et_current_password.setFocusable(true);
            return false;
        } else {

            ProfileFragment_et_current_password.setError(null);
            ProfileFragment_et_current_password.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }

    private boolean validateNewPass() {
        input_new_password = ProfileFragment_et_new_password.getText().toString().trim();
        if (input_new_password.isEmpty()) {
            ProfileFragment_et_new_password.setError(getString(R.string.password_new_required));
            ProfileFragment_et_new_password.setBackgroundResource(R.drawable.custom_bg_et_error);
            ProfileFragment_et_new_password.setFocusable(true);
            return false;
        } else {
            if (input_new_password.length() < 6) {
                ProfileFragment_et_new_password.setError(getString(R.string.password_new_short));
                ProfileFragment_et_new_password.setBackgroundResource(R.drawable.custom_bg_et_error);
                ProfileFragment_et_new_password.setFocusable(true);
                return false;
            }
            ProfileFragment_et_new_password.setError(null);
            ProfileFragment_et_new_password.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }

    private boolean validateConfirmNewPass() {
        input_confirm_new_password = ProfileFragment_et_confirm_new_password.getText().toString().trim();
        if (input_confirm_new_password.isEmpty()) {
            ProfileFragment_et_confirm_new_password.setError(getString(R.string.password_confirmation_required));
            ProfileFragment_et_confirm_new_password.setBackgroundResource(R.drawable.custom_bg_et_error);
            ProfileFragment_et_confirm_new_password.setFocusable(true);
            return false;
        } else {
            if (!input_new_password.equals(input_confirm_new_password)) {
                ProfileFragment_et_confirm_new_password.setError(getString(R.string.password_not_match));
                ProfileFragment_et_confirm_new_password.setBackgroundResource(R.drawable.custom_bg_et_error);
                ProfileFragment_et_confirm_new_password.setFocusable(true);
                return false;
            }
            ProfileFragment_et_confirm_new_password.setError(null);
            ProfileFragment_et_confirm_new_password.setBackgroundResource(R.drawable.custom_bg_et);
            return true;

        }

    }


    //for listener edit text change
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s == ProfileFragment_et_full_name.getEditableText()) {
            ProfileFragment_et_full_name.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == ProfileFragment_et_current_password.getEditableText()) {
            ProfileFragment_et_current_password.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == ProfileFragment_et_new_password.getEditableText()) {
            ProfileFragment_et_new_password.setBackgroundResource(R.drawable.custom_bg_et);
        } else if (s == ProfileFragment_et_confirm_new_password.getEditableText()) {
            ProfileFragment_et_confirm_new_password.setBackgroundResource(R.drawable.custom_bg_et);
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
