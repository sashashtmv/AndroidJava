package com.sashashtmv.game4in1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;
import android.widget.Toast;

import com.sashashtmv.game4in1.adapter.AdapterForLevels;
import com.sashashtmv.game4in1.fragments.AskFreandsFragment;
import com.sashashtmv.game4in1.fragments.CoinsFragment;
import com.sashashtmv.game4in1.fragments.LevelFragment;
import com.sashashtmv.game4in1.fragments.ResultFragment;
import com.sashashtmv.game4in1.fragments.StartFragment;
import com.sashashtmv.game4in1.model.Item;
import com.sashashtmv.game4in1.model.ModelLevel;
import com.sashashtmv.game4in1.model.PreferenceHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements StartFragment.callback, LevelFragment.callbackResult, LevelFragment.callbackCoinsFragment, LevelFragment.callbackAskFreandsFragment {

    FragmentManager mFragmentManager;
    StartFragment startFragment;
    ArrayList<Item> mItems;
    private int countCoins;
    private PreferenceHelper mPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceHelper.getInstance().init(this);
        mPreferenceHelper = PreferenceHelper.getInstance();
        countCoins = mPreferenceHelper.getInt("gold");
        if(countCoins == 0){
            mPreferenceHelper.putInt("gold", 100);
        }
        Log.i(TAG, "init: context - " + countCoins);

        mFragmentManager = getFragmentManager();
        startFragment = StartFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, startFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onCreatLevel(ArrayList<Item> items, ModelLevel item) {

        Fragment level = LevelFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, level, "level")
                .addToBackStack(null)
                .commit();
        mItems = items;
        if(level != null) {
            //Toast.makeText(this, customer.getTelephoneNumber(), Toast.LENGTH_LONG).show();
            ((LevelFragment) level).updateLevels(mItems, item);
            //((InformationFragment) information).updateOrder(mOrder);
        }

    }

    @Override
    public void onCreatResult(ModelLevel item) {
        Fragment result = ResultFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, result, "result")
                .addToBackStack(null)
                .commit();
        //mItems = items;
        if(result != null) {
            //Toast.makeText(this, customer.getTelephoneNumber(), Toast.LENGTH_LONG).show();
            ((ResultFragment) result).updateResult(item);
            //((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatCoinsFragment() {
        Fragment coinsFragment = CoinsFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, coinsFragment, "coinsFragment")
                .addToBackStack(null)
                .commit();
        //mItems = items;
        if(coinsFragment != null) {
            //Toast.makeText(this, customer.getTelephoneNumber(), Toast.LENGTH_LONG).show();
            ((CoinsFragment) coinsFragment).updateCoins(countCoins);
            //((InformationFragment) information).updateOrder(mOrder);
        }
    }

    @Override
    public void onCreatAskFreandsFragment() {
        Fragment askFreandsFragment = AskFreandsFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, askFreandsFragment, "askFreandsFragment")
                .addToBackStack(null)
                .commit();
    }
}
