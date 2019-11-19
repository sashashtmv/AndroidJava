package com.sashashtmv.vkapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

public abstract class BaseFragment extends MvpAppCompatFragment {

    //аннотация предполагает, что метод будет возвращать ссылку на этот лейаут
    @LayoutRes
    protected abstract int getMainContentLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getMainContentLayout(), container, false);
    }

    //для отображения заголовка тулбара
    public  String createToolbarTitle(Context context){
        return context.getString(onCreateToolbarTitle());
    }

    //будет запрашивать заголовок тулбара у дочерних фрагментов
    @StringRes
    public abstract int onCreateToolbarTitle();
}
