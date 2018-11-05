package com.sashashtmv.fastcredit.other;

import android.net.Uri;
import android.widget.ImageView;

import java.util.UUID;

public class Other {
    private UUID mId;
    private ImageView mIcon;
    private String mTitle;
    private String mDescription;
    private Uri adress;

    public Uri getAdress() {
        return adress;
    }

    public void setAdress(Uri adress) {
        this.adress = adress;
    }

    public Other() {
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

