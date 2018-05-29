package com.sashashtmv.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int sumA = 0;
    int sumB = 0;
    public void thirtisA(View view){
        display1(3);
    }
    public void twisA(View view){
        display1(2);
    }
    public void onesA(View view){
        display1(1);
    }
    private void display1(int count){
        sumA = sumA + count;
        if(count==0)sumA=0;
        TextView countTextView = (TextView) findViewById(R.id.textView);
        countTextView.setText("" + sumA);
    }
    public void thirtisB(View view){
        display2(3);
    }
    public void twisB(View view){
        display2(2);
    }
    public void onesB(View view){
        display2(1);
    }
    private void display2(int count){
        sumB = sumB + count;
        if(count==0)sumB=0;
        TextView countTextView = (TextView) findViewById(R.id.textView3);
        countTextView.setText("" + sumB);
    }
    public void reset(View view){
        display1(0);
        display2(0);
    }
}
