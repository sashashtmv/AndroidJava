package com.sashashtmv.vkapp.di.module;

//будет отвечать за предоставление контекста

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//аннотация необходима дагеру для пометки этого класса и формирования графа зависимостей.
// Это аннотация для классов, у которых определен набор методов с аннотацией provides
//Этой аннотацией обозначается некий поставщик зависимостей
@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    //будет возвращать экземпляр Application
    //Определяет, что объект будет в одном экземпляре
    @Singleton
    //метод предоставляет нужный нам объект для внедрения зависимости которую мы сможем заинжектить в нужном месте
    @Provides
    public Context provideContext(){
        return mApplication;
    }
}
