package testcom.com.net2.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import testcom.com.net2.R;
import testcom.com.net2.api.ServerAPI;
import testcom.com.net2.dialog.ModDialog;
import testcom.com.net2.fragment.LoadingFragment;
import testcom.com.net2.fragment.RecyclerFragment;
import testcom.com.net2.model.DialogClickListener;
import testcom.com.net2.model.DownloadListener;
import testcom.com.net2.model.Mod;
import testcom.com.net2.model.RecyclerClickListener;
import testcom.com.net2.model.ServerResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DialogClickListener, RecyclerClickListener, DownloadListener {

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager fragmentManager;
    private LoadingFragment loadingFragment;
    private RecyclerFragment recyclerFragment;
    private FragmentTransaction fragmentTransaction;
    private AlertDialog permissionDialog;

    private final int PERMISSION_ID = 1;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        permissionDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied_dialog_title)
                .setMessage(R.string.permission_denied_dialog_message)
                .setNegativeButton(R.string.permission_denied_dialog_n_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(R.string.permission_denied_dialog_p_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts(getPackageName(), getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }).create();
    }

    private void downloadTask(String url, WeakReference<Context> contextWeakReference) {
        DownloadTask downloadTask = new DownloadTask(contextWeakReference, mProgressDialog);
        downloadTask.execute(url);
    }

    private void init() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

        fragmentManager = getSupportFragmentManager();
        loadingFragment = new LoadingFragment();
        recyclerFragment = new RecyclerFragment();

//        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//        fragmentTransaction.replace(fragmentContainer.getId(), loadingFragment).commit();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://tut13.ru/").addConverterFactory(GsonConverterFactory.create()).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        Call<ServerResponse> serverResponseCall = serverAPI.cResponseCall();
        serverResponseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull final Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        final Bundle extras = new Bundle();
                        recyclerFragment.setArguments(extras);
                        extras.putParcelable(ServerResponse.class.getName(), response.body());

                        fragmentTransaction.replace(fragmentContainer.getId(), recyclerFragment).commit();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                init();
            }
        }
    }

    @Override
    public void onDownloadClicked(String url) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionDialog.show();
        else
            downloadTask(url, new WeakReference<Context>(this));
    }

    @Override
    public FragmentManager getFragManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void onItemClicked(Mod item) {
        ModDialog alertDialog = new ModDialog(this, item);
        alertDialog.show();
    }

    private static class DownloadTask extends AsyncTask<String, Integer, String> {

        private WeakReference<Context> contextWeakReference;
        private PowerManager.WakeLock mWakeLock;
        private DownloadListener downloadListener;
        private ProgressDialog progressDialog;

        DownloadTask(WeakReference<Context> contextWeakReference, ProgressDialog progressDialog) {
            this.contextWeakReference = contextWeakReference;
            this.downloadListener = (DownloadListener) contextWeakReference.get();
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/temp.zip");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) contextWeakReference.get().getSystemService(Context.POWER_SERVICE);
            if (pm != null) {
                mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
                mWakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
            }
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (mWakeLock != null)
                mWakeLock.release();
            progressDialog.dismiss();
            if (result != null)
                Toast.makeText(contextWeakReference.get(), "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(contextWeakReference.get(), "File downloaded", Toast.LENGTH_SHORT).show();

            downloadListener.onFileDownloaded();
        }
    }

    @Override
    public void onFileDownloaded() {
        Log.wtf("DOGAD", "HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY HEY");
        Decompress decompress = new Decompress(Environment.getExternalStorageDirectory() + "/temp.zip", Environment.getExternalStorageDirectory() + "/games/com.mojang/minecraftWorlds/", new WeakReference<Context>(this), mProgressDialog);
        decompress.execute();
    }

    private static class Decompress extends AsyncTask<Void, Integer, Integer> {

        private String _zipFile;
        private String _location;
        private int per = 0;
        private int totalSize = 0;

        private ProgressDialog progressDialog;

        public Decompress(String zipFile, String location, WeakReference<Context> contextWeakReference, ProgressDialog progressDialog) {
            _zipFile = zipFile;
            _location = location;
            _dirChecker("");
            this.progressDialog = progressDialog;
        }

        private void _dirChecker(String dir) {
            File f = new File(_location + dir);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                unzip(new FileInputStream(Environment.getExternalStorageDirectory() + "/temp.zip"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return totalSize;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            Log.wtf("DOGAD", "DOGAD");
        }
    }

    public static boolean unzip(InputStream file) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(file));
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file3 = new File(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/com.mojang/minecraftWorlds/"), nextEntry.getName());
                    File parentFile = nextEntry.isDirectory() ? file3 : file3.getParentFile();
                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                        throw new FileNotFoundException("Can not find file " + parentFile.getAbsolutePath());
                    } else if (!nextEntry.isDirectory()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                    }
                } else {
                    zipInputStream.close();
                    return true;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }
}