package com.sashashtmv.fastcredit.loans;

import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Loaner {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Uri adressLoaner;
    private Uri adressPicture;

    public Uri getAdressLoaner() {
        return adressLoaner;
    }

    public void setAdressLoaner(Uri adressLoaner) {
        this.adressLoaner = adressLoaner;
    }

    public Uri getAdressPicture() {
        return adressPicture;
    }

    public void setAdressPicture(Uri adressPicture) {
        this.adressPicture = adressPicture;
    }

    public Loaner() {
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

