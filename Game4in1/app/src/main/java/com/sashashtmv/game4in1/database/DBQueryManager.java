package com.sashashtmv.game4in1.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sashashtmv.game4in1.model.ModelLevel;

import java.util.ArrayList;
import java.util.List;

public class DBQueryManager {
    private SQLiteDatabase mDatabase;

    public DBQueryManager(SQLiteDatabase database) {
        mDatabase = database;
    }

    public ModelLevel getLevel(long timeStamp){
        ModelLevel modelLevel = null;
        Cursor c = mDatabase.query(DBHelper.LEVELS_TABLE, null, DBHelper.SELECTION_WORD,
                new String[]{Long.toString(timeStamp)}, null, null, null);
        if(c.moveToFirst()){
            String word = c.getString(c.getColumnIndex(DBHelper.LEVEL_WORD_COLUMN));
            String image1 = c.getString(c.getColumnIndex(DBHelper.IMAGE_ONE_COLUMN));
            String image2 = c.getString(c.getColumnIndex(DBHelper.IMAGE_TWO_COLUMN));
            String image3 = c.getString(c.getColumnIndex(DBHelper.IMAGE_THREE_COLUMN));
            String image4 = c.getString(c.getColumnIndex(DBHelper.IMAGE_FOUR_COLUMN));

            int status = c.getInt(c.getColumnIndex(DBHelper.LEVEL_STATUS_COLUMN));

            modelLevel = new ModelLevel(word, image1, image2, image3, image4, status,timeStamp);
        }
        c.close();
        return modelLevel;

    }
    public List<ModelLevel> getLevels(){
        List<ModelLevel> modelLevels = new ArrayList<>();
//будет находить в базе данных значения и формировать их в Tasks
        //Cursor c = mDatabase.query(DBHelper.LEVELS_TABLE, null, null, null, null, null, null);
        Cursor c = mDatabase.rawQuery("select * from "+ DBHelper.LEVELS_TABLE,null);

        if(c.moveToFirst()){
            do{
                String word = c.getString(c.getColumnIndex(DBHelper.LEVEL_WORD_COLUMN));
                String image1 = c.getString(c.getColumnIndex(DBHelper.IMAGE_ONE_COLUMN));
                String image2 = c.getString(c.getColumnIndex(DBHelper.IMAGE_TWO_COLUMN));
                String image3 = c.getString(c.getColumnIndex(DBHelper.IMAGE_THREE_COLUMN));
                String image4 = c.getString(c.getColumnIndex(DBHelper.IMAGE_FOUR_COLUMN));
                long timeStamp = c.getLong(c.getColumnIndex(DBHelper.LEVEL_TIME_STAMP_COLUMN));

                int status = c.getInt(c.getColumnIndex(DBHelper.LEVEL_STATUS_COLUMN));

                ModelLevel modelLevel = new ModelLevel(word, image1, image2, image3, image4, status,timeStamp);
                modelLevels.add(modelLevel);
            }while (c.moveToNext());
        }
        return modelLevels;
    }

    public List<String> getWords(String selection, String[] selectionArgs, String orderBy){
        List<String> words = new ArrayList<>();


        return  words;
    }
}
