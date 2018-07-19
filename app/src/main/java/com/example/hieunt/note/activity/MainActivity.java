package com.example.hieunt.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.hieunt.note.database.DatabaseQuery;
import com.example.hieunt.note.R;
import com.example.hieunt.note.base.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    String TAG = "MainActivity";

    private DatabaseQuery db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseQuery.getInstance(this);
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
        super.onResume();
        Log.d(TAG,"size : " + db.getAllNote().size());
    }
}
