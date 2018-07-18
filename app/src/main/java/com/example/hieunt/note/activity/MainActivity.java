package com.example.hieunt.note.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.hieunt.note.R;
import com.example.hieunt.note.base.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setlayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.iv_add_note)
    public void addNote() {
        startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
    }
}
