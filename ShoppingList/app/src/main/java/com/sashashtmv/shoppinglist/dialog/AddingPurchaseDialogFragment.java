package com.sashashtmv.shoppinglist.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.sashashtmv.shoppinglist.R;
import com.sashashtmv.shoppinglist.model.ModelPurchase;

public class AddingPurchaseDialogFragment extends DialogFragment implements OnClickListener {
    FragmentManager mFragmentManager;
    EditText title;
     ModelPurchase modelPurchase;


    private AddingPurchaseListener mAddingPurchaseListener;
    public interface AddingPurchaseListener {
        void PurchaseAdd(ModelPurchase newPurchase);

    }

    //для получения доступа к диалогу из активити, например, во время нажатия, нужно определить слушателя
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mAddingPurchaseListener = (AddingPurchaseListener) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement AddingPurchaseListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        getDialog().setTitle(R.string.add_purchase);
        title = view.findViewById(R.id.tx_title);
        view.findViewById(R.id.bt_ok).setOnClickListener(this);
        view.findViewById(R.id.bt_cancel).setOnClickListener(this);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        mFragmentManager = activity.getFragmentManager();
        modelPurchase = new ModelPurchase();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == view.findViewById(R.id.bt_ok)){
            modelPurchase.setStatus(ModelPurchase.STATUS_CURRENT);
            modelPurchase.setBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.add_photo));
            modelPurchase.setTitle(title.getText().toString());
            mAddingPurchaseListener.PurchaseAdd(modelPurchase);
//            mAddingPurchaseListener.PurchaseAdd(new ModelPurchase(title.getText().toString(), ModelPurchase.STATUS_CURRENT,
//                    BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.add_photo)));
            dismiss();
        }
        if(view == view.findViewById(R.id.bt_cancel)){

            dismiss();
        }
    }

}