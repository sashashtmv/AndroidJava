package com.sashashtmv.fastcredit.other;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sashashtmv.fastcredit.SingleFragmentActivity;

public class OtherListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, OtherListActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return  new OtherListFragment();
    }
}
