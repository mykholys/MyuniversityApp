package com.mykholy.myuniversity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mykholy.myuniversity.MyInterface.OnRecyclerViewExam;
import com.mykholy.myuniversity.MyInterface.OnRecyclerViewLecture;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Exam;
import com.mykholy.myuniversity.model.Lecture;
import com.mykholy.myuniversity.utilities.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.MyViewHolder> {

    private Context context;
    private List<Exam> data;
    private OnRecyclerViewExam listener;


    public ExamAdapter(Context context, List<Exam> data, OnRecyclerViewExam listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_exam, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        Exam exam = data.get(position);
        holder.rec_exam_exam_name.setText(exam.getTitle());

        holder.rec_exam_start_date.setText(exam.getStartDate().split(" ")[0]);
        holder.rec_exam_end_date.setText(exam.getEndDate().split(" ")[0]);

        try {
            holder.rec_exam_start_time.setText(convertTimeTo12(exam.getStartDate().split(" ")[1]));
            holder.rec_exam_end_time.setText(convertTimeTo12(exam.getEndDate().split(" ")[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (exam.getStatus().equals("active")) {
            holder.rec_exam_status.setText(context.getString(R.string.active));
            holder.rec_exam_status.setBackgroundResource(R.drawable.bg_active);
        } else if (exam.getStatus().equals("pending")) {
            holder.rec_exam_status.setText(context.getString(R.string.pending));
            holder.rec_exam_status.setBackgroundResource(R.drawable.bg_pending);
        } else {
            holder.rec_exam_status.setText(context.getString(R.string.finished));
            holder.rec_exam_status.setBackgroundResource(R.drawable.bg_finished);
        }

        if (Constants.getSPreferences(context).getLanguage().equals("ar")) {
            holder.rec_exam_start_time.setGravity(Gravity.END);
            holder.rec_exam_end_time.setGravity(Gravity.END);
        }


        holder.rec_exam_ly.setOnClickListener(new View.OnClickListener() {
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
        RelativeLayout rec_exam_ly;

        TextView rec_exam_exam_name, rec_exam_start_date, rec_exam_end_date, rec_exam_status, rec_exam_start_time, rec_exam_end_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_exam_ly = itemView.findViewById(R.id.rec_exam_ly);
            rec_exam_exam_name = itemView.findViewById(R.id.rec_exam_exam_name);
            rec_exam_start_date = itemView.findViewById(R.id.rec_exam_start_date);
            rec_exam_end_date = itemView.findViewById(R.id.rec_exam_end_date);
            rec_exam_status = itemView.findViewById(R.id.rec_exam_status);
            rec_exam_start_time = itemView.findViewById(R.id.rec_exam_start_time);
            rec_exam_end_time = itemView.findViewById(R.id.rec_exam_end_time);


        }
    }

    private String convertTimeTo12(String time) throws ParseException {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        Date _24HourDt = _24HourSDF.parse(time);

        return _12HourSDF.format(_24HourDt);
    }
}
