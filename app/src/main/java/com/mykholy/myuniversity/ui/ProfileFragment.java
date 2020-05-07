package com.mykholy.myuniversity.ui;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.utilities.ConnectionUtils;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;


public class ProfileFragment extends Fragment implements TextWatcher {
    ImageView ProfileFragment_iv;
    TextView ProfileFragment_tv_name, ProfileFragment_tv_email;
    EditText ProfileFragment_et_full_name, ProfileFragment_et_current_password,
            ProfileFragment_et_new_password, ProfileFragment_et_confirm_new_password;
    androidx.appcompat.widget.AppCompatButton ProfileFragment_btn_update;

    private String input_full_name,input_current_password,input_new_password,input_confirm_new_password;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
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
        setData();
        setListener();
        ProfileFragment_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() | !validateCurrentPass()|!validateNewPass()|!validateConfirmNewPass()) {
                    return;

                } else {
                    if (ConnectionUtils.isConnected(Objects.requireNonNull(getActivity()))) {
                        DynamicToast.makeSuccess(getActivity(),"Updated Successfully").show();
                    }

                }
            }
        });
    }

    private void setUi(View view){
        ProfileFragment_iv = view.findViewById(R.id.ProfileFragment_iv);
        ProfileFragment_tv_name = view.findViewById(R.id.ProfileFragment_tv_name);
        ProfileFragment_tv_email = view.findViewById(R.id.ProfileFragment_tv_email);
        ProfileFragment_et_full_name = view.findViewById(R.id.ProfileFragment_et_full_name);
        ProfileFragment_et_current_password = view.findViewById(R.id.ProfileFragment_et_current_password);
        ProfileFragment_et_new_password = view.findViewById(R.id.ProfileFragment_et_new_password);
        ProfileFragment_et_confirm_new_password = view.findViewById(R.id.ProfileFragment_et_confirm_new_password);
        ProfileFragment_btn_update = view.findViewById(R.id.ProfileFragment_btn_update);
    }
    private void setData(){

        //setImageProfile
        Glide.with(this)
                .load("https://i.pinimg.com/736x/da/3b/4b/da3b4b2d4e6bcb5b4551b3de06ee93d2.jpg")
                .apply(RequestOptions.circleCropTransform())
                .into(ProfileFragment_iv);

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
        input_current_password= ProfileFragment_et_current_password.getText().toString().trim();
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
        input_new_password= ProfileFragment_et_new_password.getText().toString().trim();
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
           if(!input_new_password.equals(input_confirm_new_password)) {
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
