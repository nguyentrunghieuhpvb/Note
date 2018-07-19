package com.example.hieunt.note.reciver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.example.hieunt.note.model.Note;

public class AlarmReciver extends BroadcastReceiver {
    String TAG = "AlarmReciver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"receiver");
        Note mNote = (Note) intent.getSerializableExtra("note");
        Log.d(TAG,"title : " + mNote.getTitle());
    }
}
