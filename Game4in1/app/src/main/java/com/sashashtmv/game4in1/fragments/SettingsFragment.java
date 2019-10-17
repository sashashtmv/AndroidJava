package com.sashashtmv.game4in1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.constants.AppConstants;
import com.sashashtmv.game4in1.model.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SettingsFragment extends PreferenceFragment  {
    private BillingClient mBillingClient;
    private String mSkuId1 ;
    private String mSkuId2 ;
    private Map<String, SkuDetails> mSkuDetailsMap = new HashMap<>();

    //BillingProcessor bp;
    Preference preferencepurchase;
    Preference preferencesubscribe;
    int countCoins;
    private PreferenceHelper mPreferenceHelper;

    AlertDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        mSkuId1 = SUBSCRIPTION_ID();
        mSkuId2 = PRODUCT_ID();

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();
        countCoins = mPreferenceHelper.getInt("gold");

        String license = getResources().getString(R.string.google_play_license);
        initBilling();
        //bp = new BillingProcessor(getActivity(), license, this);
        //bp.loadOwnedPurchasesFromGoogle();
        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@BillingClient.BillingResponse int responseCode,
                                          String outToken) {
                if (responseCode == BillingClient.BillingResponse.OK) {
                    mPreferenceHelper.putInt("gold", countCoins + 1000);
                    // Handle the success of the consume operation.
                    // For example, increase the number of player's coins,
                    // that provide temporary benefits
                }
            }
        };
        mBillingClient.consumeAsync(mSkuId2, listener);



        preferencesubscribe = findPreference("subscribe");

        if (null != license && !license.equals("")) {

            preferencesubscribe
                    .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            //bp.subscribe(getActivity(), SUBSCRIPTION_ID());
                            //bp.purchase(getActivity(), SUBSCRIPTION_ID());
                            launchBilling(mSkuId1);
                            return true;
                        }
                    });

            if (getIsSubscribe(getActivity())) {
                //mPreferenceHelper.putInt("gold", countCoins + 1000);
//				preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
            }
        }

        preferencepurchase = findPreference("purchase");

        if (null != license && !license.equals("")) {


            preferencepurchase
                    .setOnPreferenceClickListener(new OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            //bp.purchase(getActivity(), PRODUCT_ID());
                            launchBilling(mSkuId2);
                            return true;
                        }
                    });

            if (getIsPurchased(getActivity())) {
                //mPreferenceHelper.putInt("gold", countCoins + 10000);
                //preferencepurchase.setIcon(R.drawable.ic_action_action_done);
//				preferencesubscribe.setEnabled(false);
//				preferencesubscribe.setSelectable(false);
            }
        }
    }

    private void initBilling() {
        mBillingClient = BillingClient.newBuilder(getActivity()).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
                if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
                    payComplete();//сюда мы попадем когда будет осуществлена покупка

                }
            }
        }).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    //здесь мы можем запросить информацию о товарах и покупках
                    querySkuDetails(); //запрос о товарах
                    List<Purchase> purchasesList = queryPurchases(); //query for purchases

                    //if the purchase has already been made to give the goods
                    for (int i = 0; i < purchasesList.size(); i++) {
                        String purchaseId = purchasesList.get(i).getSku();
                        if(TextUtils.equals(mSkuId2, purchaseId)) {
                            payComplete();
                        }
                    }

                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                //сюда мы попадем если что-то пойдет не так
            }
        });

    }
    private void querySkuDetails() {
        SkuDetailsParams.Builder skuDetailsParamsBuilder = SkuDetailsParams.newBuilder();
        List<String> skuList = new ArrayList<>();
        skuList.add(mSkuId1);
        skuList.add(mSkuId2);
        skuDetailsParamsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuDetailsParamsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                if (responseCode == 0) {
                    for (SkuDetails skuDetails : skuDetailsList) {
                        mSkuDetailsMap.put(skuDetails.getSku(), skuDetails);
                    }
                }
            }
        });
    }
    private List<Purchase> queryPurchases() {
        Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
        return purchasesResult.getPurchasesList();
    }

    private void payComplete() {
        Log.i(TAG, "payComplete: - " + "hello world");
    }

    public void launchBilling(String skuId) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(mSkuDetailsMap.get(skuId))
                .build();
        mBillingClient.launchBillingFlow(getActivity(), billingFlowParams);
    }


//    @Override
//    public void onBillingInitialized() {
//
//    }

//    @Override
//    public void onProductPurchased(String productId, TransactionDetails details) {
//        if (productId.equals(PRODUCT_ID())) {
//            setIsPurchased(false, getActivity());
//            //preferencepurchase.setIcon(R.drawable.ic_action_action_done);
//            Toast.makeText(getActivity(), getResources().getString(R.string.settings_purchase_success), Toast.LENGTH_LONG).show();
//            Log.v("TAG", "Purchase purchased");
//        }
//
//        if (productId.equals(SUBSCRIPTION_ID())) {
//            setIsSubscribe(true, getActivity());
//            //preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
//            Toast.makeText(getActivity(), getResources().getString(R.string.settings_purchase_success), Toast.LENGTH_LONG).show();
//            Log.v("TAG", "Subscribe purchased");
//        } else {
//            setIsSubscribe(false, getActivity());
////			preferencesubscribe.getIcon().setVisible(false, true);
//        }
//        Log.v("TAG", "Purchase or subscribe purchased");
//    }
//
//    @Override
//    public void onBillingError(int errorCode, Throwable error) {
//        Toast.makeText(getActivity(), getResources().getString(R.string.settings_purchase_fail), Toast.LENGTH_LONG).show();
//        Log.v("TAG", "Error");
//    }
//
//    @Override
//    public void onPurchaseHistoryRestored() {
//        if (bp.isPurchased(PRODUCT_ID())) {
//            setIsPurchased(true, getActivity());
//            Log.v("TAG", "Purchase actually restored");
//            preferencepurchase.setIcon(R.drawable.ic_action_action_done);
//            if (dialog != null) dialog.cancel();
//            Toast.makeText(getActivity(), getResources().getString(R.string.settings_restore_purchase_success), Toast.LENGTH_LONG).show();
//        }
//
//        if (bp.isSubscribed(SUBSCRIPTION_ID())) {
//            setIsSubscribe(true, getActivity());
//            Log.v("TAG", "Subscribe actually restored");
//            preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
//            if (dialog != null) dialog.cancel();
//        } else {
//            setIsSubscribe(false, getActivity());
//            preferencesubscribe.getIcon().setVisible(false, true);
//        }
//        Log.v("TAG", "Purchase and subscription restored called");
//    }


    public void setIsPurchased(boolean purchased, Context c) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(AppConstants.PRODUCT_ID_BOUGHT, purchased);
        editor.apply();
    }

    public void setIsSubscribe(boolean purchased, Context c) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, purchased);
        editor.apply();
    }

    public static boolean getIsPurchased(Context c) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        return prefs.getBoolean(AppConstants.PRODUCT_ID_BOUGHT, false);
    }

    public static boolean getIsSubscribe(Context c) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(c);

        return prefs.getBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, false);
    }

    private String PRODUCT_ID() {
        return getResources().getString(R.string.product_id);
    }

    private String SUBSCRIPTION_ID() {
        return getResources().getString(R.string.subscription_id);
    }


    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        //bp.handleActivityResult(requestCode, resultCode, intent);
    }


    @Override
    public void onDestroy() {
//        if (bp != null)
//            bp.release();

        super.onDestroy();
    }
}
