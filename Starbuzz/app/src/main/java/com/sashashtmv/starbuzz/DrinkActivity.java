package com.sashashtmv.starbuzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends Activity {

    private ImageView imageDrink;
    private TextView nameDrink;
    private TextView descriptionDrink;

    public static final String EXTRA_DRINKID = "extra drink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Intent intent = getIntent();
        int idDrink = intent.getIntExtra(EXTRA_DRINKID, 0);
        Drink drink = Drink.drinks[idDrink];

        imageDrink = findViewById(R.id.photo);
        nameDrink = findViewById(R.id.name);
        descriptionDrink = findViewById(R.id.description);

        imageDrink.setImageResource(drink.getImageResourceID());
        imageDrink.setContentDescription(drink.getName());
        nameDrink.setText(drink.getName());
        descriptionDrink.setText(drink.getDescription());
    }
}
