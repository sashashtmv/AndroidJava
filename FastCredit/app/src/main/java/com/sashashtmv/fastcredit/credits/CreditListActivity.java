package com.sashashtmv.fastcredit.credits;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sashashtmv.fastcredit.SingleFragmentActivity;

public class CreditListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CreditListActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return  new CreditListFragment();
    }
}
