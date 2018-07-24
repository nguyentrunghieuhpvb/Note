package com.example.hieunt.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieunt.note.R;

import java.util.ArrayList;

public class PopupAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listItem;

    public PopupAdapter(Context context, ArrayList<String> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    public void setListItem(ArrayList<String> list) {
        this.listItem.clear();
        this.listItem.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.item_popup_window, viewGroup, false);
        TextView tvItem = view1.findViewById(R.id.tv_item_pop_up);
        tvItem.setText(listItem.get(i));
        return view1;
    }
}
