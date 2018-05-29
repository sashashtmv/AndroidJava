package com.sashashtmv.errorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view) {
        ImageView imageView = (ImageView)findViewById(R.id.android_cookie_image_view);
        imageView.setImageResource(R.drawable.after_cookie);
        TextView textView = (TextView)findViewById(R.id.status_text_view);
        textView.setText("I'm so full");
        Button button = (Button)findViewById(R.id.eat);
        button.setVisibility(View.INVISIBLE);

        // TODO: Find a reference to the ImageView in the layout. Change the image.

        // TODO: Find a reference to the TextView in the layout. Change the text.

    }
}
