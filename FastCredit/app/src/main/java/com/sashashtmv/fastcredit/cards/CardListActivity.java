package com.sashashtmv.fastcredit.cards;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sashashtmv.fastcredit.SingleFragmentActivity;

public class CardListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CardListActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return  new CardListFragment();
    }
}
