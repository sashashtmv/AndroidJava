package com.sashashtmv.vkapp.di.component;

import com.sashashtmv.vkapp.di.module.ApplicationModule;
import com.sashashtmv.vkapp.di.module.ManagerModule;
import com.sashashtmv.vkapp.di.module.RestModule;
import com.sashashtmv.vkapp.ui.activity.BaseActivity;
import com.sashashtmv.vkapp.ui.activity.MainActivity;
import com.sashashtmv.vkapp.ui.fragment.NewsFeedFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
//помечается интерфейс, который связывает модули непосредственно частями, которые запрашивают зависимости
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    //activities
    void inject(BaseActivity activity);
    void inject(MainActivity activity);

    //fragments
    void inject(NewsFeedFragment fragment);
}
