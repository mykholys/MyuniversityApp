package com.mykholy.myuniversity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mykholy.myuniversity.MyInterface.OnItemClickListener;
import com.mykholy.myuniversity.R;
import com.mykholy.myuniversity.model.Dialog;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MyViewHolder> {

    private ArrayList<Dialog> dialogs;
    private OnItemClickListener listener;


    public DialogAdapter(ArrayList<Dialog> dialogs, OnItemClickListener listener) {
        this.dialogs = dialogs;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_dialog, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvTitle.setText(dialogs.get(position).getName());
        holder.rec_dialog_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(dialogs.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dialogs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        RelativeLayout rec_dialog_ly;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.rec_dialog_tv_title);
            rec_dialog_ly = itemView.findViewById(R.id.rec_dialog_ly);


        }
    }
}
