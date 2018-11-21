package com.sashashtmv.fastcredit.credits;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Bank {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Uri adressBank;
    private Uri adressPicture;
    private Bitmap mIcon;

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap icon) {
        mIcon = icon;
    }

    public Uri getAdressPicture() {
        return adressPicture;
    }

    public void setAdressPicture(Uri adressPicture) {
        this.adressPicture = adressPicture;
    }

    public Uri getAdressBank() {
        return adressBank;
    }

    public void setAdressBank(Uri adressBank) {
        this.adressBank = adressBank;
    }

    public Bank() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}

