package com.sashashtmv.vkapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sashashtmv.vkapp.CurrentUser;
import com.sashashtmv.vkapp.mvp.view.MainView;

//аннотация для привязки ViewState к презентеру. Сущность ViewState хранит в себе список команд, которые были переданы из презентера во вью
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    //проверяется условие, если пользователь не авторизован
    public  void checkAuth(){
        if(!CurrentUser.isAuthorised()){
            //вызывается методе интерфейса mainView для старта авторизации
            getViewState().startSignIn();
        }
        else {//иначе вызывается метод для идентификации пользователя
            getViewState().signedId();
        }
    }
}
