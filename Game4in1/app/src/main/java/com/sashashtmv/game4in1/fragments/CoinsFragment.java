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
import android.widget.LinearLayout;

import com.sashashtmv.game4in1.R;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoinsFragment extends Fragment {
    private Toolbar toolbar;
    private int coins;
    LinearLayout llTwitter;

    public static CoinsFragment newInstance() {
        CoinsFragment fragment = new CoinsFragment();
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
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        llTwitter = view.findViewById(R.id.ll_twit);

        llTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode("Tweet text"),
                        urlEncode("https://www.google.fi/"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

// Narrow down to official Twitter app, if available:
                List<ResolveInfo> matches = getActivity().getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
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
        // Inflate the layout for this fragment
        return view;
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
        }
        catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }


    public void updateCoins(int countCoins) {
        coins = countCoins;
    }
}
