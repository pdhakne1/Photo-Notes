package com.project.coen268.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pallavi on 2/12/16.
 */
public class PhotoNoteDBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_CAPTION = "Caption";
    public static final String COLUMN_IMAGE_PATH = "ImagePath";

    public static final String DATABASE_TABLE = "PhotoNote";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhotoNoteDB";

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text)",
            DATABASE_TABLE, COLUMN_ID, COLUMN_CAPTION, COLUMN_IMAGE_PATH);

    public PhotoNoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
    public void insertPhotoNoteObject(PhotoNote photoNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COLUMN_CAPTION, photoNote.getCaption());
        newValues.put(COLUMN_IMAGE_PATH, photoNote.getImagePath());
        db.insert(DATABASE_TABLE, null, newValues);
        db.close();
    }

    public List<PhotoNote> getPhotoNoteObject() {

        final List<PhotoNote> photoNoteList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {COLUMN_ID, COLUMN_CAPTION, COLUMN_IMAGE_PATH};
        Cursor cursor = db.query(DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            PhotoNote photoNote = new PhotoNote();

            photoNote.setCaption(cursor.getString(1));
            photoNote.setImagePath(cursor.getString(2));
            photoNoteList.add(photoNote);
        }
        db.close();
        return photoNoteList;
    }
}
