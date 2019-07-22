package com.sashashtmv.shoppinglist;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sashashtmv.shoppinglist.adapter.TabAdapter;
import com.sashashtmv.shoppinglist.dialog.AddingPurchaseDialogFragment;
import com.sashashtmv.shoppinglist.fragment.PurchasedPurchasesFragment;
import com.sashashtmv.shoppinglist.fragment.ShoppingListFragment;
import com.sashashtmv.shoppinglist.model.ModelPurchase;

public class MainActivity extends AppCompatActivity implements AddingPurchaseDialogFragment.AddingPurchaseListener, ShoppingListFragment.ShoppingListFragmentCallback {

    private FragmentManager mFragmentManager;
    PurchasedPurchasesFragment mPurchasedPurchasesFragment;
    public ShoppingListFragment mShoppingListFragment;

    private TabAdapter mTabAdapter;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getFragmentManager();

        setUI();
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null){
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // создаем вкладки методом addTab
        tabLayout.addTab(tabLayout.newTab().setText(R.string.need));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.purchased));

        final ViewPager viewPager = findViewById(R.id.pager);
        mTabAdapter = new TabAdapter(mFragmentManager, 2);

        mPurchasedPurchasesFragment = (PurchasedPurchasesFragment) mTabAdapter.getItem(TabAdapter.PURSHESED_PURSHESES_FRAGMENT_POSITION);
        mShoppingListFragment = (ShoppingListFragment) mTabAdapter.getItem(TabAdapter.SHOPPING_LIST_FRAGMENT_POSITION) ;

        viewPager.setAdapter(mTabAdapter);
        // установим слушателя на событие смены вкладок
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //добавим слушатель объекту tabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //определяет, что tab выбран
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //передаем view pager-у необходимый для отображения фрагмент
                viewPager.setCurrentItem(tab.getPosition());
            }

            //определяет, что tab более не выбран
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //определяет, что выбран ранее выбранный tab
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //вешаем слушателя на fab
        fab = findViewById(R.id.float_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingPurchaseDialogFragment = new AddingPurchaseDialogFragment();
                addingPurchaseDialogFragment.show(mFragmentManager, "AddingPurchaseDialogFragment");
            }
        });
    }

    @Override
    public void PurchaseAdd(ModelPurchase newPurchase) {
        mShoppingListFragment.addPurchase(newPurchase);
    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }

    @Override
    public void onPurchasedPurchases(ModelPurchase modelPurchase) {
        
    }
}
