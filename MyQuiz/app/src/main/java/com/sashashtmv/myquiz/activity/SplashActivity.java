package com.sashashtmv.myquiz.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.utilities.ActivityUtilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Animation mAnimation;
    private ProgressBar mProgressBar;
    private ConstraintLayout mConstraintLayout;

    //констатнта для длительности отображения экрана приветствия
    private static final int SPLASH_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = findViewById(R.id.progressBar);
        mConstraintLayout = findViewById(R.id.splash_layout);
        mImageView = findViewById(R.id.iv_splash_icon);
        mAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
    }

    //создаем отдельный поток и в нем отображаем прогрессбар
    //применяем анимацию к изображению и создаем слушателя к изображению
    private void initFunctionality(){
        mConstraintLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
                mImageView.startAnimation(mAnimation);
                mAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
            // по окончании анимации будем запускать главный экран приложения
                        ActivityUtilities.getInstance().invokeNewActivity(SplashActivity.this, MainActivity.class, true);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }, SPLASH_DURATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFunctionality();
    }
}
