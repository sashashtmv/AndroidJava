package com.sashashtmv.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);

        ListView adapterDrinks = findViewById(R.id.list_drinks);
        adapterDrinks.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> adapterDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
                            Intent intent = new Intent(DrinkCategoryActivity.this,
                                    DrinkActivity.class);
                            intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                            startActivity(intent);

                    }
                };

        //Добавить слушателя к списковому представлению

        adapterDrinks.setOnItemClickListener(itemClickListener);
    }
}
