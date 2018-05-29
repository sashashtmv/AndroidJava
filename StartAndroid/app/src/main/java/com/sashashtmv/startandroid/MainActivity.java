package com.sashashtmv.startandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void fish(View view){
        String fisher = "jgfkjhkhkjh";
        display(fisher);
    }
    public void horoskop(View view){
        Intent intent = new Intent(this, TwoActivity.class);
        startActivity(intent);
    }
    public void display(String s){
        TextView fishh = (TextView)findViewById(R.id.textView3);
        fishh.setText(s);
    }
}
