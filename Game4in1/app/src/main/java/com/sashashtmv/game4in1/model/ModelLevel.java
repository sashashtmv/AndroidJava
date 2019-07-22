package com.sashashtmv.game4in1.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ModelLevel implements Item {

    public static final int STATUS_AVALABLE = 0;
    public static final int STATUS_NOT_AVALABLE = 1;
    public static final int STATUS_DONE = 2;

    private String word;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private Bitmap mBitmap4;

    public ModelLevel(String word, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4) {
        this.word = word;
        mBitmap1 = bitmap1;
        mBitmap2 = bitmap2;
        mBitmap3 = bitmap3;
        mBitmap4 = bitmap4;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Bitmap getBitmap1() {
        return mBitmap1;
    }

    public void setBitmap1(Bitmap bitmap1) {
        mBitmap1 = bitmap1;
    }

    public Bitmap getBitmap2() {
        return mBitmap2;
    }

    public void setBitmap2(Bitmap bitmap2) {
        mBitmap2 = bitmap2;
    }

    public Bitmap getBitmap3() {
        return mBitmap3;
    }

    public void setBitmap3(Bitmap bitmap3) {
        mBitmap3 = bitmap3;
    }

    public Bitmap getBitmap4() {
        return mBitmap4;
    }

    public void setBitmap4(Bitmap bitmap4) {
        mBitmap4 = bitmap4;
    }

    @Override
    public boolean isTask() {
        return true;
    }
}
