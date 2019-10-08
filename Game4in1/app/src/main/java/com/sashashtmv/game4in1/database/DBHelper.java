package com.sashashtmv.game4in1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import androidx.annotation.Nullable;

import com.sashashtmv.game4in1.model.ModelLevel;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "my_levels_database";
    public static final String LEVELS_TABLE = "levels_table";

    public static final String LEVEL_WORD_COLUMN = "level_word";
    public static final String IMAGE_ONE_COLUMN = "image_one";
    public static final String IMAGE_TWO_COLUMN = "image_two";
    public static final String IMAGE_THREE_COLUMN = "image_three";
    public static final String IMAGE_FOUR_COLUMN = "image_four";
    public static final String LEVEL_STATUS_COLUMN = "level_status";
    public static final String LEVEL_TIME_STAMP_COLUMN = "level_time_stamp";

    private static final String LEVELS_TABLE_CREATE_SCRIPT = "CREATE TABLE " + LEVELS_TABLE + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LEVEL_WORD_COLUMN + " TEXT, " + IMAGE_ONE_COLUMN + " TEXT, " + IMAGE_TWO_COLUMN + " TEXT, " +
            IMAGE_THREE_COLUMN + " TEXT, " + IMAGE_FOUR_COLUMN + " TEXT, " + LEVEL_STATUS_COLUMN + " INTEGER, " + LEVEL_TIME_STAMP_COLUMN + " LONG);";

    private DBQueryManager mDBQueryManager;
    private DBUpdateManager mDBUpdateManager;
    //константа для выборки данных по статусу из таблицы
    // public static final String SELECTION_IMAGE_ONE = DBHelper.IMAGE_ONE_COLUMN + " = ?";
    public static final String SELECTION_WORD = LEVEL_WORD_COLUMN + " = ?";
    public static final String SELECTION_TIME_STAMP = LEVEL_TIME_STAMP_COLUMN + " = ?";
//    public static final String SELECTION_LIKE_TITLE = TASK_TITLE_COLUMN + " LIKE ?";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mDBQueryManager = new DBQueryManager(getReadableDatabase());
        mDBUpdateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LEVELS_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + LEVELS_TABLE);
        onCreate(db);
    }

    public void saveLevel(ModelLevel level) {
        ContentValues newValues = new ContentValues();

        newValues.put(LEVEL_WORD_COLUMN, level.getWord());
        newValues.put(IMAGE_ONE_COLUMN, level.getBitmap1());
        newValues.put(IMAGE_TWO_COLUMN, level.getBitmap2());
        newValues.put(IMAGE_THREE_COLUMN, level.getBitmap3());
        newValues.put(IMAGE_FOUR_COLUMN, level.getBitmap4());
        newValues.put(LEVEL_STATUS_COLUMN, level.getStatus());
        newValues.put(LEVEL_TIME_STAMP_COLUMN, level.getTimeStamp());

        getWritableDatabase().insert(LEVELS_TABLE, null, newValues);
    }

    public DBQueryManager query() {
        return mDBQueryManager;
    }

    public DBUpdateManager update() {
        return mDBUpdateManager;
    }
}
