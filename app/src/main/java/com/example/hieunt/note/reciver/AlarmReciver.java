package com.example.hieunt.note.reciver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.hieunt.note.R;
import com.example.hieunt.note.activity.DetailActivity;
import com.example.hieunt.note.activity.MainActivity;
import com.example.hieunt.note.model.Note;
import com.example.hieunt.note.utils.Constant;

public class AlarmReciver extends BroadcastReceiver {
    String TAG = "AlarmReciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "receiver");
        int id = intent.getIntExtra(Constant.NOTE_ID, 0);
        Log.d(TAG,"id : " + id);
        String title = intent.getStringExtra(Constant.TITLE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setAutoCancel(true); // clear notification after click

        Intent notifyIntent = new Intent(context, DetailActivity.class);
        notifyIntent.putExtra(Constant.NOTE_ID,id);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
