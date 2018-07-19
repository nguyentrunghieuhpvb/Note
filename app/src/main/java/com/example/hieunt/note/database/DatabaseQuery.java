package com.example.hieunt.note.database;

import android.content.Context;

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
        realm.beginTransaction();
        RealmResults<Note> rs = realm.where(Note.class).findAll();
        for (Note note : rs) {
           listNote.add(note);
        }
        return listNote;
    }
}
