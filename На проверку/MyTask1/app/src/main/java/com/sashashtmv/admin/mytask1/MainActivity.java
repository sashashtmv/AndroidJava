package com.sashashtmv.admin.mytask1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mInputText;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputText = findViewById(R.id.input);
        mButton = findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mInputText.getText())) {
                    Toast.makeText(MainActivity.this, mInputText.getText(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                    intent.putExtra(TwoActivity.TAG_TEXT,mInputText.getText().toString());
                    startActivity(intent);
                }
//                if (!TextUtils.isEmpty(mInputText.getText())) {
//                    Toast.makeText(MainActivity.this, mInputText.getText(), Toast.LENGTH_LONG).show();
//                }
            }
        });
    }


}
