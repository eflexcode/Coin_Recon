package com.larrex.coinrecon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.repository.ExchangeRepository;
import com.larrex.coinrecon.repository.MarketInfoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExchangeViewModel extends AndroidViewModel {

    public MutableLiveData<List<Exchange>> resultMutableLiveData = new MutableLiveData<List<Exchange>>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();


    @Inject
    public ExchangeViewModel(@NonNull Application application) {
        super(application);
    }

    @Inject
    ExchangeRepository repository;

    public void getExchangeList(int perPage,int page) {
        repository.getExchangeData(perPage,page);
    }

    public LiveData<List<Exchange>> getResultMutableLiveData() {

        resultMutableLiveData = repository.resultMutableLiveData;

        return resultMutableLiveData;
    }

    public MutableLiveData<String> getErrorMutableLiveData() {
        errorMutableLiveData = repository.errorMutableLiveData;
        return errorMutableLiveData;
    }
}
