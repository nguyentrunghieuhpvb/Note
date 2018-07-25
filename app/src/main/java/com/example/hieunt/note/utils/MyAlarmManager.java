package com.example.hieunt.note.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.hieunt.note.model.Note;
import com.example.hieunt.note.receiver.AlarmReceiver;

import java.util.Calendar;

public class MyAlarmManager {
    private Context context;
    public MyAlarmManager(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addAlarm(Note note) {
        Calendar cal = Calendar.getInstance();
        String date = note.getDate();
        int day = Integer.parseInt(date.substring(0, date.indexOf("/")));
        int month = Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
        int year = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));
        String time = note.getTime();
        int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
        int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
        cal.set(year, month - 1, day, hour, minute, 0);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(Constant.NOTE_ID, note.getId());
        intent.putExtra(Constant.TITLE, note.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, note.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    public void removeAalrm(Note note) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, note.getId(), myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
