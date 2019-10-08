package com.sashashtmv.bookreader.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sashashtmv.bookreader.R;
import com.sashashtmv.bookreader.adapter.ListAdapter;
import com.sashashtmv.bookreader.model.BookModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListActivity extends AppCompatActivity implements ListAdapter.ItemListener {

    private int count = 0;
    private int countBook = 0;
    private List<BookModel> mItems = new ArrayList<>();

    @BindView(R.id.rvBooks)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.coordinator)
    protected CoordinatorLayout mCoordinatorLayout;

    private GridLayoutManager lLayout;
    private ListAdapter rcAdapter;


    private static final String TAG = "ListActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        getItems();

    }

    private void getItems() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("books.json");
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.i(TAG, "doInBackground: url - " + url);
                new AsyncRequestList().execute(url, "/ajax", "foo=bar");
            }
        });

    }


    class AsyncRequestList extends AsyncTask<String, Integer, String> {

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
            // Log.i(TAG, "doInBackground: temp - " + temp);

            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray masJson = jsonObject.getJSONArray("book");
                countBook = masJson.length();
                for (int i = 0; i < masJson.length(); i++) {
                    JSONObject obj = masJson.getJSONObject(i);

                    String[] bookName = new String[]{obj.getString("book_name")};
                    String[] addressBook = new String[]{obj.getString("address_book")};
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference islandRef = storageRef.child(obj.getString("address_image"));
                    islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            new AsyncRequestImage().execute(url, bookName[0] , addressBook[0]);
                        }
                    });
                }

            } catch (JSONException err) {
                Log.d("Error", err.toString());
            }
            //Log.i(TAG, "onPostExecute: result - " + "items - " + items.size() + ", address book - " + items.get(0).getAddressBook() + " book name - " + items.get(0).getNameBook() + " count literal - " + s.length());
        }
    }
    class AsyncRequestImage extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            URL url = null;
            String temp = "";
            Bitmap bmp = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                url = new URL(arg[0]);
                URLConnection connection = url.openConnection();
                InputStream in = connection.getInputStream();
                bufferedInputStream = new BufferedInputStream(in);

                bmp = BitmapFactory.decodeStream(bufferedInputStream);
                mItems.add(new BookModel(arg[1], arg[2], bmp));
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: image - " + 1);
            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            count++;
            if(count == countBook) {
                lLayout = new GridLayoutManager(ListActivity.this, 2);

                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(lLayout);
                rcAdapter = new ListAdapter(ListActivity.this, mItems, ListActivity.this);

                mRecyclerView.setAdapter(rcAdapter);
                setTheme(R.style.AppTheme);
                mCoordinatorLayout.setBackground(getResources().getDrawable(R.drawable.background_books));

            }

        }
    }

    @Override
    public void onItemClick(BookModel item) {
        Intent intent = new Intent(this, PageActivity.class);
        String nameBook = item.getAddressBook();
        intent.putExtra("name", nameBook);
        startActivity(intent);
    }

}
