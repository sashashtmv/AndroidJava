package com.elegion.myfirstapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";
    public static final int REQUEST_CODE_GET_PHOTO = 101;

    private AppCompatImageView mPhoto;
    private TextView mLogin;
    private TextView mPassword;
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private User mUser;

    private View.OnClickListener mOnPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openGallery();
        }
    };

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GET_PHOTO
                && resultCode == Activity.RESULT_OK
                && data != null) {
            Uri photoUri = data.getData();
            mPhoto.setImageURI(photoUri);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mSharedPreferencesHelper = new SharedPreferencesHelper(this);

        mPhoto = findViewById(R.id.ivPhoto);
        mLogin = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.tvPassword);

        Bundle bundle = getIntent().getExtras();
        mUser = (User) bundle.get(USER_KEY);
        mLogin.setText(mUser.getLogin());
        mPassword.setText(mUser.getPassword());

        mPhoto.setOnClickListener(mOnPhotoClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLogout:
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
