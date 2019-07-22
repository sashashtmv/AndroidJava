package testcom.com.net2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Mod implements Parcelable {
    public String title;
    public String description;
    public String iconUrl;
    public String url;
    public int type;

    protected Mod(Parcel in) {
        title = in.readString();
        description = in.readString();
        iconUrl = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(iconUrl);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mod> CREATOR = new Creator<Mod>() {
        @Override
        public Mod createFromParcel(Parcel in) {
            return new Mod(in);
        }

        @Override
        public Mod[] newArray(int size) {
            return new Mod[size];
        }
    };
}
