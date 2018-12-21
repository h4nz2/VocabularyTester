package janhric.vocabularytester.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;

/**
 * Created by Honza on 12/21/2018.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "VocabularyTester.db";
    private final Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TEAS = "CREATE TABLE " + Phrase.TABLE + '('
                + Phrase.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Phrase.KEY_CZECH + " TEXT, "
                + Phrase.KEY_ENGLISH + " TEXT, "
                + Phrase.KEY_UNIT + " INTEGER, ) NOT NULL";
        db.execSQL(CREATE_TABLE_TEAS);

        String CREATE_TABLE_SHOPS = "CREATE TABLE " + Unit.TABLE + '('
                + Unit.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Unit.KEY_NAME + " TEXT, )";
        db.execSQL(CREATE_TABLE_SHOPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Phrase.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Unit.TABLE);
        onCreate(db);
    }
}
