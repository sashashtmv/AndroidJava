package com.sashashtmv.taskmanager.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sashashtmv.taskmanager.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

public class DBQueryManager {
    private SQLiteDatabase mDatabase;

    public DBQueryManager(SQLiteDatabase database) {
        mDatabase = database;
    }

    public ModelTask getTask(long timeStamp){
        ModelTask modelTask = null;
        Cursor c = mDatabase.query(DBHelper.TASKS_TABLE, null, DBHelper.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);
        if(c.moveToFirst()){
            String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
            int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
            int type_task = c.getInt(c.getColumnIndex(DBHelper.TYPE_TASK_COLUMN));

            modelTask = new ModelTask(title, date, priority, status, timeStamp, type_task);
        }
        c.close();
        return modelTask;

    }
    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy){
        List<ModelTask> modelTasks = new ArrayList<>();
//будет находить в базе данных значения и формировать их в Tasks
        Cursor c = mDatabase.query(DBHelper.TASKS_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if(c.moveToFirst()){
            do{
                String title = c.getString(c.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date = c.getLong(c.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                int priority = c.getInt(c.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status = c.getInt(c.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));
                int type_task = c.getInt(c.getColumnIndex(DBHelper.TYPE_TASK_COLUMN));
                long timeStamp = c.getLong(c.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));

                ModelTask modelTask = new ModelTask(title, date, priority, status, timeStamp, type_task);
                modelTasks.add(modelTask);
            }while (c.moveToNext());
        }
        return modelTasks;
    }
}
