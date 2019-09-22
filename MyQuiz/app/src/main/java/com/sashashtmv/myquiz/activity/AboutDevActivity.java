package com.sashashtmv.myquiz.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.utilities.ActivityUtilities;

public class AboutDevActivity extends BaseActivity {

    private ImageView mImageView;
    TextView tvDevTitle, tvDevSubTitle, tvDevText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);

        mImageView = findViewById(R.id.imgDev);
        tvDevTitle = findViewById(R.id.tvDevTitle);
        tvDevSubTitle = findViewById(R.id.tvDevSubtitle);
        tvDevText = findViewById(R.id.tvDevText);

        initToolbar(true);
        setToolbarTitle(getString(R.string.about_dev));
        enableUpButton();
    }

    //обработаем нажатие кнопки домой в тулбаре, будем просто принудительно закрывать активити при ее нажатии

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // в окне о приложении - делаем наш текст и изображение кликабельными
    public void clickView(View view){
        ActivityUtilities.getInstance().invokeCustomUrlActivity(AboutDevActivity.this, CustomUrlActivity.class,
                getResources().getString(R.string.site), getResources().getString(R.string.site_url), false);
    }
}
