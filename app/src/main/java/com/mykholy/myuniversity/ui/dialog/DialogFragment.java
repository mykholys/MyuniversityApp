package com.mykholy.myuniversity.ui.dialog;


import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnItemClickListener;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.DialogAdapter;
import com.mykholy.myuniversity.model.Department;
import com.mykholy.myuniversity.model.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_SUB_TITLE = "sub_title";
    private static final String ARG_DialogDepartment = "department";


    // TODO: Rename and change types of parameters
    private String title;
    private String subTitle;
    private ArrayList<Dialog> dialogDepartment;


    private TextView tvTitle;
    private TextView tvSubTitle;
    private RecyclerView rv;
    private DialogAdapter adapter;
    private API_Interface api_interface;

    ArrayList<Dialog> DialogDepartment = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public DialogFragment() {
        // Required empty public constructor
    }


    public static DialogFragment newInstance(String title, String sub_title) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUB_TITLE, sub_title);

        fragment.setArguments(args);
        return fragment;
    }

    public static DialogFragment newInstance(String title, String sub_title, ArrayList<Dialog> DialogDepartment) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUB_TITLE, sub_title);
        args.putSerializable(ARG_DialogDepartment, DialogDepartment);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            subTitle = getArguments().getString(ARG_SUB_TITLE);


            if (getArguments().getSerializable(ARG_DialogDepartment) != null) {
                dialogDepartment = (ArrayList<Dialog>) getArguments().getSerializable(ARG_DialogDepartment);
                assert dialogDepartment != null;
                Log.i(ARG_DialogDepartment, String.valueOf(dialogDepartment.size()));
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.DialogFragment_tv_title);
        tvSubTitle = view.findViewById(R.id.DialogFragment_tv_sub_title);
        rv = view.findViewById(R.id.DialogFragment_rv);

        tvTitle.setText(title);
        tvSubTitle.setText(subTitle);

        api_interface = AppClient.getClient().create(API_Interface.class);

        adapter = new DialogAdapter(getDialogs(), new OnItemClickListener() {
            @Override
            public void OnItemClick(Dialog dialog) {
                mListener.onFragmentInteraction(dialog, title);
                dismiss();
            }
        });

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Dialog dialog, String title);
    }

    private ArrayList<Dialog> getDialogs() {

        ArrayList<Dialog> mdialogs = new ArrayList<>();
        mdialogs.clear();
        if (title.equals(getString(R.string.state))) {

            mdialogs.add(new Dialog(getString(R.string.alexandria)));
            mdialogs.add(new Dialog(getString(R.string.aswan)));
            mdialogs.add(new Dialog(getString(R.string.asyut)));
            mdialogs.add(new Dialog(getString(R.string.beheira)));
            mdialogs.add(new Dialog(getString(R.string.beni_suef)));
            mdialogs.add(new Dialog(getString(R.string.cairo)));
            mdialogs.add(new Dialog(getString(R.string.dakahlia)));
            mdialogs.add(new Dialog(getString(R.string.damietta)));
            mdialogs.add(new Dialog(getString(R.string.faiyum)));
            mdialogs.add(new Dialog(getString(R.string.gharbia)));
            mdialogs.add(new Dialog(getString(R.string.giza)));
            mdialogs.add(new Dialog(getString(R.string.ismailia)));
            mdialogs.add(new Dialog(getString(R.string.kafr_el_sheikh)));
            mdialogs.add(new Dialog(getString(R.string.luxor)));
            mdialogs.add(new Dialog(getString(R.string.matruh)));
            mdialogs.add(new Dialog(getString(R.string.minya)));
            mdialogs.add(new Dialog(getString(R.string.monufia)));
            mdialogs.add(new Dialog(getString(R.string.new_valley)));
            mdialogs.add(new Dialog(getString(R.string.north_sina)));
            mdialogs.add(new Dialog(getString(R.string.port_said)));
            mdialogs.add(new Dialog(getString(R.string.qalyubia)));
            mdialogs.add(new Dialog(getString(R.string.qena)));
            mdialogs.add(new Dialog(getString(R.string.red_sea)));
            mdialogs.add(new Dialog(getString(R.string.sharqia)));
            mdialogs.add(new Dialog(getString(R.string.sohag)));
            mdialogs.add(new Dialog(getString(R.string.south_sinai)));
            mdialogs.add(new Dialog(getString(R.string.suez)));


        } else if (title.equals(getString(R.string.gender))) {
            mdialogs.add(new Dialog(1, getString(R.string.male)));
            mdialogs.add(new Dialog(2, getString(R.string.female)));

        } else if (title.equals(getString(R.string.academic_year))) {
            mdialogs.add(new Dialog("1"));
            mdialogs.add(new Dialog("2"));
            mdialogs.add(new Dialog("3"));
            mdialogs.add(new Dialog("4"));
        } else if (title.equals(getString(R.string.department))) {
            mdialogs = dialogDepartment;


        } else if (title.equals(getString(R.string.menu_settings))) {
            mdialogs.add(new Dialog("English"));
            mdialogs.add(new Dialog("العربية"));

        }


        return mdialogs;
    }


}
