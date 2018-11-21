package com.sashashtmv.fastcredit;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.onesignal.OneSignal;

public class StartActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        return CountriesFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).init();
    }
}
