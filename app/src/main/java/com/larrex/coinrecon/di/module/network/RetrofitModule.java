package com.larrex.coinrecon.di.module.network;

import com.google.firebase.auth.FirebaseAuth;
import com.larrex.coinrecon.api.ApiClients;

import org.jetbrains.annotations.NotNull;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@InstallIn(ViewModelComponent.class)
@Module
public class RetrofitModule {

    @Named("marketData")
    @Provides
    public static ApiClients getLoginMarket() {

        String base_url = "https://api.coingecko.com/api/v3/coins/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiClients.class);

    }


    @Named("chartData")
    @Provides
    public static ApiClients getChartData() {

        String base_url = "https://api.coingecko.com/api/v3/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiClients.class);

    }

    @Named("exchange")
    @Provides
    public static ApiClients getExchangeData() {

        String base_url = "https://api.coingecko.com/api/v3/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiClients.class);

    }

    @Named("news")
    @Provides
    public static ApiClients getNews() {
        final String Base_url = "https://newsapi.org/v2/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiClients.class);
    }

    @Named("trending")
    @Provides
    public static ApiClients getTrending() {
        final String Base_url = "https://api.coingecko.com/api/v3/search/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiClients.class);
    }

}
