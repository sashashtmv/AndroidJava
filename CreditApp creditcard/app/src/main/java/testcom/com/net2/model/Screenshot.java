package testcom.com.net2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Screenshot implements Parcelable {
    public String imageUrl;

    protected Screenshot(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<Screenshot> CREATOR = new Creator<Screenshot>() {
        @Override
        public Screenshot createFromParcel(Parcel in) {
            return new Screenshot(in);
        }

        @Override
        public Screenshot[] newArray(int size) {
            return new Screenshot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
