package com.sashashtmv.shoppinglist.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.shoppinglist.R;
import com.sashashtmv.shoppinglist.fragment.ShoppingListFragment;
import com.sashashtmv.shoppinglist.model.ModelPurchase;

public class ShoppingListAdapter extends PurchaseAdapter {
    ShoppingListFragment mShoppingListFragment;


    public ShoppingListAdapter(ShoppingListFragment shoppingListFragment) {
        super(shoppingListFragment);
        mShoppingListFragment = shoppingListFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.purchase_model,parent,false);
                ImageView image = v.findViewById(R.id.iv_image);
                TextView title = v.findViewById(R.id.tv_purchase_title);
                CheckBox status = v.findViewById(R.id.cb_check_box);

                return new PurchaseViewHolder(v,image, title, status);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        final ModelPurchase item = mItemList.get(position);


        holder.itemView.setEnabled(true);
        //активируем возможность нажатия Таска

        final PurchaseViewHolder purchaseViewHolder = (PurchaseViewHolder) holder;

        final View itemView = purchaseViewHolder.itemView;
        final Resources resources = itemView.getResources();
        final int mPosition = position;
        purchaseViewHolder.image.setImageResource(R.drawable.add_photo);
        purchaseViewHolder.title.setText(item.getTitle());
        purchaseViewHolder.mCheckBox.setVisibility(View.GONE);

        //поставим для элемента списка признак видимости
        itemView.setVisibility(View.VISIBLE);

        purchaseViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPurchaseFragment().addImage(mPosition, holder);

                //установим основной цвет текста для заголовка задачи
                purchaseViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));


                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //handler нужен для того, чтобы сработала рипл-анимация, чтобы она успела сработать до того, как вызовется диалог
                        Handler handler = new Handler();
                        //щрганизуем задержку
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               mShoppingListFragment.choosePurchase();

                            }
                        }, 1000);
                        return true;
                    }
                });
            }
        });
    }
}
