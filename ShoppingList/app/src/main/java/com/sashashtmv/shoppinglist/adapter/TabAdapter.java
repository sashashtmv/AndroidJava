package com.sashashtmv.shoppinglist.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sashashtmv.shoppinglist.fragment.PurchasedPurchasesFragment;
import com.sashashtmv.shoppinglist.fragment.ShoppingListFragment;


public class TabAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    //для получения экземпляра уже существующего фрагмента из тейбллейаут создадим две стат.константы
    public static final int SHOPPING_LIST_FRAGMENT_POSITION = 0;
    public static final int PURSHESED_PURSHESES_FRAGMENT_POSITION = 1;
    //для того, чтобы объекты не создавались при каждом вызове метода getItem, нужно инициализировать классы CurrentTaskFragment и DoneTaskFragment
    ShoppingListFragment mShoppingListFragment;
    PurchasedPurchasesFragment mPurchasedPurchasesFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        mShoppingListFragment = new ShoppingListFragment();
        mPurchasedPurchasesFragment = new PurchasedPurchasesFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mShoppingListFragment;
            case 1:
                return mPurchasedPurchasesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
