package com.sashashtmv.shoppinglist.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sashashtmv.shoppinglist.MainActivity;
import com.sashashtmv.shoppinglist.R;
import com.sashashtmv.shoppinglist.adapter.PurchaseAdapter;
import com.sashashtmv.shoppinglist.adapter.ShoppingListAdapter;
import com.sashashtmv.shoppinglist.model.ModelPurchase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public abstract class PurchaseFragment extends Fragment {

    private static final int REQUEST_CODE_PHOTO = 1;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected PurchaseAdapter mAdapter;
    protected FloatingActionButton mFloatingActionButton;
    public Bitmap bitmap;
    ModelPurchase mModelPurchase;
    int mPosition;
    RecyclerView.ViewHolder mHolder;


    public MainActivity mMainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mMainActivity = (MainActivity) getActivity();
        }
        //addTaskFromDB();
    }


    public void addPurchase(ModelPurchase newPurchase) {
        mAdapter.addItem(newPurchase);
        mModelPurchase = newPurchase;
    }

    public void choosePurchase(){

    }

    public void addImage(int position, final RecyclerView.ViewHolder holder) {
        mPosition = position;
        mHolder = holder;
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
        chooser.putExtra(Intent.EXTRA_TITLE, "title");

        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooser, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        ImageView imageView = mHolder.itemView.findViewById(R.id.iv_image);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (imageReturnedIntent.getData() != null) {

                try {
//                    if (bitmap != null) {
//                        bitmap.recycle();
//                    }
                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imageReturnedIntent.getData());
                    bitmap = BitmapFactory.decodeStream(imageStream);

                    mModelPurchase.setBitmap(bitmap);
                    imageView.setImageBitmap(bitmap);

                    imageStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                mModelPurchase.setBitmap(bitmap);
                imageView.setImageBitmap(bitmap);

            }
        }
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    }

}
