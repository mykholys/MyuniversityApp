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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mykholy.myuniversity.MyInterface.OnItemClickListener;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.DialogAdapter;
import com.mykholy.myuniversity.model.Dialog;

import java.util.ArrayList;

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
    private static final String ARG_AcademicYear = "academic_year";


    // TODO: Rename and change types of parameters
    private String title;
    private String subTitle;
    private int academic_year;


    private TextView tvTitle;
    private TextView tvSubTitle;
    private RecyclerView rv;
    private DialogAdapter adapter;

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

    public static DialogFragment newInstance(String title, String sub_title, int academic_year) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUB_TITLE, sub_title);
        args.putInt(ARG_AcademicYear, academic_year);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            subTitle = getArguments().getString(ARG_SUB_TITLE);


            if (getArguments().getInt(ARG_AcademicYear) != 0) {
                academic_year = getArguments().getInt(ARG_AcademicYear);
                Log.i(ARG_AcademicYear, String.valueOf(academic_year));
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
            mdialogs.add(new Dialog(getString(R.string.male)));
            mdialogs.add(new Dialog(getString(R.string.female)));

        } else if (title.equals(getString(R.string.academic_year))) {
            mdialogs.add(new Dialog("1"));
            mdialogs.add(new Dialog("2"));
            mdialogs.add(new Dialog("3"));
            mdialogs.add(new Dialog("4"));
        } else if (title.equals(getString(R.string.department))) {
            if (academic_year == 1 | academic_year == 2) {
                mdialogs.add(new Dialog(1,"GN"));
                mdialogs.add(new Dialog(2,"SW"));
                mdialogs.add(new Dialog(3,"BIO"));
            } else if (academic_year == 3 | academic_year == 4) {
                mdialogs.add(new Dialog(4,"CS"));
                mdialogs.add(new Dialog(5,"IT"));
                mdialogs.add(new Dialog(6,"IS"));
                mdialogs.add(new Dialog(7,"SW"));
                mdialogs.add(new Dialog(8,"BIO"));
            }
        }
        else if(title.equals(getString(R.string.menu_settings))){
            mdialogs.add(new Dialog("English"));
            mdialogs.add(new Dialog("العربية"));
        }


        return mdialogs;
    }


}
