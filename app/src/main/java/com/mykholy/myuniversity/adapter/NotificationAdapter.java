package com.mykholy.myuniversity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewLecture;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewNotification;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Lecture;
import com.mykholy.myuniversity.model.Notification;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context context;
    private List<Notification> data;
    private OnRecyclerViewNotification listener;


    public NotificationAdapter(Context context, List<Notification> data, OnRecyclerViewNotification listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_notification, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.rec_notification_tv_title.setText(data.get(position).getTitle());
        holder.rec_notification_tv_date.setText(data.get(position).getCreatedAt());
        holder.rec_notification_tv_body.setText(data.get(position).getBody());
        holder.rec_notification_tv_course_name.setText(data.get(position).getCName());
        holder.rec_notification_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickItemListener(data.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rec_notification_ly;

        TextView rec_notification_tv_title, rec_notification_tv_date, rec_notification_tv_body, rec_notification_tv_course_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_notification_ly = itemView.findViewById(R.id.rec_notification_ly);
            rec_notification_tv_course_name = itemView.findViewById(R.id.rec_notification_tv_course_name);
            rec_notification_tv_title = itemView.findViewById(R.id.rec_notification_tv_title);
            rec_notification_tv_date = itemView.findViewById(R.id.rec_notification_tv_date);
            rec_notification_tv_body = itemView.findViewById(R.id.rec_notification_tv_body);

        }
    }

    private boolean checkIsFileExist(String... sUrl) {
        String[] fileName = sUrl[0].split("/");
        File myfile = new File(context.getExternalFilesDir("Download"), fileName[fileName.length - 1]);
        return myfile.exists();
    }
}
