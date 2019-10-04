package com.sashashtmv.myquiz.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.constants.AppConstants;


public class SettingsFragment extends PreferenceFragment implements
		BillingProcessor.IBillingHandler {
	
	BillingProcessor bp;
	Preference preferencepurchase;
	Preference preferencesubscribe;

	AlertDialog dialog;
	

	//создадим секцию экрана настроек на основе settings_preference.xml. Назначаем каждой секции слушателя и
	// прописываем действия, которые будут выполняться по нажатию на секцию. Проверяем лицензионный ключ и
	// совершенную покупку путем создания экземпляра класса BillingProcessor, который получает список покупок пользователя
	// с сервера гугл методом loadOwnedPurchasesFromGoogle. При нажатии секции выполняется запрос на покупку с помощью метода purchase биллинг процессора.
	//Если продукт уже приобретен, то устанавливается изображение флажка в секции экрана настроек. Ниже прописаны диалоговые экраны, которые показываются при
	// различных событиях покупки и восстановления предыдущих покупок, а также при попытке открытия контента, заблокированного в бесплатной версии приложения.
	//Далее реализованы методы обратного вызова интерфейса BillingProcessor.IBillingHandler, которые срабатывают при определенных событиях, связанных с покупками
	// в приложении.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_preference);

		String license = getResources().getString(R.string.google_play_license);
		bp = new BillingProcessor(getActivity(), license, this);
		bp.loadOwnedPurchasesFromGoogle();



		preferencesubscribe = findPreference("subscribe");

		if (null != license && !license.equals("")){

			preferencesubscribe
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						bp.subscribe(getActivity(), SUBSCRIPTION_ID());
						return true;
					}
				});

			if (getIsSubscribe(getActivity())){
				preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
			}
		}

		preferencepurchase = findPreference("purchase");

		if (null != license && !license.equals("")){


			preferencepurchase
					.setOnPreferenceClickListener(new OnPreferenceClickListener() {
						@Override
						public boolean onPreferenceClick(Preference preference) {
							bp.purchase(getActivity(), PRODUCT_ID());
							return true;
						}
					});

			if (getIsPurchased(getActivity())){
				preferencepurchase.setIcon(R.drawable.ic_action_action_done);
				preferencesubscribe.setEnabled(false);
				preferencesubscribe.setSelectable(false);
			}
		}



	}

	//вызывается, когда биллинг процессор инициализирован и готов к покупке, тело метода здесь пустое, мы ничего не делаем после этого события
	@Override
	public void onBillingInitialized() {

	}

	//вызывается после успешного приобретения продукта. Здесь мы вызываем метод setIsPurchased, в котором создаем параметр состояния покупки
	// и сохраняем в него логическую переменную purchased.
	@Override
	public void onProductPurchased(String productId, TransactionDetails details) {
		if (productId.equals(PRODUCT_ID())){
			setIsPurchased(true, getActivity());
			preferencepurchase.setIcon(R.drawable.ic_action_action_done);
			Toast.makeText(getActivity(), getResources().getString(R.string.settings_purchase_success), Toast.LENGTH_LONG).show();
			Log.v("TAG", "Purchase purchased");
		}

		if (productId.equals(SUBSCRIPTION_ID())){
			setIsSubscribe(true, getActivity());
			preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
			Toast.makeText(getActivity(), getResources().getString(R.string.settings_subscribe_success), Toast.LENGTH_LONG).show();
			Log.v("TAG", "Subscribe purchased");
		} else {
			setIsSubscribe(false, getActivity());
			preferencesubscribe.getIcon().setVisible(false, true);
		}
		Log.v("TAG", "Purchase or subscribe purchased");
	}

	//вызывается в случае ошибки в процессе покупки, или когда пользователь отменил покупку. Здесь показываем уведомление об ошибке
	@Override
	public void onBillingError(int errorCode, Throwable error) {
		Toast.makeText(getActivity(), getResources().getString(R.string.settings_purchase_fail), Toast.LENGTH_LONG).show();
		Log.v("TAG", "Error");
	}

	//выполняется в случае успешной загрузки списка покупок пользователя из Google Play. Здесь мы проверяем по идентификатору продукта
	// факт его покупки и в случае совпадения показываем пользователю оповещение о восстановленной покупке. Также сохраняем
	// значение успешной покупки в параметре настроек и устанавливаем картинку-флажок.
	@Override
	public void onPurchaseHistoryRestored() {
		if (bp.isPurchased(PRODUCT_ID())) {
            	setIsPurchased(true, getActivity());
            	Log.v("TAG", "Purchase actually restored");
            	preferencepurchase.setIcon(R.drawable.ic_action_action_done);
            	if (dialog != null) dialog.cancel();
            	Toast.makeText(getActivity(), getResources().getString(R.string.settings_restore_purchase_success), Toast.LENGTH_LONG).show();
            }

            if (bp.isSubscribed(SUBSCRIPTION_ID())) {
			setIsSubscribe(true, getActivity());
			Log.v("TAG", "Subscribe actually restored");
			preferencesubscribe.setIcon(R.drawable.ic_action_action_done);
			if (dialog != null) dialog.cancel();
		} else {
				setIsSubscribe(false, getActivity());
				preferencesubscribe.getIcon().setVisible(false, true);
			}
		Log.v("TAG", "Purchase and subscription restored called");
	}


	//Методы setIsPurchased и getIsPurchased сохраняют и извлекают сохраненную настройку о совершенной покупке в базе настроек.
	public void setIsPurchased(boolean purchased, Context c){
    	SharedPreferences prefs = PreferenceManager
        	    .getDefaultSharedPreferences(c);
    	
    	SharedPreferences.Editor editor= prefs.edit();
    	
    	editor.putBoolean(AppConstants.PRODUCT_ID_BOUGHT, purchased);
 	    editor.apply();
	}

	public void setIsSubscribe(boolean purchased, Context c){
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(c);

		SharedPreferences.Editor editor= prefs.edit();

		editor.putBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, purchased);
		editor.apply();
	}
	
	public static boolean getIsPurchased(Context c){
		SharedPreferences prefs = PreferenceManager
        	    .getDefaultSharedPreferences(c);

		return prefs.getBoolean(AppConstants.PRODUCT_ID_BOUGHT, false);
	}

	public static boolean getIsSubscribe(Context c){
		SharedPreferences prefs = PreferenceManager
        	    .getDefaultSharedPreferences(c);

        return prefs.getBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, false);
	}
	
	private String PRODUCT_ID(){
		return getResources().getString(R.string.product_id);
	}

	private String SUBSCRIPTION_ID(){
		return getResources().getString(R.string.subscription_id);
	}

	//вызываем метод биллинг-процессора handleActivityResult, который инициирует запрос на покупку и дергает методы обратного вызова в зависимости от результата.
	public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        bp.handleActivityResult(requestCode, resultCode, intent);
    }
	
	//освобождаем ресурсы биллинг процессора.
	@Override
	public void onDestroy() {
	   if (bp != null) 
	        bp.release();

	    super.onDestroy();
	}
}
