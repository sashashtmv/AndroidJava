package com.elegion.test.behancer.ui.profile;

import android.view.View;

import com.elegion.test.behancer.BuildConfig;
import com.elegion.test.behancer.common.BasePresenter;
import com.elegion.test.behancer.data.Storage;
import com.elegion.test.behancer.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.elegion.test.behancer.ui.profile.ProfileFragment.PROFILE_KEY;

public class ProfilePresenter extends BasePresenter {

    private ProfileView mView;
    private Storage mStorage;

    public ProfilePresenter(ProfileView view, Storage storage) {
        mView = view;
        mStorage = storage;
    }

    private void getProfile() {
        mCompositeDisposable.add(ApiUtils.getApiService().getUserInfo(getArguments().getString(PROFILE_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ?
                                mStorage.getUser(mUsername) :
                                null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefreshOwner.setRefreshState(true))
                .doFinally(() -> mRefreshOwner.setRefreshState(false))
                .subscribe(
                        response -> {
                            mErrorView.setVisibility(View.GONE);
                            mProfileView.setVisibility(View.VISIBLE);
                            bind(response.getUser());
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mProfileView.setVisibility(View.GONE);
                        }));
    }
}
