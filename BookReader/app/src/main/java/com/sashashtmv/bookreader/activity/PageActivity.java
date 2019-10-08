package com.sashashtmv.bookreader.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sashashtmv.bookreader.R;
import com.sashashtmv.bookreader.adapter.PageAdapter;
import com.sashashtmv.bookreader.model.PageModel;
import com.sashashtmv.bookreader.model.PreferenceHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Scanner;

public class PageActivity extends AppCompatActivity {

    private static final String TAG = "PageActivity";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private PreferenceHelper mPreferenceHelper;
    private PageAdapter adapter;
    public static int index = -1;
    public static int top = -1;
    LinearLayoutManager mLayoutManager;

    private String nameBook = "";
    private int itemCount = 0;
    private int proc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ButterKnife.bind(this);

        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();

        Intent i = getIntent();
        nameBook = i.getStringExtra("name");

        index = mPreferenceHelper.getInt(nameBook + "index");
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Log.i(TAG, "doCreate: index - " + index);

        proc = mPreferenceHelper.getInt(nameBook + "proc");
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        doApiCall();

    }

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            URL url = null;
            String temp = "";
            try {
                url = new URL(arg[0]);
                URLConnection connection = url.openConnection();
                InputStream in = connection.getInputStream();
                final StringBuilder out = new StringBuilder();

                Scanner s = new Scanner(in, "UTF-8").useDelimiter("\\A");
                String sss = s.hasNext() ? s.next() : "";
                out.append(sss);

                temp = out.toString();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<PageModel> items = new ArrayList<>();
            ArrayList<String> pages = new ArrayList<>();

            String temp = "";
            for (int i = 0; i < s.length(); i++) {
                temp += s.charAt(i);
                if (i % 500 == 0 && i != 0) {
                    while (s.charAt(i) != '\n') {
                        i++;
                        temp += s.charAt(i);

                    }
                    pages.add(temp);
                    temp = "";

                }
            }
//                для последней страницы
            if (temp.length() > 0) {
                pages.add(temp);
            }
//                наполняем модель данными
            for (int i = 0; i < pages.size(); i++) {
                itemCount++;
                PageModel page = new PageModel("Страница: " + itemCount, pages.get(i));

                items.add(page);
            }
            adapter = new PageAdapter(new ArrayList<PageModel>(), nameBook, mPreferenceHelper);
            mRecyclerView.setAdapter(adapter);
            adapter.addItems(items);
            Log.i(TAG, "onPostExecute: result - " + "items - " + items.size() + ", pages - " + pages.size() + " count literal - " + s.length());
        }
    }

    private void doApiCall() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(nameBook);
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                new AsyncRequest().execute(url, "/ajax", "foo=bar");
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        View v = mRecyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - mRecyclerView.getPaddingTop());
        Log.i(TAG, "doPause: top - " + top + "  " + index);

    }

    @Override
    public void onResume() {
        super.onResume();
        index = mPreferenceHelper.getInt(nameBook + "index");
        proc = mPreferenceHelper.getInt(nameBook + "proc");
        if (index != -1) {
            //mSpeedyLinearLayoutManager.scrollToPositionWithOffset(index, 0);
            mLayoutManager.scrollToPositionWithOffset(index, 0);
        }

        Log.i(TAG, "onResume: index - " + index);
    }

}
