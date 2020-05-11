package com.mykholy.myuniversity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mykholy.myuniversity.API.AppClient;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    private Context context;
    private List<Course> data;
    private OnRecyclerViewCourse listener;


    public CourseAdapter(Context context, List<Course> data, OnRecyclerViewCourse listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_course, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(AppClient.BASE_URL + "public/images/Courses/"+data.get(position).getCImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.rec_course_iv_course);

        holder.rec_course_tv_course_name.setText(data.get(position).getCName());
        holder.rec_course_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickItemListener(data.get(position));
            }
        });

        int[] bg = {R.drawable.mybg, R.drawable.mybg2, R.drawable.mybg3, R.drawable.mybg4, R.drawable.mybg5, R.drawable.mybg6};

        int n = (int) (0 + Math.random() * 6);

        holder.rec_course_ly.setBackgroundResource(bg[n]);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rec_course_ly;
        ImageView rec_course_iv_course;
        TextView rec_course_tv_course_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_course_ly = itemView.findViewById(R.id.rec_course_ly);
            rec_course_iv_course = itemView.findViewById(R.id.rec_course_iv_course);
            rec_course_tv_course_name = itemView.findViewById(R.id.rec_course_tv_course_name);

        }
    }
}
