package com.sashashtmv.admin.mymessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecievMessageActivity extends Activity {
    private TextView sendText;
    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciev_message);
        Intent intent = getIntent();
        String sendMessage = intent.getStringExtra(EXTRA_MESSAGE);
        sendText = findViewById(R.id.message);
        sendText.setText(sendMessage);
    }
}
