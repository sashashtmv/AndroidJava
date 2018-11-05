package com.sashashtmv.fastcredit.loans;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sashashtmv.fastcredit.SingleFragmentActivity;

public class LoanListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, LoanListActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return  new LoanListFragment();
    }
}
