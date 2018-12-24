package janhric.vocabularytester.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;

/**
 * Created by Honza on 12/21/2018.
 */
public class UnitCRUD {
    private final DBHelper dbHelper;

    public UnitCRUD(Context context){
        dbHelper = new DBHelper(context);
    }

    public Unit findUnit(int id) {
        Unit unit;
        Cursor cursor = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Unit.KEY_ID + ',' +
                Unit.KEY_NAME +
                " FROM " + Unit.TABLE +
                " WHERE id = " + id;


        try {
            cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                unit = new Unit(
                    cursor.getInt(cursor.getColumnIndex(Unit.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Unit.KEY_NAME))
                );
                return unit;
            } else {
                return null;
            }
        }finally {
            cursor.close();
        }
    }
    
    public void saveUnit(Unit unit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Unit.KEY_NAME, unit.getName());

        if (unit.getId() <= 0) {
            int id = (int) db.insert(Unit.TABLE, null, values);
            unit.setId(id);
        } else {
            db.update(Unit.TABLE, values, Unit.KEY_ID + "= ?", new String[]{String.valueOf(unit.getId())});
        }
        db.close();
    }
    
    public void deleteUnit(Unit unit) { SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Phrase.TABLE, Phrase.KEY_UNIT + "= ?", new String[]{String.valueOf(unit.getId())});
        db.delete(Unit.TABLE, Unit.KEY_ID + "= ?", new String[]{String.valueOf(unit.getId())});
        db.close();
    }
    
    public List<Unit> getAllUnits() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Unit.KEY_ID + ',' +
                Unit.KEY_NAME +
                " FROM " + Unit.TABLE +
                " ORDER BY name Asc";
        List<Unit> unitList = new ArrayList<Unit>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Unit unit = new Unit(
                        cursor.getInt(cursor.getColumnIndex(Unit.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Unit.KEY_NAME))
                );
                unitList.add(unit);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return unitList;
    }
}
