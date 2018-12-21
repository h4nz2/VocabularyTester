package janhric.vocabularytester.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;

/**
 * Created by Honza on 12/21/2018.
 */
public class PhraseCRUD {
    private final DBHelper dbHelper;
    private Context context;

    public PhraseCRUD(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void addPhrase(Phrase phrase){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Phrase.KEY_CZECH, phrase.getCzechPhrase());
        values.put(Phrase.KEY_ENGLISH, phrase.getEnglishPhrase());
        values.put(Phrase.KEY_UNIT, phrase.getUnit().getId());

        db.insert(Phrase.TABLE, null, values);
        db.close();
    }

    public List<Phrase> getAllPhrases(){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Phrase.KEY_ID + ',' +
                Phrase.KEY_CZECH + ',' +
                Phrase.KEY_ENGLISH + ',' +
                Phrase.KEY_UNIT +
                " FROM " + Phrase.TABLE;
        List<Phrase> PhraseList = new ArrayList<Phrase>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            UnitCRUD unitCRUD = new UnitCRUD(this.context);
            do {
                Phrase phrase = new Phrase(
                        cursor.getInt(cursor.getColumnIndex(Phrase.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Phrase.KEY_CZECH)),
                        cursor.getString(cursor.getColumnIndex(Phrase.KEY_ENGLISH)),
                        unitCRUD.findUnit(cursor.getInt(cursor.getColumnIndex(Phrase.KEY_UNIT)))
                );
                PhraseList.add(phrase);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return PhraseList;
    }

    /*public void updatePhrase(Phrase Phrase){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Phrase.KEY_NAME, Phrase.getName());
        values.put(Phrase.KEY_TYPE, Phrase.getType().ordinal());
        values.put(Phrase.KEY_INFUSIONS, Phrase.getInfusions());
        db.update(Phrase.TABLE, values, Phrase.KEY_ID + "= ?", new String[]{String.valueOf(Phrase.getID())});
        db.close();
    }*/

    public void deletePhrase(Phrase Phrase){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Phrase.TABLE, Phrase.KEY_ID + "= ?", new String[]{String.valueOf(Phrase.getId())});
        db.close();
    }

    public List<Phrase> getUnitPhrases(Unit unit){
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Phrase.KEY_ID + ',' +
                Phrase.KEY_CZECH + ',' +
                Phrase.KEY_ENGLISH + ',' +
                Phrase.KEY_UNIT +
                " FROM " + Phrase.TABLE +
                " WHERE " + Phrase.KEY_UNIT +
                " = " + unit.getId();
        List<Phrase> PhraseList = new ArrayList<Phrase>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            UnitCRUD unitCRUD = new UnitCRUD(this.context);
            do {
                Phrase phrase = new Phrase(
                        cursor.getInt(cursor.getColumnIndex(Phrase.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Phrase.KEY_CZECH)),
                        cursor.getString(cursor.getColumnIndex(Phrase.KEY_ENGLISH)),
                        unitCRUD.findUnit(cursor.getInt(cursor.getColumnIndex(Phrase.KEY_UNIT)))
                );
                PhraseList.add(phrase);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return PhraseList;
    }
}