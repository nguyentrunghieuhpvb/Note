package com.example.hieunt.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hieunt.note.R;
import com.example.hieunt.note.activity.DetailActivity;
import com.example.hieunt.note.model.Note;
import com.example.hieunt.note.utils.Constant;

import java.util.ArrayList;

public class ListNodeAdapter extends RecyclerView.Adapter<ListNodeAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Note> listNote;
    public ListNodeAdapter(Context context) {
        this.context = context;
    }
    public void setListNote(ArrayList<Note> listNote) {
        this.listNote = listNote;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final Note note = listNote.get(i);
        if (note.isAlarm()) {
            holder.ivAlarm.setVisibility(View.VISIBLE);
        } else {
            holder.ivAlarm.setVisibility(View.INVISIBLE);
        }
        holder.tvTime.setText(note.getDayCreate());
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Constant.NOTE_ID, note.getId());
                context.startActivity(intent);
            }
        });

        holder.cvNote.setBackgroundColor(note.getColor());
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAlarm;
        private TextView tvTitle, tvContent, tvTime;
        private CardView cvNote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlarm = itemView.findViewById(R.id.iv_alarm);
            tvContent = itemView.findViewById(R.id.tv_item_content);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvTime = itemView.findViewById(R.id.tv_item_time);
            cvNote = itemView.findViewById(R.id.cv_note);
        }
    }
}
