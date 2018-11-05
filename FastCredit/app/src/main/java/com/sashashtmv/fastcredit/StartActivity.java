package com.sashashtmv.fastcredit;

import android.support.v4.app.Fragment;

public class StartActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        return CountriesFragment.newInstance();
    }

}
