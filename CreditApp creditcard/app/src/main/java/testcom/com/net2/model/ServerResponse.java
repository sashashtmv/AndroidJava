package testcom.com.net2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ServerResponse implements Parcelable {
    public ArrayList<Mod> banks;

    protected ServerResponse(Parcel in) {
        banks = in.createTypedArrayList(Mod.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(banks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServerResponse> CREATOR = new Creator<ServerResponse>() {
        @Override
        public ServerResponse createFromParcel(Parcel in) {
            return new ServerResponse(in);
        }

        @Override
        public ServerResponse[] newArray(int size) {
            return new ServerResponse[size];
        }
    };
}
