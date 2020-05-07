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
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewCourse;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewLecture;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Course;
import com.mykholy.myuniversity.model.Lecture;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.MyViewHolder> {

    private Context context;
    private List<Lecture> data;
    private OnRecyclerViewLecture listener;


    public LectureAdapter(Context context, List<Lecture> data, OnRecyclerViewLecture listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_lecture, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.rec_lecture_tv_lec_name.setText(data.get(position).getName());
        holder.rec_lecture_ly.setOnClickListener(new View.OnClickListener() {
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
        RelativeLayout rec_lecture_ly;
        ImageView rec_lecture_iv_download;
        TextView rec_lecture_tv_lec_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_lecture_ly = itemView.findViewById(R.id.rec_lecture_ly);
            rec_lecture_iv_download = itemView.findViewById(R.id.rec_lecture_iv_download);
            rec_lecture_tv_lec_name = itemView.findViewById(R.id.rec_lecture_tv_lec_name);

        }
    }
}
