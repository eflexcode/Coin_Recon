package com.larrex.coinrecon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.model.Item;
import com.larrex.coinrecon.model.Trending;
import com.larrex.coinrecon.repository.TrendingRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TrendingViewModel extends AndroidViewModel {

    @Inject
    TrendingRepository repository;

    public MutableLiveData<Trending> resultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();

    @Inject
    public TrendingViewModel(@NonNull Application application) {
        super(application);

    }

    public void getTrendingCoins() {
        repository.getTrendingCoins();
    }

    public MutableLiveData<Trending> observeTrending() {

        resultMutableLiveData = repository.resultMutableLiveData;

        return resultMutableLiveData;
    }

    public MutableLiveData<String> observeError() {

        errorMutableLiveData = repository.errorMutableLiveData;

        return errorMutableLiveData;
    }

}
