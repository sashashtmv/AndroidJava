package com.sashashtmv.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG_CHEAT = "CheatActivity";
    private static final String KEY_RESULT = "result";// ключ для записи пары ключ-значения в объект savedInstanceState, в котором хранится информация о конфигурации активности
    private TextView mAnswer_text;
    private boolean mAnswerIsTrue;
    private boolean resultCheat=false;
    private Button mShowAnswer;
    public static final String EXTRA_ANSWER_IS_TRUE = "com.sashashtmv.geoquiz.answer_is_true";//константа для применения ключа при записи пары ключ-значение в дополнение к интенту, переданному из родительской активности
    public static final String EXTRA_ANSWER_SHOWN = "com.sashashtmv.geoquiz.answer_shown";//константа для применения ключа при записи пары ключ-значение в дополнение к интенту, переданному из дочерней активности к родительской

    //метод для создания интента и дополнения к нему данных, передаваемых из главной активности
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    // метод по декодированию интента результата от дочерней к родительской активности
    public static boolean wasAnswerShown(Intent result) {

        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);//получение данных из дополнения, переданного интентом из главной активности. Если такого ключа нету, то передаётся дефолтное значение  false
        mAnswer_text = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) mAnswer_text.setText(R.string.true_button);
                else mAnswer_text.setText(R.string.false_button);
                resultCheat = true;
                setAnswerShownResult(resultCheat);
            }
        });
        if(savedInstanceState!=null) {
            resultCheat = savedInstanceState.getBoolean(KEY_RESULT);
            setAnswerShownResult(resultCheat);
        }
    }

    // Метод создания интента для возврата результата запроса из родительской активности
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        savedInstaceState.putBoolean(KEY_RESULT, resultCheat);

    }
}