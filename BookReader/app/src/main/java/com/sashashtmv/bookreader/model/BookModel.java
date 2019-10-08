package com.sashashtmv.bookreader.model;

import android.graphics.Bitmap;

public class BookModel {
    private String nameBook;
    private String addressBook;
    private Bitmap imageName;

    public BookModel(String nameBook, String addressBook, Bitmap imageName) {
        this.nameBook = nameBook;
        this.addressBook = addressBook;
        this.imageName = imageName;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(String addressBook) {
        this.addressBook = addressBook;
    }

    public Bitmap getImageName() {
        return imageName;
    }

    public void setImageName(Bitmap imageName) {
        this.imageName = imageName;
    }

}
