package com.sashashtmv.fastcredit.credits;

import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Bank {
    private UUID mId;
    private Uri adress;

    public Uri getAdress() {
        return adress;
    }

    public void setAdress(Uri adress) {
        this.adress = adress;
    }

    private ImageView mIcon;
    private String mSum;
    private String mRate;
    private String mTerm;

    public Bank() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public ImageView getIcon() {
        return mIcon;
    }

    public void setIcon(ImageView icon) {
        mIcon = icon;
    }

    public String getSum() {
        return mSum;
    }

    public void setSum(String sum) {
        mSum = sum;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String rate) {
        mRate = rate;
    }

    public String getTerm() {
        return mTerm;
    }

    public void setTerm(String term) {
        mTerm = term;
    }
}

