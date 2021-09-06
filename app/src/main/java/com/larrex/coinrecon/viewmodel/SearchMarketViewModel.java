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
import com.larrex.coinrecon.repository.SearchMarketRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchMarketViewModel extends AndroidViewModel {

    public MutableLiveData<ApiResult<Market>> resultMutableLiveData = new MutableLiveData<ApiResult<Market>>();
    public MutableLiveData<ApiResult<Error>> errorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResult<Error>> errorChartMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> isAdapterLoading = new MutableLiveData<>();

    @Inject
    public SearchMarketViewModel(@NonNull Application application) {
        super(application);
    }

    @Inject
    SearchMarketRepository repository;

    public void getMarketData(String currency, String id) {
        repository.searchMarket(currency, id);
    }

    public LiveData<ApiResult<Market>> getResultMutableLiveData() {

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

    public MutableLiveData<Boolean> getIsAdapterLoading() {
        return isAdapterLoading;
    }
}
