package com.mykholy.myuniversity.ui.dialog;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mykholy.myuniversity.R;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDeveloperFragment extends androidx.fragment.app.DialogFragment {
    TextView DialogDeveloperFragment_tv_facebook, DialogDeveloperFragment_tv_phone;

    public DialogDeveloperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_developer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogDeveloperFragment_tv_facebook = view.findViewById(R.id.DialogDeveloperFragment_tv_facebook);
        DialogDeveloperFragment_tv_phone = view.findViewById(R.id.DialogDeveloperFragment_tv_phone);

        DialogDeveloperFragment_tv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setPackage("com.facebook.orca");
                intent.setData(Uri.parse("https://m.me/" + "3syer"));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    DynamicToast.makeWarning(Objects.requireNonNull(getActivity()), "Messenger app not installed").show();
                }

            }
        });

        DialogDeveloperFragment_tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+201064754874"));
                startActivity(intent);
            }
        });
    }
}
