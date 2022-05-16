package com.larrex.coinrecon.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Item;
import com.larrex.coinrecon.model.Trending;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingRepository {

    Context context;

    private static final String TAG = "TrendingRepository";

    public MutableLiveData<Trending> resultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();

    @Named("trending")
    @Inject
    ApiClients apiClientsTrending;

    @Inject
    public TrendingRepository(@ApplicationContext Context context) {
        this.context = context;
    }

    public void getTrendingCoins(){

        apiClientsTrending.getTrending().enqueue(new Callback<Trending>() {
            @Override
            public void onResponse(@NonNull Call<Trending> call, @NonNull Response<Trending> response) {

                    if (response.code() == 404) {
                        String error = "Request Not Found";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 400) {
                        String error = "Bad Request";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 403) {
                        String error = "Request Forbidden";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 408) {
                        String error = "Request Timeout";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 409) {
                        String error = "Conflict";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 429) {
                        String error = "Too Many Requests";

                        errorMutableLiveData.setValue(error);
                    } else if (response.code() == 500) {
                        String error = "Internal Server Error";

                        errorMutableLiveData.setValue(error);
                    }

                    if (response.code() == 200 && response.body() != null) {

                        Trending itemList = response.body();

//                        Log.d(TAG, "onResponse: "+response.body().getTrendingCoinsList().get(0).getCoin().name);

                        resultMutableLiveData.setValue(itemList);
                    } else {
                        String error = "Request OK but noting was returned";

                        errorMutableLiveData.setValue(error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Trending> call, @NonNull Throwable t) {
                Error error = new Error("Something is not right \nDrag down to refresh");

                ApiResult<Error> errorApiResult = new ApiResult<>(error);

                errorMutableLiveData.setValue(error.getErrorMessage());
            }
        });

    }

}
