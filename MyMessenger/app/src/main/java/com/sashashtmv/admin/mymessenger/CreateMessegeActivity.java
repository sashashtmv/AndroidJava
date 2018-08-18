package com.sashashtmv.admin.mymessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessegeActivity extends Activity {

    private EditText enterMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_messege);
    }

    public void sendMessage(View view){
        enterMessage = findViewById(R.id.add_text);
        String message = enterMessage.getText().toString();
        //Intent intent = new Intent(this, RecievMessageActivity.class);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        Intent chooseIntent = Intent.createChooser(intent, getString(R.string.my_choose));//оборачиваем наш интент в метод, который заставляет всегда выбирать активность
        //intent.putExtra(RecievMessageActivity.EXTRA_MESSAGE, message);
        startActivity(chooseIntent);
    }
}
