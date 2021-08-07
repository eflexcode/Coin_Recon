package com.larrex.coinrecon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.repository.MarketInfoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MarketInfoViewModel extends AndroidViewModel {

    public MutableLiveData<ApiResult<List<Market>>> resultMutableLiveData = new MutableLiveData<ApiResult<List<Market>>>();
    public MutableLiveData<ApiResult<Error>> errorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResult<Error>> errorChartMutableLiveData = new MutableLiveData<>();


    @Inject
    public MarketInfoViewModel(@NonNull Application application) {
        super(application);
    }

    @Inject
    MarketInfoRepository repository;

    public void getMarketData(String currency, int perPage, int page) {
        repository.getMarketData(currency, perPage, page);
    }

    public LiveData<ApiResult<List<Market>>> getResultMutableLiveData() {

        resultMutableLiveData = repository.resultMutableLiveData;

        return resultMutableLiveData;
    }

    public MutableLiveData<ApiResult<Error>> getErrorMutableLiveData() {

        errorMutableLiveData = repository.errorMutableLiveData;

        return errorMutableLiveData;
    }

    public MutableLiveData<ApiResult<Error>> getErrorChartMutableLiveData() {
        return errorChartMutableLiveData;
    }
}
