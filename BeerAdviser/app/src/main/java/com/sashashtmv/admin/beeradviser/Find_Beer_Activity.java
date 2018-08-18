package com.sashashtmv.admin.beeradviser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Find_Beer_Activity extends Activity {
    private TextView mBrands;
    private Spinner mColor;
    private ExpertBeer expert = new ExpertBeer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__beer_);
    }

    public void findBeer(View view){
        mBrands = findViewById(R.id.brands);
        mColor = findViewById(R.id.color);
        String selectedSpiner = String.valueOf(mColor.getSelectedItem());
        mBrands.setText(expert.getBrands(selectedSpiner).toString());
    }
}
