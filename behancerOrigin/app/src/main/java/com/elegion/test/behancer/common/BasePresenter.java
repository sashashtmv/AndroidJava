package com.elegion.test.behancer.common;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter {

    //переменная нужна, чтобы мы могли отписываться абсолютно от всех подписок, которые у нас есть
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void disposeAll(){
        mCompositeDisposable.clear();
    }
}
