package com.sashashtmv.admin.mytask1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TwoActivity extends AppCompatActivity {
    private TextView mShowText;
    private Button mButtonTwo;
    public static final String TAG_TEXT = "TAG_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        mShowText = findViewById(R.id.show);
        mButtonTwo = findViewById(R.id.button_two);

        Bundle bundle = getIntent().getExtras();

        mShowText.setText(bundle.getString(TAG_TEXT));

        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mShowText.getText())) {
                    Uri address = Uri.parse("http://google.com/search?q="+ mShowText.getText());
                    Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                    startActivity(openlinkIntent);
                }
            }
        });
    }
}
