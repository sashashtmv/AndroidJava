package testcom.com.net2.model;

import android.support.v4.app.FragmentManager;

public interface DialogClickListener {
    void onDownloadClicked(String url);
    FragmentManager getFragManager();
}
