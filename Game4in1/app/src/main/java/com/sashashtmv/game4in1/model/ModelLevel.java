package com.sashashtmv.game4in1.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ModelLevel implements Item {

    public static final int STATUS_AVALABLE = 0;
    public static final int STATUS_NOT_AVALABLE = 1;
    public static final int STATUS_DONE = 2;

    private String mWord;
    private String mBitmap1;
    private String mBitmap2;
    private String mBitmap3;
    private String mBitmap4;
    private int mStatus;
    private long mTimeStamp;

    public ModelLevel(String word, String bitmap1, String bitmap2, String bitmap3, String bitmap4, int status, long timeStamp) {
        mWord = word;
        mBitmap1 = bitmap1;
        mBitmap2 = bitmap2;
        mBitmap3 = bitmap3;
        mBitmap4 = bitmap4;
        mStatus = status;
        mTimeStamp = timeStamp;
    }


    public String getWord() {
        return mWord;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public void setWord(String word) {
        mWord = word;
    }


    public void setBitmap1(String bitmap1) {
        mBitmap1 = bitmap1;
    }

    public String getBitmap1() {
        return mBitmap1;
    }
    public String getBitmap2() {
        return mBitmap2;
    }
    public String getBitmap3() {
        return mBitmap3;
    }

    public String getBitmap4() {
        return mBitmap4;
    }
    public void setBitmap2(String bitmap2) {
        mBitmap2 = bitmap2;
    }


    public void setBitmap3(String bitmap3) {
        mBitmap3 = bitmap3;
    }


    public void setBitmap4(String bitmap4) {
        mBitmap4 = bitmap4;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    public long getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        mTimeStamp = timeStamp;
    }
}