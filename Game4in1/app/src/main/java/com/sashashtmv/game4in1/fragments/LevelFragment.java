package com.sashashtmv.game4in1.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sashashtmv.game4in1.MainActivity;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.database.DBHelper;
import com.sashashtmv.game4in1.model.Item;
import com.sashashtmv.game4in1.model.ModelLevel;
import com.sashashtmv.game4in1.model.PreferenceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment {

    private Toolbar toolbar;
    private List<ModelLevel> mItems;
    private ModelLevel mItem;
    public DBHelper mDBHelper;

    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;

    private TextView mLevelNumber;
    LinearLayout myContainer;
    private ImageView mLetter1;
    private ImageView mLetter2;
    private ImageView mLetter3;
    private ImageView mLetter4;
    private ImageView mLetter5;
    private ImageView mLetter6;
    private ImageView mLetter7;
    private ImageView mLetter8;
    private ImageView mLetter9;
    private ImageView mLetter10;

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    private TextView mTextView7;
    private TextView mTextView8;
    private TextView mTextView9;
    private TextView mTextView10;

    private TextView mRandomLetter1;
    private TextView mRandomLetter2;
    private TextView mRandomLetter3;
    private TextView mRandomLetter4;
    private TextView mRandomLetter5;
    private TextView mRandomLetter6;
    private TextView mRandomLetter7;
    private TextView mRandomLetter8;
    private TextView mRandomLetter9;
    private TextView mRandomLetter10;
    private TextView mRandomLetter11;
    private TextView mRandomLetter12;
    private TextView mCountGold;
    private Map<TextView, String> map;
    ArrayList<TextView> mLetters;

    private Button mFreands;
    private Button mAdvices;
    private int countCoins;
    private PreferenceHelper mPreferenceHelper;
    private LinearLayout coinsLayout;
    TextView[] mas;
    String[] letters;

    //private Button mAdvice;


    private int level = 0;
    public MainActivity mMainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            mMainActivity = (MainActivity)getActivity();
        }
//        addTaskFromDB();
    }
    public interface callbackResult{
        void onCreatResult(ModelLevel item);
    }
    public interface callbackCoinsFragment{
        void onCreatCoinsFragment();
    }
    public interface callbackAskFreandsFragment{
        void onCreatAskFreandsFragment();
    }
    public interface callbackAdvicesFragment{
        void onCreatAdvicesFragment();
    }


    public LevelFragment() {
        // Required empty public constructor
    }

    public static LevelFragment newInstance() {
        LevelFragment fragment = new LevelFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, fragment.mDate);
//        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_level, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_level);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        map = new HashMap<>();
        mLetters = new ArrayList<>();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        mImage1 = view.findViewById(R.id.image1);
        mImage2 = view.findViewById(R.id.image2);
        mImage3 = view.findViewById(R.id.image3);
        mImage4 = view.findViewById(R.id.image4);

        mLevelNumber = view.findViewById(R.id.tv_number_level);
        mCountGold = view.findViewById(R.id.tv_count_gold);
        mLetter1 = view.findViewById(R.id.iv_liter1);
        mLetter2 = view.findViewById(R.id.iv_liter2);
        mLetter3 = view.findViewById(R.id.iv_liter3);
        mLetter4 = view.findViewById(R.id.iv_liter4);
        mLetter5 = view.findViewById(R.id.iv_liter5);
        mLetter6 = view.findViewById(R.id.iv_liter6);
        mLetter7 = view.findViewById(R.id.iv_liter7);
        mLetter8 = view.findViewById(R.id.iv_liter8);
        mLetter9 = view.findViewById(R.id.iv_liter9);
        mLetter10 = view.findViewById(R.id.iv_liter10);
        mTextView1 = view.findViewById(R.id.tv_liter1);
        mTextView2 = view.findViewById(R.id.tv_liter2);
        mTextView3 = view.findViewById(R.id.tv_liter3);
        mTextView4 = view.findViewById(R.id.tv_liter4);
        mTextView5 = view.findViewById(R.id.tv_liter5);
        mTextView6 = view.findViewById(R.id.tv_liter6);
        mTextView7 = view.findViewById(R.id.tv_liter7);
        mTextView8 = view.findViewById(R.id.tv_liter8);
        mTextView9 = view.findViewById(R.id.tv_liter9);
        mTextView10 = view.findViewById(R.id.tv_liter10);
        mRandomLetter1 = view.findViewById(R.id.tv_letter1);
        mRandomLetter2 = view.findViewById(R.id.tv_letter2);
        mRandomLetter3 = view.findViewById(R.id.tv_letter3);
        mRandomLetter4 = view.findViewById(R.id.tv_letter4);
        mRandomLetter5 = view.findViewById(R.id.tv_letter5);
        mRandomLetter6 = view.findViewById(R.id.tv_letter6);
        mRandomLetter7 = view.findViewById(R.id.tv_letter7);
        mRandomLetter8 = view.findViewById(R.id.tv_letter8);
        mRandomLetter9 = view.findViewById(R.id.tv_letter9);
        mRandomLetter10 = view.findViewById(R.id.tv_letter10);
        mRandomLetter11 = view.findViewById(R.id.tv_letter11);
        mRandomLetter12 = view.findViewById(R.id.tv_letter12);
        mFreands = view.findViewById(R.id.bt_ask_freands);
        mAdvices = view.findViewById(R.id.bt_advices);
        coinsLayout = view.findViewById(R.id.ll_gold);


        setUi();

        coinsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.onCreatCoinsFragment();
            }
        });

        mFreands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.onCreatAskFreandsFragment();
            }
        });
        mAdvices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.onCreatAdvicesFragment();
            }
        });

        mLetter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView1);
            }
        });
        mLetter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView2);
            }
        });
        mLetter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView3);
            }
        });
        mLetter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView4);
            }
        });
        mLetter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView5);
            }
        });
        mLetter6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView6);
            }
        });
        mLetter7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView7);
            }
        });
        mLetter8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView8);
            }
        });
        mLetter9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView9);
            }
        });
        mLetter10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLetter(mTextView10);
            }
        });
        mRandomLetter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter1);
            }
        });
        mRandomLetter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter2);
            }
        });
        mRandomLetter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter3);
            }
        });
        mRandomLetter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter4);
            }
        });
        mRandomLetter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter5);
            }
        });
        mRandomLetter6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter6);
            }
        });
        mRandomLetter7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter7);
            }
        });
        mRandomLetter8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter8);
            }
        });
        mRandomLetter9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter9);
            }
        });
        mRandomLetter10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter10);
            }
        });
        mRandomLetter11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter11);
            }
        });
        mRandomLetter12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLetter(mRandomLetter12);
            }
        });

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
            for(int i = 0; i < 12; i++){
                mPreferenceHelper.putString("letter" + i, "");
            }
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void check() {
        String word = "";
        for(int i = 0; i < mLetters.size(); i++){
            word += mLetters.get(i).getText().toString();
        }
        if (mItem.getWord().toUpperCase().equals(word)) {
            mItem.setStatus(ModelLevel.STATUS_DONE);
            int position = mItems.indexOf(mItem);
            ModelLevel level = (ModelLevel) mItems.get(position + 1);
            if (level.getStatus() == ModelLevel.STATUS_NOT_AVALABLE) {

                mDBHelper.update().status(level.getTimeStamp(), ModelLevel.STATUS_AVALABLE);
            }
            mDBHelper.update().status(mItem.getTimeStamp(), ModelLevel.STATUS_DONE);
            for(int i = 0; i < 12; i++){
                mPreferenceHelper.putString("letter" + i, "");
            }
            mMainActivity.onCreatResult(mItem);
        }

    }

    public  void sendLetter( TextView randomLetter){
        if(randomLetter.getText().toString().length()>0) {
            for (int i = 0; i < mLetters.size(); i++) {
                if (mLetters.get(i).getText().toString().equals("")) {
                    mLetters.get(i).setText(map.get(randomLetter));
                    randomLetter.setText("");
                    break;
                }
            }
            //sendLetter(mRandomLetter1);
        }
        check();
//            if (mItem.getWord().toUpperCase().equals(check())) {
//                //ToDo
//                mItem.setStatus(ModelLevel.STATUS_DONE);
//                int position = mItems.indexOf(mItem);
//                ModelLevel level = (ModelLevel)mItems.get(position+1);
//                if(level.getStatus() == ModelLevel.STATUS_NOT_AVALABLE) {
//
//                mDBHelper.update().status(level.getTimeStamp(), ModelLevel.STATUS_AVALABLE);
//                }
//                mDBHelper.update().status(mItem.getTimeStamp(), ModelLevel.STATUS_DONE);
//                mMainActivity.onCreatResult(mItem);
//                Log.i(TAG, "onClick: check - " + check());
//            }
    }
    public  void showLetter(){
        for(int i = 0; i < mItem.getWord().length(); i++){
            String temp = mItem.getWord().charAt(i)+"";
            if(mLetters.get(i).getText().toString().equals("")){
                mPreferenceHelper.putString("letter"+i, temp);
                check();
                break;
            }
        }
    }

    public void returnLetter(TextView returnText){
        if(returnText.getText().toString().length() > 0) {
            String str = returnText.getText().toString();
            TextView textView;
            Set<Map.Entry<TextView,String>> entrySet=map.entrySet();

            for (Map.Entry<TextView,String> pair : entrySet) {
                if (str.equals(pair.getValue())) {
                    textView = pair.getKey();// нашли наше значение и возвращаем  ключ
                    textView.setText(str);
                }
            }
            returnText.setText("");
        }
    }

    public void setUi() {
        mImage1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mItem.getBitmap1(), "drawable", getActivity().getPackageName())));
        mImage2.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mItem.getBitmap2(), "drawable", getActivity().getPackageName())));
        mImage3.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mItem.getBitmap3(), "drawable", getActivity().getPackageName())));
        mImage4.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(mItem.getBitmap4(), "drawable", getActivity().getPackageName())));
        countCoins = mPreferenceHelper.getInt("gold");
        mLevelNumber.setText(" " + level);
        mCountGold.setText("" + countCoins);
        mas = new TextView[]{mRandomLetter1, mRandomLetter2, mRandomLetter3, mRandomLetter4, mRandomLetter5, mRandomLetter6, mRandomLetter7,
                mRandomLetter8, mRandomLetter9, mRandomLetter10, mRandomLetter11, mRandomLetter12};
        letters = new String[]{"Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э", "Я", "Ч", "С", "М", "Т", "Ь", "Б", "Ю"};
//      наполнение буквами искомого слова в рандомном порядке нижнего бокса с буквами
        for (int i = 0; i < mItem.getWord().length(); i++) {
            int random = new Random().nextInt((11 - 0) + 1) + 0;
            Log.i(TAG, "setUi: random number - " + random);
            while (true) {
                TextView temp = mas[random];
                String str = temp.getText().toString();
                if (str.equals("")) {
                    String buff = mItem.getWord().charAt(i) + "";
                    temp.setText(buff.toUpperCase());
                    map.put(temp, buff.toUpperCase());
                    break;
                } else {
                    random = new Random().nextInt((11 - 0) + 1) + 0;
                }
            }
        }
//      наполнение рандомными буквами нижнего бокса с буквами
        for (int i = 0; i < mas.length; i++) {
            int random = new Random().nextInt((29 - 0) + 1) + 0;
            if (mas[i].getText().toString().equals("")) {
                mas[i].setText(letters[random]);
                map.put(mas[i], letters[random]);
            }
        }
        if (mItem.getWord().length() == 1) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
        }
        if (mItem.getWord().length() == 2) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
        }
        if (mItem.getWord().length() == 3) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
        }
        if (mItem.getWord().length() == 4) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
        }
        if (mItem.getWord().length() == 5) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
        }
        if (mItem.getWord().length() == 6) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetter6.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
            mLetters.add(mTextView6);
        }
        if (mItem.getWord().length() == 7) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetter6.setVisibility(View.VISIBLE);
            mLetter7.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
            mLetters.add(mTextView6);
            mLetters.add(mTextView7);
        }
        if (mItem.getWord().length() == 8) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetter6.setVisibility(View.VISIBLE);
            mLetter7.setVisibility(View.VISIBLE);
            mLetter8.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
            mLetters.add(mTextView6);
            mLetters.add(mTextView7);
            mLetters.add(mTextView8);
        }
        if (mItem.getWord().length() == 9) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetter6.setVisibility(View.VISIBLE);
            mLetter7.setVisibility(View.VISIBLE);
            mLetter8.setVisibility(View.VISIBLE);
            mLetter9.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
            mLetters.add(mTextView6);
            mLetters.add(mTextView7);
            mLetters.add(mTextView8);
            mLetters.add(mTextView9);
        }
        if (mItem.getWord().length() == 10) {
            mLetter1.setVisibility(View.VISIBLE);
            mLetter2.setVisibility(View.VISIBLE);
            mLetter3.setVisibility(View.VISIBLE);
            mLetter4.setVisibility(View.VISIBLE);
            mLetter5.setVisibility(View.VISIBLE);
            mLetter6.setVisibility(View.VISIBLE);
            mLetter7.setVisibility(View.VISIBLE);
            mLetter8.setVisibility(View.VISIBLE);
            mLetter9.setVisibility(View.VISIBLE);
            mLetter10.setVisibility(View.VISIBLE);
            mLetters.add(mTextView1);
            mLetters.add(mTextView2);
            mLetters.add(mTextView3);
            mLetters.add(mTextView4);
            mLetters.add(mTextView5);
            mLetters.add(mTextView6);
            mLetters.add(mTextView7);
            mLetters.add(mTextView8);
            mLetters.add(mTextView9);
            mLetters.add(mTextView10);
        }
        //перебор для открытия буквы за золото
        for (int i = 0; i < mItem.getWord().length(); i++) {
            String preference = mPreferenceHelper.getString("letter" + i);
            if (preference != null | preference.length() > 0) {
                mLetters.get(i).setText(preference.toUpperCase());
                for (int j = 0; j < mas.length; j++) {
                    if (mas[j].getText().toString().equals(preference.toUpperCase())) {
                        mas[j].setText("");
                    }
                }
                check();
            }
        }

    }

    public void updateLevels(List<ModelLevel> items, ModelLevel item, DBHelper dbHelper) {
        mItems = items;
        mItem = item;
        mDBHelper = dbHelper;
        if (items.size() > 0) {
            level = items.indexOf(item) + 1;
            Log.i(TAG, "updateLevels: level - " + level);
        }
        //mLevelNumber.setText("" + level);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        for(int i = 0; i < 12; i++){
            PreferenceHelper.getInstance().init(getActivity());
            mPreferenceHelper = PreferenceHelper.getInstance();
            mPreferenceHelper.putString("letter" + i, "");
        }
    }

    public ModelLevel getItem() {
        return mItem;
    }
}
