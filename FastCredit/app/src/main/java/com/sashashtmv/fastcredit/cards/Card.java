package com.sashashtmv.fastcredit.cards;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Card {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Uri adressBank;
    private Uri adressPicture;
    private Bitmap mIcon;

    public Uri getAdressBank() {
        return adressBank;
    }

    public void setAdressBank(Uri adressBank) {
        this.adressBank = adressBank;
    }

    public Card() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public Uri getAdressPicture() {
        return adressPicture;
    }

    public void setAdressPicture(Uri adressPicture) {
        this.adressPicture = adressPicture;
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

