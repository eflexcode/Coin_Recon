package com.larrex.coinrecon.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.News;

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

public class NewsRepository {

    Context context;

    public final String ApiKey = "2f3a84ce7aa542d09c74f7e58cc2375c";

    public MutableLiveData<News> resultMutableLiveData = new MutableLiveData<News>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();

    @Named("news")
    @Inject
    ApiClients apiClientsNewsData;

    @Inject
    public NewsRepository(@ApplicationContext Context context) {
        this.context = context;
    }

    public void getNewsData() {

        Map<String, String> map = new HashMap<>();

        map.put("q", "cryptocurrency");
        map.put("sortBy", "publishedAt");
        map.put("apiKey", ApiKey);

        apiClientsNewsData.searchNews(map).enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
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

                    News exchanges = response.body();

                    resultMutableLiveData.setValue(exchanges);
                } else {
                    String error = "Request OK but noting was returned";

                    errorMutableLiveData.setValue(error);
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, Throwable t) {
                String error = t.getMessage() + "\n Drag down to refresh";

                errorMutableLiveData.setValue(error);
            }
        });

    }


}
