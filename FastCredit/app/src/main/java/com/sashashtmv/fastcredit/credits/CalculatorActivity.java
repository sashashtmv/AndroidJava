package com.sashashtmv.fastcredit.credits;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sashashtmv.fastcredit.SingleFragmentActivity;

public class CalculatorActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CalculatorActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {

        return CalculatorFragment.newInstance();
    }
}
