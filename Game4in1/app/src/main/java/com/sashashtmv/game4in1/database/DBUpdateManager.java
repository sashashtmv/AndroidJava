package com.sashashtmv.game4in1.database;
//будет отвечать за обновление данных в нашей базе

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;



public class DBUpdateManager {
    private SQLiteDatabase mDatabase;
    public DBUpdateManager(SQLiteDatabase database) {
        this.mDatabase = database;
    }

//    public void title(long timeStamp, String title){
//        update(DBHelper.TASK_TITLE_COLUMN,timeStamp, title);
//    }
//
//    public void date(long timeStamp, long date){
//        update(DBHelper.TASK_DATE_COLUMN,timeStamp, date);
//    }
//    public void priority(long timeStamp, int priority){
//        update(DBHelper.TASK_PRIORITY_COLUMN,timeStamp, priority);
//    }

    public void status(long timeStamp, int status){
        update(DBHelper.LEVEL_STATUS_COLUMN, timeStamp, status);
    }
    public void title(long timeStamp, String title){
        update(DBHelper.IMAGE_ONE_COLUMN,timeStamp, title);
    }

    //    public void task(ModelTask modelTask){
//        title(modelTask.getTimeStamp(),modelTask.getTitle());
//        date(modelTask.getTimeStamp(), modelTask.getDate());
//        priority(modelTask.getTimeStamp(), modelTask.getPriority());
//        status(modelTask.getTimeStamp(), modelTask.getStatus());
//    }
//
//    private void update(String column, long key, String value){
//        ContentValues cv = new ContentValues();
//        cv.put(column,value);
//        mDatabase.update(DBHelper.TASKS_TABLE, cv, DBHelper.TASK_TIME_STAMP_COLUMN + " - " + key, null);
//    }
    private void update(String column, long key, String value){
        ContentValues cv = new ContentValues();
        cv.put(column,value);
        mDatabase.update(DBHelper.LEVELS_TABLE, cv, DBHelper.LEVEL_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update(String column, long key, int value){
        ContentValues cv = new ContentValues();
        cv.put(column,value);
        mDatabase.update(DBHelper.LEVELS_TABLE, cv, DBHelper.LEVEL_TIME_STAMP_COLUMN + " = " + key, null);
    }
}