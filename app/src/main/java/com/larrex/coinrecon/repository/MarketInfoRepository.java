package com.larrex.coinrecon.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketInfoRepository {

    Context context;

    @Named("marketData")
    @Inject
    ApiClients apiClientsMarketData;

    @Named("chartData")
    @Inject
    ApiClients getChartDataApiClients;

    public MutableLiveData<ApiResult<List<Market>>> resultMutableLiveData = new MutableLiveData<ApiResult<List<Market>>>();
    public MutableLiveData<ApiResult<Error>> errorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResult<Error>> errorChartMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Market> marketMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<List<Float>>> chartDataMutableLiveData = new MutableLiveData<>();

    @Inject
    public MarketInfoRepository(@ApplicationContext Context context) {
        this.context = context;
    }


    public void getMarketData(String currency, int perPage, int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("vs_currency", currency);
        map.put("order", "market_cap_desc");
        map.put("per_page", perPage);
        map.put("page", page);
        map.put("sparkline", false);
        map.put("price_change_percentage", "24h");

        apiClientsMarketData.getMarketData(map).enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(Call<List<Market>> call, Response<List<Market>> response) {
                if (response.code() == 404) {
                    Error error = new Error("Request Not Found");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 400) {
                    Error error = new Error("Bad Request");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 403) {
                    Error error = new Error("Request Forbidden");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 408) {
                    Error error = new Error("Request Timeout");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 409) {
                    Error error = new Error("Conflict");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 429) {
                    Error error = new Error("Too Many Requests");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 500) {
                    Error error = new Error("Internal Server Error");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                }

                if (response.code() == 200 && response.body() != null) {

                    ApiResult<List<Market>> marketApiResult = new ApiResult<>(response.body());

                    resultMutableLiveData.setValue(marketApiResult);
                } else {
                    Error error = new Error("Request OK but noting was returned");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                Error error = new Error(t.getMessage()+"\n Drag down to refresh");

                ApiResult<Error> errorApiResult = new ApiResult<>(error);

                errorMutableLiveData.setValue(errorApiResult);
                ;

//                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}
