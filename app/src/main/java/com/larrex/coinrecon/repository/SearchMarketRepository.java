package com.larrex.coinrecon.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class SearchMarketRepository {

    Context context;

    private static final String TAG = "SearchMarketRepository";

    @Named("marketData")
    @Inject
    ApiClients apiClientsMarketData;

    @Named("chartData")
    @Inject
    ApiClients getChartDataApiClients;

    public MutableLiveData<ApiResult<Market>> resultMutableLiveData = new MutableLiveData<ApiResult<Market>>();
    public MutableLiveData<ApiResult<Error>> errorMutableLiveData = new MutableLiveData<>();

    @Inject
    public SearchMarketRepository(@ApplicationContext Context context) {
        this.context = context;
    }

    public void searchMarket(String currency, String id) {

//        Toast.makeText(context, "hhhhhhhhhhhhhhhhhhhhhhh", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "searchMarket: hhhhhhhhhhhhhhhhhhhhhhh");

        Map<String, Object> map = new HashMap<>();
        map.put("vs_currency", currency);
        map.put("ids", id);
        map.put("order", "market_cap_desc");
        map.put("per_page", 100);
        map.put("page", 1);
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

                    for (Market market : response.body()) {
                        ApiResult<Market> marketApiResult = new ApiResult<>(market);
                        resultMutableLiveData.setValue(marketApiResult);
                        Log.d(TAG, "searchMarket: "+market.getName());
                    }

                    Log.d(TAG, "searchMarket: lllllllllllllllllllllllll");



                } else {
                    Error error = new Error("Request OK but noting was returned");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                }
            }

            @Override
            public void onFailure(Call<List<Market>> call, Throwable t) {
                Error error = new Error(t.getMessage());

                ApiResult<Error> errorApiResult = new ApiResult<>(error);

                errorMutableLiveData.setValue(errorApiResult);

            }
        });


    }

}
