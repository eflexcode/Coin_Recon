package com.larrex.coinrecon.api;

import com.larrex.coinrecon.model.Market;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiClients {

    @GET("markets")
    Call<List<Market>> getLoginMarketData(@QueryMap Map<String, Object> marketInfoParameter);

    @GET("coins/{coinname}/ohlc")
    Call<List<List<Float>>> getChartData(@Path("coinname") String coinname, @QueryMap Map<String, Object> chartDataParameter);

}
