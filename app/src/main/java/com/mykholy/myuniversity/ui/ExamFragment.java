package com.mykholy.myuniversity.ui;

import android.content.Context;
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
import android.widget.TextView;

import com.github.loadingview.LoadingView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewExam;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.ExamAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.ui.dialog.SortDialogFragment;
import com.mykholy.myuniversity.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ExamFragment extends Fragment {
    private RecyclerView ExamFragment_rv;
    private List<Exam> examList;
    private ExamAdapter adapter;
    private FloatingActionButton ExamFragment_fab;
    private API_Interface api_interface;
    private LoadingView loadingView;
    private TextView ExamFragment_tv_no_exam;

    private static final String ARG_COURSE = "course";
    private static final String ARG_TYPE_EXAMS = "type_exams";


    // TODO: Rename and change types of parameters
    private Course mCourse;
    private String mType_exmas;


    private OnFragmentInteractionListener mListener;

    public ExamFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(Course course, String type_exam) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        args.putString(ARG_TYPE_EXAMS, type_exam);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourse = (Course) getArguments().getSerializable(ARG_COURSE);
            mType_exmas = getArguments().getString(ARG_TYPE_EXAMS);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);
        setApi();

        if (mType_exmas.equals("unsolved"))
            setDataUnSolvedExam();
        else
            setDataSolvedExam();


        ExamFragment_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortDialogFragment fragment = SortDialogFragment.newInstance(mType_exmas);
                fragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
            }
        });
    }

    private void setUi(View view) {
        ExamFragment_rv = view.findViewById(R.id.ExamFragment_rv);
        ExamFragment_fab = view.findViewById(R.id.ExamFragment_fab);
        ExamFragment_tv_no_exam = view.findViewById(R.id.ExamFragment_tv_no_exam);
        loadingView = view.findViewById(R.id.loadingView);
    }

    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }

    private void setDataUnSolvedExam() {

        loadDataUnSolvedExam();


    }

    private void setDataSolvedExam() {

        loadDataSolvedExam();


    }

    private void loadDataUnSolvedExam() {
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();

        examList = new ArrayList<>();
        Call<List<Exam>> call = api_interface.getAllUnSolvedExam(token, Constants.getSPreferences(getActivity()).getSTUDENT_ID(), mCourse.getCId());
        call.enqueue(new Callback<List<Exam>>() {

            @Override
            public void onResponse(@NonNull Call<List<Exam>> call, @NonNull Response<List<Exam>> response) {
                Log.i("getAllUnSolvedExam", "before if" + response.toString());

                if (response.isSuccessful()) {
                    Log.i("getAllUnSolvedExam", "in Successful" + response.toString());
                    Log.i("getAllUnSolvedExam", "in Successful" + response.body().toString());
                    Log.i("getAllUnSolvedExam", "in Successful size" + response.body().size());

                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        examList.addAll(response.body());
                        adapter = new ExamAdapter(getActivity(), mType_exmas, examList, new OnRecyclerViewExam() {
                            @Override
                            public void OnClickItemListener(Exam exam, String exam_type) {
                                mListener.onFragmentInteraction(exam, exam_type);

                            }
                        });
                        ExamFragment_rv.setAdapter(adapter);

                        ExamFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ExamFragment_tv_no_exam.setVisibility(View.INVISIBLE);
                    }

                }  else if (response.code() == 404) {
                    Log.i("getAllSolvedExam", "in 404 ");
                    ExamFragment_tv_no_exam.setVisibility(View.VISIBLE);
                }


                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<List<Exam>> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });

    }

    private void loadDataSolvedExam() {
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();

        examList = new ArrayList<>();
        Call<List<Exam>> call = api_interface.getAllSolvedExam(token, Constants.getSPreferences(getActivity()).getSTUDENT_ID(), mCourse.getCId());
        call.enqueue(new Callback<List<Exam>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exam>> call, @NonNull Response<List<Exam>> response) {
                Log.i("getAllSolvedExam", "before if" + response.toString());

                if (response.isSuccessful()) {
                    Log.i("getAllSolvedExam", "in Successful" + response.toString());
                    Log.i("getAllSolvedExam", "in Successful" + response.body().toString());
                    Log.i("getAllSolvedExam", "in Successful size" + response.body().size());
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        examList.addAll(response.body());
                        adapter = new ExamAdapter(getActivity(), mType_exmas, examList, new OnRecyclerViewExam() {
                            @Override
                            public void OnClickItemListener(Exam exam, String exam_type) {
                                mListener.onFragmentInteraction(exam, exam_type);

                            }
                        });
                        ExamFragment_rv.setAdapter(adapter);

                        ExamFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        ExamFragment_tv_no_exam.setVisibility(View.INVISIBLE);
                    }


                } else if (response.code() == 404) {
                    Log.i("getAllSolvedExam", "in 404 ");
                    ExamFragment_tv_no_exam.setVisibility(View.VISIBLE);
                }

                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<List<Exam>> call, @NonNull Throwable t) {
                loadingView.stop();
                Log.i("onFailure:", t.getMessage());
            }
        });

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Exam exam, String type_exam);
    }
}
