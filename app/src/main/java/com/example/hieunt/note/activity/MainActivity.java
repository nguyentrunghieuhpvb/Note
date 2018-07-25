package com.example.hieunt.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.hieunt.note.adapter.ListNodeAdapter;
import com.example.hieunt.note.database.DatabaseQuery;
import com.example.hieunt.note.R;
import com.example.hieunt.note.base.BaseActivity;
import com.example.hieunt.note.model.Note;
import com.example.hieunt.note.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rc_note)
    RecyclerView rvNote;
    private DatabaseQuery db;
    private ArrayList<Note> listNode;
    private ListNodeAdapter adapterNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DisplayMetrics displayMetrics = getApplication().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numOfColumns = (int) (dpWidth / 180);
        adapterNode = new ListNodeAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, numOfColumns, GridLayoutManager.VERTICAL, false);
        rvNote.setLayoutManager(manager);
        rvNote.addItemDecoration(new GridSpacingItemDecoration(numOfColumns, 20, true));
        rvNote.setAdapter(adapterNode);
    }

    @Override
    public int setlayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.iv_add_note)
    public void addNote() {
        startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
    }


    @Override
    protected void onResume() {
        db = DatabaseQuery.getInstance(this);
        listNode = db.getAllNote();
        Collections.sort(listNode, new Comparator<Note>() {
            @Override
            public int compare(Note note, Note x) {
                return x.getId() - note.getId();
            }
        });
        adapterNode.setListNote(listNode);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
