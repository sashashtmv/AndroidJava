package com.sashashtmv.vkapp.common.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import com.sashashtmv.vkapp.ui.activity.BaseActivity;
import com.sashashtmv.vkapp.ui.fragment.BaseFragment;

import java.util.Stack;

public class MyFragmentManager {
    //на экране должен быть всегда один фрагмент, создаем константу, чтоб его случайно не удалить
    private static final int EMPTY_FRAGMENT_STACK_SIZE = 1;
    //переменные для стека и текущего фрагмента
    private Stack<BaseFragment> mFragmentStack = new Stack<>();
    BaseFragment mCurrentFragment;

    //будет устанавливать корневой фрагмент. В этом фрагменте будет отображаться основное содержимое
    // окна, а в нем будут меняться заголовки тулбара и видимость кнопки fab
    public void setFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId){
        //проверяем, не существует ли данный фрагмент в стеке
        if(activity != null && !activity.isFinishing() && !isAlreadyContains(fragment)){
            FragmentTransaction transaction = createAddTransaction(activity, fragment, false);
            transaction.replace(containerId, fragment);
            commitAddTransaction(activity,fragment, transaction, false);
        }
    }

    //будет добавлять фрагменты поверх корневого, в них будут открываться окна из пунктов окна навигации
    public void addFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId){
        //проверяем, не существует ли данный фрагмент в стеке
        if(activity != null && !activity.isFinishing() && !isAlreadyContains(fragment)){
            FragmentTransaction transaction = createAddTransaction(activity, fragment, true);
            transaction.add(containerId, fragment);
            commitAddTransaction(activity,fragment, transaction, true);
        }
    }
    //метод удаления текущего фрагмента
    public boolean removeCurrentFragment(BaseActivity activity){
        return removeFragment(activity, mCurrentFragment);
    }

    //метод для удаления фрагмента
    public boolean removeFragment(BaseActivity activity, BaseFragment fragment){
        //чтоб случайно не удалили корневой фрагмент
        boolean canRemove = fragment != null && mFragmentStack.size() > EMPTY_FRAGMENT_STACK_SIZE;

        if(canRemove){
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            //удаляем последний фрагмент из списка
            mFragmentStack.pop();
            mCurrentFragment = mFragmentStack.lastElement();

            transaction.remove(fragment);
            //комитим транзакцию
            commitTransaction(activity, transaction);
        }
        return canRemove;
    }

    //будет создавать  fragment transaction и в зависимости от значения параметра  addToBackStack будет
    // добавлять или не добавлять фрагмент в бекстек, посколько нам не все фрагменты будут нужны в бекстеке
    private FragmentTransaction createAddTransaction(BaseActivity activity, BaseFragment fragment,
                                                     boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        return fragmentTransaction;
    }

    //будет комитить транзакцию добавления фрагмента
    private void commitAddTransaction(BaseActivity activity, BaseFragment fragment, FragmentTransaction transaction, boolean addToBackStack) {
        if (transaction != null) {
            mCurrentFragment = fragment;
            //если фрагмент не добавляется в бекстек, очищаем бекстек
            if (!addToBackStack) {
                mFragmentStack = new Stack<>();
            }
            mFragmentStack.add(fragment);

            commitTransaction(activity, transaction);
        }
    }


    //будет комитить любые транзакции  в независимости от того, добавляются фрагменты или удаляются
    private void commitTransaction(BaseActivity activity, FragmentTransaction transaction) {
        transaction.commit();

        activity.fragmentOnScreen(mCurrentFragment);
    }

    //метод проверки, существует ли в стеке текущий фрагмент
    public boolean isAlreadyContains(BaseFragment baseFragment) {
        if (baseFragment == null) {
            return false;
        }
        return mCurrentFragment != null && mCurrentFragment.getClass().getName().equals(baseFragment.getClass().getName());
    }

    public Stack<BaseFragment> getFragmentStack() {
        return mFragmentStack;
    }
}