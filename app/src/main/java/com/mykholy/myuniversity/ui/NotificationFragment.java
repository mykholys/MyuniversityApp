package com.mykholy.myuniversity.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.mykholy.myuniversity.API.API_Interface;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewNotification;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.adapter.CourseAdapter;
import com.mykholy.myuniversity.adapter.NotificationAdapter;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Notification;
import com.mykholy.myuniversity.utilities.Constants;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {
    private TextView NotificationFragment_tv_no_notifications;
    private RecyclerView NotificationFragment_rv;
    private LoadingView loadingView;
    private List<Notification> notifications;
    private NotificationAdapter adapter;
    private API_Interface api_interface;


    private OnFragmentInteractionListener mListener;

    public NotificationFragment() {
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUi(view);
        setApi();
        loadData();
    }

    private void setUi(View view) {

        NotificationFragment_rv = view.findViewById(R.id.NotificationFragment_rv);
        NotificationFragment_tv_no_notifications = view.findViewById(R.id.NotificationFragment_tv_no_notifications);

        loadingView = view.findViewById(R.id.loadingView);
        loadingView.stop();


    }


    private void setApi() {

        api_interface = AppClient.getClient().create(API_Interface.class);
    }

    private void loadData() {
        loadingView.start();
        String token = Constants.getSPreferences(getActivity()).getType_Token() + " " + Constants.getSPreferences(getActivity()).getToken();

        notifications = new ArrayList<>();

        Call<List<Notification>> call = api_interface.getAllNotifications(token, Constants.getSPreferences(getActivity()).getSTUDENT_ACADEMIC_YEAR(), Constants.getSPreferences(getActivity()).getSTUDENT_DEPT_ID());
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<List<Notification>> call, @NonNull Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        NotificationFragment_tv_no_notifications.setVisibility(View.INVISIBLE);
                        notifications.addAll(response.body());
                        adapter = new NotificationAdapter(getActivity(), notifications, new OnRecyclerViewNotification() {
                            @Override
                            public void OnClickItemListener(Notification notification) {
                                mListener.onFragmentInteraction(notification);

                            }
                        });
                        NotificationFragment_rv.setAdapter(adapter);
                        ;
                        NotificationFragment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    } else
                        NotificationFragment_tv_no_notifications.setVisibility(View.VISIBLE);

                }
                loadingView.stop();

            }

            @Override
            public void onFailure(@NonNull Call<List<Notification>> call, @NonNull Throwable t) {
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
        void onFragmentInteraction(Notification notification);
    }
}
