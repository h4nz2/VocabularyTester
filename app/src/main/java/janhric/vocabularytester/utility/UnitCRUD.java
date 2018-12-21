package janhric.vocabularytester.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
}
