package com.sashashtmv.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";// ключ для логирования
    private static final String KEY_INDEX = "index";// ключ для записи пары ключ-значения в объект savedInstanceState, в котором хранится информация о конфигурации активности
    private static final String KEY_CHEATER = "cheat";// ключ для записи пары ключ-значения в объект savedInstanceState, в котором хранится информация о конфигурации активности
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_ocean,true),
            new Question(R.string.question_city_in_turkey, false),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    //обновляем наш вопрос для викторины
    private void updateQuestion(){
        //Log.d(TAG, "Update question text for question #" + mCurrentIndex, new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    //проверяем ответ на правильность
    private void chekAnswer(boolean userPresedTrue){
        boolean answerIsTrue  = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if(mIsCheater)messageResId = R.string.judgment_toast;
        else {
            if (userPresedTrue == answerIsTrue) messageResId = R.string.correct_toast;
            else messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        mCheatButton = findViewById(R.id.cheat_button);
        // проверяем значение переменной объекта Bundle, если оно присутствует, то присваиваем текущее значение индекса массива с вопросами нашей переменной индекса
        if(savedInstanceState!=null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
        }
        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekAnswer(true);
                mIsCheater=false;
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chekAnswer(false);
                mIsCheater=false;
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsCheater==false) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }else Toast.makeText(QuizActivity.this, R.string.input_answer, Toast.LENGTH_LONG).show();
            }
        });
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsCheater==false) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    if (mCurrentIndex < 0) mCurrentIndex = 0;
                    updateQuestion();
                }else Toast.makeText(QuizActivity.this, R.string.input_answer, Toast.LENGTH_LONG).show();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionBank.length;
                updateQuestion();
            }
        });
    }
// переопределение метода получения результата из дочерней активности
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null) return;
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    // переопределяем метод, который вносит в объект Bundle значение переменной индекса, которое нужно сохранить во время смены конфигурации активности
    @Override
    public void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        Log.i(TAG, "onSaveInstanceState called");
        savedInstaceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstaceState.putBoolean(KEY_CHEATER, mIsCheater);

    }

    // переопределяем 5 методов жизненного цикла активности
//    @Override
//    public void onStart(){
//        super.onStart();
//        Log.d(TAG, "onStart() called");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause() called");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume() called");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop() called");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy() called");
//    }
}
