package com.sashashtmv.shoppinglist.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Date;

public class ModelPurchase {

    public static final int STATUS_CURRENT = 0;
    public static final int STATUS_DONE = 1;
    private long timeStamp;

    private Bitmap mBitmap;

    public ModelPurchase() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    private String title;
    private int status;

    public ModelPurchase(String title, int status, Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
