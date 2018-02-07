package guaz.wykrywaczpalindromow20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "RESULTS";

    public static final String _COL2 = "CONTENT";
    public static final String _COL3 = "RESULT";

    String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( " +
            _ID + " INTEGER PRIMARY KEY," +
            _COL2 + " TEXT , " +
            _COL3 + " TEXT  " +
            " ) ";

    String CLEAR_RESULTS_TABLE = "DELETE FROM " + TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String content, boolean result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_COL2, content);
        contentValues.put(_COL3, result);
        long insertResult = db.insert(TABLE_NAME, null, contentValues);

        //Log.d("wpis", "insertData: "+ insertResult);
        if (insertResult == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getResult = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResult;
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL(CLEAR_RESULTS_TABLE);
    }
}