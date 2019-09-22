package com.sashashtmv.myquiz.models.quiz;

import android.os.Parcel;
import android.os.Parcelable;

//интерфейс parcelable используется для передачи объектов между активити

public class CategoryModel implements Parcelable {
    private String categoryId, categoryName;

    public CategoryModel(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    //описывает различного рода специальные объекты, описывающие интерфейс
    @Override
    public int describeContents() {
        return 0;
    }

    //упаковывает объект для передачи
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(categoryName);
    }

    //принимает пакет и считывает строковые значения из него

    public CategoryModel(Parcel in) {
        categoryId = in.readString();
        categoryName = in.readString();
    }

    //генерирует объект класса-передатчика
    public static final  Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel source) {
            return new CategoryModel(source);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    //теперь мы можем передать объект CategoryModel через Intent
    public static  Creator<CategoryModel> getCreator(){
        return CREATOR;
    }
}
