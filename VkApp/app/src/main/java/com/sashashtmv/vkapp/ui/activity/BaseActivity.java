package com.sashashtmv.vkapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.sashashtmv.vkapp.MyApplication;
import com.sashashtmv.vkapp.R;
import com.sashashtmv.vkapp.common.manager.MyFragmentManager;
import com.sashashtmv.vkapp.ui.fragment.BaseFragment;

import javax.inject.Inject;

public abstract class BaseActivity extends MvpAppCompatActivity {

        //через менеджер получим доступ к методам добавления и удаления фрагментов
    //для инициализации переменной из даггера, который инициализирует через ManagerModule
    @Inject
    MyFragmentManager mMyFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //инициализируем контентный лейаут
        FrameLayout parent = findViewById(R.id.main_wrapper);
        getLayoutInflater().inflate(getMainContentLayout(), parent);
    }

    //аннотация предполагает, что метод будет возвращать ссылку на этот лейаут
    @LayoutRes
    protected abstract int getMainContentLayout();

    //будем вызывать его при смене фрагмента. Нужен для того, чтобы менять заголовок тулбара и видимость кнопки fab
    public void  fragmentOnScreen(BaseFragment fragment){
        setToolbarTitle(fragment.createToolbarTitle(this));
    }

    //метод установки заголовка тулбара
    public void setToolbarTitle(String title){
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    //для удобства добавляем методы добавления и удаления фрагментов непосредственно в активити.
    // Будет использоваться для установки корневого фрагмента
    public void setContent(BaseFragment fragment){
        mMyFragmentManager.setFragment(this, fragment, R.id.main_wrapper);
    }
    //Для установки дополнительных фрагментов
    public void getContent(BaseFragment fragment){
        mMyFragmentManager.addFragment(this, fragment, R.id.main_wrapper);
    }
    //для удаления текущего фрагмента
    public boolean removeCurrentFragment(){
        return mMyFragmentManager.removeCurrentFragment(this);
    }
    //для удаления фрагмента
    public boolean removeFragment(BaseFragment fragment){
        return mMyFragmentManager.removeFragment(this, fragment);
    }

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }
}
