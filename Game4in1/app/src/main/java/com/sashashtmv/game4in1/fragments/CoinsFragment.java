package com.sashashtmv.game4in1.fragments;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdView;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.activity.SettingsActivity;
import com.sashashtmv.game4in1.constants.AppConstants;
import com.sashashtmv.game4in1.model.PreferenceHelper;
import com.sashashtmv.game4in1.utilities.AdsUtilities;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoinsFragment extends Fragment {
    private Toolbar toolbar;
    private int coins;
    LinearLayout llTwitter;
    LinearLayout llFacebook;
    Button buy;
    static PreferenceHelper mPreferenceHelper;
    int countCoins;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public static CoinsFragment newInstance(PreferenceHelper preferenceHelper) {

        CoinsFragment fragment = new CoinsFragment();
        mPreferenceHelper = preferenceHelper;
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }


    public CoinsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coins, container, false);

        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar_coins); //находишь тулбар
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background); //помещаешь иконку слева
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        AdsUtilities.getInstance(getActivity()).showBannerAd((AdView) view.findViewById(R.id.adsView));

        countCoins = mPreferenceHelper.getInt("gold");

        llTwitter = view.findViewById(R.id.ll_twit);
        llFacebook = view.findViewById(R.id.ll_facebook);
        buy = view.findViewById(R.id.bt_bay_coins);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        llTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("Tweet text"),
                        urlEncode("https://www.google.fi/"));
                Intent intent = null;
                try {
                    intent = new TweetComposer.Builder(getActivity())
                            .text("Game4in1")
                            .url(new URL("https://play.google.com/store/apps/details?id=com.sashashtmv.myquiz"))
                            .createIntent();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

// Narrow down to official Twitter app, if available:
                List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivityForResult(intent, AppConstants.REQUEST_CODE_TWITTER);
                //startActivity(intent);
//                String token ="<Your access token>";
//                String secret = "<Your access token secret>";
//                String message = "";
//
//                AccessToken a = new AccessToken(token,secret);
//                Twitter twitter = new TwitterFactory().getInstance();
//                twitter.setOAuthConsumer("<Your consumer key>", "<Your consumer secret>");
//                twitter.setOAuthAccessToken(a);
//                try {
//                    twitter.updateStatus(message);
//                } catch (TwitterException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
            }
        });

        llFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String urlToShare = "http://mithsoft.net/home/";
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
//
//                // See if official Facebook app is found
//                boolean facebookAppFound = false;
//                List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
//                for (ResolveInfo info : matches) {
//                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
//                        intent.setPackage(info.activityInfo.packageName);
//                        facebookAppFound = true;
//                        break;
//                    }
//                }
//                // As fallback, launch sharer.php in a browser
//                if (!facebookAppFound) {
//                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
//                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
//                }
//                startActivityForResult(intent, AppConstants.REQUEST_CODE_FACEBOOK);
//                Log.i(TAG, "onClick: " );
//                ShareLinkContent content = new ShareLinkContent.Builder()
//                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                        .build();
                //ShareDialog.show(this, content);
                callbackManager = CallbackManager.Factory.create();
                shareDialog = new ShareDialog(CoinsFragment.this);
                // this part is optional
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Log.i(TAG, "onSuccess: success" +  " - " + (countCoins + 15));
                    }

                    @Override
                    public void onCancel() {
                        Log.i(TAG, "onCancel: cancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.i(TAG, "onError: error");

                    }
                });
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                            .build();
                    shareDialog.show(linkContent);
                        //mPreferenceHelper.putInt("gold", countCoins + 15);
                        Log.i(TAG, "onShow: show" + linkContent.toString());
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_CODE_TWITTER) {
            if (resultCode == RESULT_OK) {
                mPreferenceHelper.putInt("gold", countCoins + 15);
                        Log.i(TAG, "onActivityResult: twitter" + resultCode );
                // do your stuff
            }
        }else if(requestCode == AppConstants.REQUEST_CODE_FACEBOOK) {
            if (resultCode == RESULT_OK) {
                mPreferenceHelper.putInt("gold", countCoins + 15);
                        Log.i(TAG, "onActivityResult: facebook" + resultCode );
                // do your stuff
            }
        }
                        Log.i(TAG, "onActivityResult: result " + resultCode + " request - "  + requestCode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        Intent intent;
//        if (id == R.id.icon_call) {
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
//            startActivity(intent);
//            return true;
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }


    public void updateCoins(int countCoins) {
        coins = countCoins;
    }
}
