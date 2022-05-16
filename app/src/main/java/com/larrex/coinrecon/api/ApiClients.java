package com.larrex.coinrecon.api;

import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.Item;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.model.News;
import com.larrex.coinrecon.model.Trending;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiClients {

    @GET("markets")
    Call<List<Market>> getMarketData(@QueryMap Map<String, Object> marketInfoParameter);

    @GET("coins/{coinname}/ohlc")
    Call<List<List<Float>>> getChartData(@Path("coinname") String coinname, @QueryMap Map<String, Object> chartDataParameter);

    @GET("exchanges")
    Call<List<Exchange>> getExchangeData(@Query("per_page") int perPage,@Query("page") int page);

    @GET("everything")
    Call<News> searchNews(@QueryMap Map<String,String> searchMap);

    @GET("trending")
    Call<Trending> getTrending();

}
