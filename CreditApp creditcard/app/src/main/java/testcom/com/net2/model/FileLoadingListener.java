package testcom.com.net2.model;

public interface FileLoadingListener {
    void onBegin();
    void onSuccess();
    void onFailure(Throwable cause);
    void onEnd();
}
