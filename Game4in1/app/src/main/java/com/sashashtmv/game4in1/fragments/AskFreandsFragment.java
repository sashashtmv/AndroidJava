package com.sashashtmv.game4in1.fragments;


import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.utilities.AdsUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AskFreandsFragment extends Fragment {

    private LinearLayout mViber;
    private LinearLayout mWhatsapp;
    private LinearLayout mTelegram;
    private LinearLayout mFacebookMessenger;
    private  File mFile;
    private boolean consumedIntent;
    Intent intent;
    Bitmap bitmap;
    private final String SAVED_INSTANCE_STATE_CONSUMED_INTENT = "SAVED_INSTANCE_STATE_CONSUMED_INTENT";


    public AskFreandsFragment() {
        // Required empty public constructor
    }

    public static AskFreandsFragment newInstance() {
        AskFreandsFragment fragment = new AskFreandsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        final FragmentManager manager = getFragmentManager();
//        if(manager!=null) {
//            manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                @Override
//                public void onBackStackChanged() {
//                    Fragment fr = manager.findFragmentById(R.id.content_frame);
////                int index = getFragmentManager().getBackStackEntryCount() - 1;
//                    if (fr != null) {
//                        Log.e("fragment=", fr.getClass().getSimpleName());
//                    }
//                }
//            });
//        }
        View view = inflater.inflate(R.layout.fragment_ask_freands, container, false);

        mViber = view.findViewById(R.id.ll_viber);
        mWhatsapp = view.findViewById(R.id.ll_whats_app);
        mTelegram = view.findViewById(R.id.ll_telegram);
        mFacebookMessenger = view.findViewById(R.id.ll_facebook_messenger);
        if( savedInstanceState != null ) {
            consumedIntent = savedInstanceState.getBoolean(SAVED_INSTANCE_STATE_CONSUMED_INTENT);
        }

        AdsUtilities.getInstance(getActivity()).showBannerAd((AdView) view.findViewById(R.id.adsView));

        mViber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               saveBitmapAsFile("com.viber.voip");
                //getActivity().onBackPressed();

            }
        });
        mWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmapAsFile("com.whatsapp");
                //getActivity().onBackPressed();

            }
        });
        mTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmapAsFile("org.telegram.messenger");
                //getActivity().onBackPressed();
            }
        });
        mFacebookMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmapAsFile("com.facebook.orca");
                //getActivity().onBackPressed();
            }
        });

        return view;
    }


    public Bitmap takeScreenshot() {
        LevelFragment fragment = (LevelFragment)getFragmentManager().findFragmentByTag("level");
        String word = fragment.getItem().getWord();

        Log.e("word=", "word - " + word);
        View rootView = fragment.mView;
        View screenView = rootView.getRootView();
        fragment.mView.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
//        bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        try {
//        if(bitmap.hashCode() == Bitmap.createBitmap(screenView.getDrawingCache()).hashCode()){
//            Log.e("files=", "files - true");
//        }
//
//        }catch (Exception e){}

        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();

//        boolean shouldThisIntentTriggerMyCode = true;
         intent = getActivity().getIntent();
//        boolean launchedFromHistory = intent != null ? (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0 : false;
//        if( !launchedFromHistory && shouldThisIntentTriggerMyCode && !consumedIntent ) {
//            consumedIntent = true;
//            //execute the code that should be executed if the activity was not launched from history
//        }
        intent.replaceExtras(new Bundle());
        intent.setAction(null);
        intent.setData(null);
        intent.setFlags(0);
        //getActivity().getIntent().setExtra(Intent.EXTRA_STREAM, "");
        getActivity().setIntent(null);
        getActivity().setIntent(intent);
        //getActivity().setIntent(new Intent());
//        if(mFile!=null) {
////            Log.e("file=", mfile.getAbsolutePath());
//            mFile=null;
            //Log.e("file=", file.getAbsolutePath());
//        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.clear();
    }


    @Override
    public void onPause() {
        super.onPause();
//        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        file.delete();

    }

    private void saveBitmapAsFile(String s) {
        Bitmap bitmapToShare = takeScreenshot();
        Log.e("bitmap=", "" + bitmapToShare.hashCode());
        if (bitmapToShare != null) {

//            if(Files.exists("screenshot.jpg",null )){
//
//            }
            File saveFile = getMainDirectoryName(getActivity());//get the path to save screenshot
            File file = store(bitmapToShare, "screenshot"  + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file,s);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(getActivity(), "Sending Error", Toast.LENGTH_SHORT).show();
    }

    public static File getMainDirectoryName(Context context) {
        //Here we will use getExternalFilesDir and inside that we will make our Demo folder
        //benefit of getExternalFilesDir is that whenever the app uninstalls the images will get deleted automatically.
        File mainDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Demo");

        //If File is not present create directory
        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                Log.e("Create Directory", "Main Directory Created : " + mainDir);
        }
        return mainDir;
    }

    public static File store(Bitmap bm, String fileName, File saveFilePath) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists())
            dir.mkdirs();

        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        if(file.length() > 0){
            try {
                FileWriter fstream1 = new FileWriter(file.getPath());// конструктор с одним параметром - для перезаписи
                BufferedWriter out1 = new BufferedWriter(fstream1); //  создаём буферезированный поток
                out1.write(""); // очищаем, перезаписав поверх пустую строку
                out1.close(); // закрываем
            } catch (Exception e)
            {Log.e("File don't clear", "fall ");}

        }
        try {
            Log.e("File length", "count - " + file.length());
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            Log.e("File length", "count - " + file.length());
        return file;
    }


    private void shareScreenshot(File file, String s) {

        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Log.e("File uri", "uri - " +uri.toString());
        Log.e("File", "file - " +file.length());
        LevelFragment fragment = (LevelFragment)getFragmentManager().findFragmentByTag("level");
        String word = fragment.getItem().getWord();
        if(getActivity().getIntent() != null){
//            getActivity().getIntent().removeFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//            getActivity().getIntent().removeFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent = getActivity().getIntent();
        }else  intent = new Intent();

        Intent intent = new Intent();

        intent.replaceExtras(new Bundle());
        intent.setAction(null);
        intent.setData(null);
        intent.setFlags(0);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        //intent.removeFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        //intent.removeFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
//        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setPackage(s);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));//pass uri here
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, word);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Помоги мне разгадать слово)))");

        startActivity(Intent.createChooser(intent, ""));


    }


}
