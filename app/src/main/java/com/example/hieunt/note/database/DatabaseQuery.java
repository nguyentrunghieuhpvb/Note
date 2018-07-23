package com.example.hieunt.note.database;

import android.content.Context;
import android.util.Log;

import com.example.hieunt.note.model.Note;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseQuery {
    private static final DatabaseQuery ourInstance = new DatabaseQuery();
    private Context context;
    private Realm realm;

    public static DatabaseQuery getInstance(Context context) {
        ourInstance.context = context;
        ourInstance.realm = Realm.getDefaultInstance();
        return ourInstance;
    }

    private DatabaseQuery() {
    }

    public ArrayList<Note> getAllNote() {
        ArrayList<Note> listNote = new ArrayList<>();
        RealmResults<Note> rs = realm.where(Note.class).findAll();
        for (Note note : rs) {
            listNote.add(note);
        }
        realm.close();
        return listNote;
    }

    public int getNoteId() {
        if (realm.where(Note.class).findAll().size() > 0) {
            int id_new = realm.where(Note.class).max("id").intValue() + 1;
            return id_new;
        } else {
            return 1;
        }
    }

    public void addNote(Note note) {
        realm.beginTransaction();
        realm.insertOrUpdate(note);
        realm.commitTransaction();
    }

    public void closeRealm() {
        if (!realm.isClosed()) {
            realm.close();
        }

    }

    public Note getNote(int id) {
        Note note = realm.where(Note.class).equalTo("id", id).findFirst();
        return note;
    }

    public void updateNote(Note x) {
        Note note = realm.where(Note.class).equalTo("id", x.getId()).findFirst();
        realm.beginTransaction();
        note.setTitle(x.getTitle());
        note.setContent(x.getContent());
        note.setColor(x.getColor());
        note.setListImage(x.getListImage());
        note.setAlarm(x.isAlarm());
        note.setDayCreate(x.getDayCreate());
        note.setDate(x.getDate());
        note.setTime(x.getTime());
        realm.commitTransaction();
    }

    public void deleteNote(final Note note) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Note> rows = realm.where(Note.class).equalTo("id", note.getId()).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }
}
