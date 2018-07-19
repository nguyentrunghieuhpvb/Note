package com.example.hieunt.note.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Note extends RealmObject implements Serializable{
    @PrimaryKey
    private int id;

    private String title;
    private String Content;
    private String timeNote;
    private String hourAlarm;
    private String minuteAlarm;
    private String DateAlarm;
    private String imagePath ;
    private int color;
    private boolean alarm = false;
    public Note() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeNote() {
        return timeNote;
    }

    public void setTimeNote(String timeNote) {
        this.timeNote = timeNote;
    }

    public String getHourAlarm() {
        return hourAlarm;
    }

    public void setHourAlarm(String hourAlarm) {
        this.hourAlarm = hourAlarm;
    }

    public String getMinuteAlarm() {
        return minuteAlarm;
    }

    public void setMinuteAlarm(String minuteAlarm) {
        this.minuteAlarm = minuteAlarm;
    }

    public String getDateAlarm() {
        return DateAlarm;
    }

    public void setDateAlarm(String dateAlarm) {
        DateAlarm = dateAlarm;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
