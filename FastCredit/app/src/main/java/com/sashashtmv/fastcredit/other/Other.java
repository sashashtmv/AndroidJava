package com.sashashtmv.fastcredit.other;

import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Other {
    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Uri adressOther;
    private Uri adressPicture;

    public Uri getAdressOther() {
        return adressOther;
    }

    public void setAdressOther(Uri adressOther) {
        this.adressOther = adressOther;
    }

    public Other() {
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

