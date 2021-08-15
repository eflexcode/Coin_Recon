package com.larrex.coinrecon.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.Market;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRepository {

    Context context;

    public MutableLiveData<List<Exchange>> resultMutableLiveData = new MutableLiveData<List<Exchange>>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();

    @Named("exchange")
    @Inject
    ApiClients apiClientsExchangeData;


    @Inject
    public ExchangeRepository(@ApplicationContext Context context) {
        this.context = context;
    }

    public void getExchangeData(int perPage,int page) {


        apiClientsExchangeData.getExchangeData(perPage,page).enqueue(new Callback<List<Exchange>>() {
            @Override
            public void onResponse(Call<List<Exchange>> call, Response<List<Exchange>> response) {
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

                    List<Exchange> exchanges = response.body();

                    resultMutableLiveData.setValue(exchanges);
                } else {
                    String error = "Request OK but noting was returned";

                    errorMutableLiveData.setValue(error);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Exchange>> call, Throwable t) {
                String error = t.getMessage() + "\n Drag down to refresh";

                errorMutableLiveData.setValue(error);
            }
        });

    }


}
