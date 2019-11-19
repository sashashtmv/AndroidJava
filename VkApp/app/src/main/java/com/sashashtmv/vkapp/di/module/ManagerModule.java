package com.sashashtmv.vkapp.di.module;

import com.sashashtmv.vkapp.common.manager.MyFragmentManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//будет использоваться для предоставления менеджеров

//аннотация необходима дагеру для пометки этого класса и формирования графа зависимостей.
// Это аннотация для классов, у которых определен набор методов с аннотацией provides
//Этой аннотацией обозначается некий поставщик зависимостей
@Module
public class ManagerModule {

    //Определяет, что объект будет в одном экземпляре
    @Singleton
    //метод предоставляет нужный нам объект для внедрения зависимости которую мы сможем заинжектить в нужном месте
    @Provides
    MyFragmentManager provideMyFragmentManager(){
        return  new MyFragmentManager();
    }
}
