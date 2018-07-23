package com.example.hieunt.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hieunt.note.R;

import java.io.File;
import java.util.ArrayList;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> listImagePath = new ArrayList<>();
    private IIvCancelClickListener ivCancelClickListener;

    public ListImageAdapter(Context context, ArrayList<String> listImagePath, IIvCancelClickListener ivCancelClickListener) {
        this.context = context;
        this.listImagePath = listImagePath;
        this.ivCancelClickListener = ivCancelClickListener;
    }

    public void setListImagePath(ArrayList<String> list) {
        listImagePath.clear();
        listImagePath.addAll(list);
    }

    public void removeImage(int pos) {
        listImagePath.remove(pos);
        notifyDataSetChanged();
    }

    public void addImage(String path) {
        listImagePath.add(path);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        final String path = listImagePath.get(i);
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.ivNote.setImageBitmap(myBitmap);
            holder.ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ivCancelClickListener.IvCancelClick(i);
                }
            });

            holder.ivNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
                    context.startActivity(intent);
                }
            });
        } else {
            listImagePath.remove(i);
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return listImagePath.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNote, ivCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNote = itemView.findViewById(R.id.iv_note);
            ivCancel = itemView.findViewById(R.id.iv_canncel_image);
        }
    }

    public interface IIvCancelClickListener {
        void IvCancelClick(int pos);
    }
}
