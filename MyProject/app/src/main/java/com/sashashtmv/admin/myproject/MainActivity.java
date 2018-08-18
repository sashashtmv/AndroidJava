package com.sashashtmv.admin.myproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends SingleFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_conteiner);

    }

    @Override
    protected Fragment getFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance())
                .addToBackStack(MainFragment.class.getName())
                .commit();
        return MainFragment.newInstance();
    }
//создаём меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    //создаём элементы меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSetting:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, SettingsFragment.newInstance())
                        .addToBackStack(SettingsFragment.class.getName())
                        .commit();

                Toast.makeText(MainActivity.this, R.string.setting_input, Toast.LENGTH_LONG).show();
                break;
            case R.id.actionSearch:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, SearchFragment.newInstance())
                        .addToBackStack(SearchFragment.class.getName())
                        .commit();
                Toast.makeText(MainActivity.this, R.string.search_input, Toast.LENGTH_LONG).show();
                break;
            case R.id.actionExit:
                Toast.makeText(MainActivity.this, R.string.exit_input, Toast.LENGTH_LONG).show();
                this.finishAffinity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
