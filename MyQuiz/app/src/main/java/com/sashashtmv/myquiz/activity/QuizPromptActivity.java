package com.sashashtmv.myquiz.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.constants.AppConstants;
import com.sashashtmv.myquiz.data.preference.AppPreference;
import com.sashashtmv.myquiz.utilities.ActivityUtilities;
import com.sashashtmv.myquiz.utilities.AdsUtilities;


public class QuizPromptActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;
    private Button mBtnYes, mBtnNo;
    private TextView firstText, thirdtext;
    //номер теста, предыдущий результат и счетчик вопросов
    private String categoryId, score, questionsCount;
    //private String questionsCount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initListener();
    }

    //инициализируем переменные. Номер теста получаем из интента, а счетчики из метода getQuizResult, он получает значения из sharedPreferences
    private void initVar() {
        mActivity = QuizPromptActivity.this;
        mContext = mActivity.getApplicationContext();

        Intent intent = getIntent();
        if (intent != null) {
            categoryId = intent.getStringExtra(AppConstants.BUNDLE_KEY_INDEX);
            score = AppPreference.getInstance(mContext).getString(categoryId);
            questionsCount = AppPreference.getInstance(mContext).getString(categoryId + AppConstants.QUESTIONS_IN_TEST);
        }
    }

    //инициализируем экранные компоненты, а также проверяем, если счетчики вопросов и правильных ответов содержат значения,
    //используем их в первом текстовом  поле для отображения результатов последнего прохождения теста
    private void initView() {
        setContentView(R.layout.activity_quiz_prompt);

        mBtnYes = (Button) findViewById(R.id.btn_yes);
        mBtnNo = (Button) findViewById(R.id.btn_no);

        firstText = (TextView) findViewById(R.id.first_text);
        thirdtext = (TextView) findViewById(R.id.third_text);

        if (score != null && questionsCount != null) {
            firstText.setText(getString(R.string.quiz_promt_first_text, score, questionsCount));
            thirdtext.setText(R.string.quiz_promt_third_text);
        }

        initToolbar(true);
        setToolbarTitle(getString(R.string.quiz_prompt));
        enableUpButton();

        // show full-screen ads
        AdsUtilities.getInstance(mContext).showFullScreenAd();
        // show banner ads
        AdsUtilities.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adsView));

    }


    private void initListener() {
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtilities.getInstance().invokeCommonQuizActivity(mActivity, QuizActivity.class, categoryId, true);
            }
        });
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtilities.getInstance().invokeNewActivity(mActivity, MainActivity.class, true);
            }
        });
    }

    //определяет стрелку назад в тулбаре
    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityUtilities.getInstance().invokeNewActivity(mActivity, MainActivity.class, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //определяет системную кнопку назад и добавляет ей аналогичное действие
    @Override
    public void onBackPressed() {
        ActivityUtilities.getInstance().invokeNewActivity(mActivity, MainActivity.class, true);
    }



}
