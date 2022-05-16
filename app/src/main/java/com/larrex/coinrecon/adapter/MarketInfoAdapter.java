package com.larrex.coinrecon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.databinding.MarketInfoItemLayoutBinding;
import com.larrex.coinrecon.fragment.CoinMarketDetailsFragment;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.LoginViewModel;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarketInfoAdapter extends RecyclerView.Adapter<MarketInfoAdapter.MarketViewHolder> {

    Context context;
    FragmentManager fragmentManager;
    List<Market> markets = new ArrayList<>();

    public MarketInfoAdapter(Context context, FragmentManager fragmentManager) {

        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    static DiffUtil.ItemCallback<Market> marketItemCallback = new DiffUtil.ItemCallback<Market>() {
        @Override
        public boolean areItemsTheSame(@NonNull Market oldItem, @NonNull Market newItem) {
            return oldItem.getClass().equals(newItem.getClass());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Market oldItem, @NonNull Market newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
//
        MarketInfoItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.market_info_item_layout, parent, false);

        return new MarketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        Market market = markets.get(position);

        if (market != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CoinMarketDetailsFragment fragment = new CoinMarketDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coinData", market);

                    fragment.setArguments(bundle);
                    fragment.show(fragmentManager,"coindata");
                }
            });

//
//            ArrayList<Entry> candleEntries = new ArrayList<>();
//
//            Map<String, Object> map = new HashMap<>();
//            map.put("vs_currency", "usd");
//            map.put("days", 1);
//
//            String base_url = "https://api.coingecko.com/api/v3/";
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(base_url)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//
//            ApiClients apiClients = retrofit.create(ApiClients.class);
//
//            apiClients.getChartData(market.getName(), map).enqueue(new Callback<List<List<Float>>>() {
//                @Override
//                public void onResponse(Call<List<List<Float>>> call, Response<List<List<Float>>> response) {
//
//                    if (response.code() == 200 && response.body() != null) {
//
//
//                        for (List<Float> doubles : response.body()) {
//
//                            candleEntries.add(new CandleEntry(doubles.get(0), doubles.get(1), doubles.get(2), doubles.get(3), doubles.get(4)));
//
//                        }
//                        Description description = new Description();
//                        description.setEnabled(false);
//
//                        holder.binding.chart.setDescription(description);
////
//
//                        LineDataSet candleDataSet = new LineDataSet(candleEntries, market.getName() + "");
//                        candleDataSet.setDrawCircles(false);
//                        candleDataSet.setCircleRadius(0f);
//                        candleDataSet.setCubicIntensity(20f);
//                        candleDataSet.setLineWidth(1f);
//                        candleDataSet.setDrawValues(false);
//                        candleDataSet.setValueTextSize(0f);
//                        candleDataSet.disableDashedLine();
//                        candleDataSet.setFillAlpha(110);
//                        candleDataSet.setHighLightColor(R.color.green);
//                        candleDataSet.setCubicIntensity(0);
//
//                        LineData cd = new LineData(candleDataSet);
//
//                        Legend legend = holder.binding.chart.getLegend();
//                        legend.setEnabled(false);
//
//                        holder.binding.chart.setData(cd);
//                        holder.binding.chart.getXAxis().setDrawGridLines(false);
//                        holder.binding.chart.setDrawBorders(false);
//                        holder.binding.chart.getAxisLeft().setDrawGridLines(false);
//                        holder.binding.chart.getXAxis().setDrawGridLines(false);
//                        holder.binding.chart.getAxisRight().setDrawGridLines(false);
//                        holder.binding.chart.setScaleEnabled(false);
//                        holder.binding.chart.invalidate();
//
//                        XAxis xAxis = holder.binding.chart.getXAxis();
//                        xAxis.setEnabled(false);
//
//                        YAxis yAxis = holder.binding.chart.getAxisLeft();
//                        yAxis.setGridLineWidth(0f);
//                        yAxis.setAxisLineColor(Color.parseColor("#FFFFFF"));
//                        yAxis.setEnabled(false);
//
//                        YAxis yAxis2 = holder.binding.chart.getAxisRight();
//                        yAxis2.setGridLineWidth(0f);
//                        yAxis2.setAxisLineColor(Color.parseColor("#FFFFFF"));
//                        yAxis2.setEnabled(false);
//
//                        xAxis.setValueFormatter(new ValueFormatter() {
//
//
//                            @Override
//                            public String getFormattedValue(float value) {
//
//
//                                return "";
//                            }
//                        });
//                    } else {
//                        Error error = new Error("Request OK but noting was returned");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<List<Float>>> call, Throwable t) {
//                    Error error = new Error(t.getMessage());
//
//                    ApiResult<Error> errorApiResult = new ApiResult<>(error);
//
////                Log.d(TAG, "onFailure: " + t.getMessage());
//                }
//            });
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.color.light_gray);
            requestOptions.placeholder(R.color.light_gray);

            Glide.with(context).load(market.getImage()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.coinLogo);

            DecimalFormat decimalFormatRank = new DecimalFormat("#");

            holder.binding.rank.setText(decimalFormatRank.format(market.getMarket_cap_rank()));

            holder.binding.coinName.setText(market.getName());

            holder.binding.coinSymbol.setText(market.getSymbol());
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(8);

            holder.binding.coinPrice.setText(formatter.format(market.getCurrent_price()));

            if (market.getPrice_change_percentage_24h() > 0) {
                holder.binding.coinUpOrDown.setImageResource(R.drawable.ic_up);
                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
                decimalFormat.setRoundingMode(RoundingMode.UP);
                String volume = decimalFormat.format(market.getPrice_change_percentage_24h());
                holder.binding.coinVolume.setText(volume + "%");
            } else {
                holder.binding.coinUpOrDown.setImageResource(R.drawable.ic_down);
                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
                decimalFormat.setRoundingMode(RoundingMode.UP);
                String volume = decimalFormat.format(market.getPrice_change_percentage_24h());
                holder.binding.coinVolume.setText(volume + "%");
            }

            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            decimalFormat.setRoundingMode(RoundingMode.UP);

            String mCap = formatter.format(market.getMarket_cap());
            String newMCap = mCap.substring(0, 6);

            String capCheck = String.valueOf(market.getMarket_cap());

            if (market.getMarket_cap() >= 1000000000) {
                holder.binding.coinMaketCap.setText("MCap " + newMCap + " Bn");
            } else if (market.getMarket_cap() < 1000000000 && market.getMarket_cap() >= 1000000) {
                holder.binding.coinMaketCap.setText("MCap " + newMCap + " Mn");
            } else {
                holder.binding.coinMaketCap.setText("MCap " + newMCap + " Th");
            }
        }
    }


    @Override
    public int getItemCount() {
        return markets.size();
    }

    public void setMarkets(List<Market> markets) {
        this.markets.addAll(markets);
//        notifyItemInserted(this.markets.size() + 1);

    }

    public void clearAllData() {
        markets.clear();
    }
//    @NonNull
//    @Override
//    public MarketInfoAdapter.MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//
//        MarketInfoItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.market_info_item_layout, parent, false);
//
//        return new MarketViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MarketInfoAdapter.MarketViewHolder holder, int position) {
//
//        Market market = getItem(position);
//
//        if (market != null) {
//
////
////            ArrayList<Entry> candleEntries = new ArrayList<>();
////
////            Map<String, Object> map = new HashMap<>();
////            map.put("vs_currency", "usd");
////            map.put("days", 1);
////
////            String base_url = "https://api.coingecko.com/api/v3/";
////
////            Retrofit retrofit = new Retrofit.Builder()
////                    .baseUrl(base_url)
////                    .addConverterFactory(GsonConverterFactory.create())
////                    .build();
////
////
////            ApiClients apiClients = retrofit.create(ApiClients.class);
////
////            apiClients.getChartData(market.getName(), map).enqueue(new Callback<List<List<Float>>>() {
////                @Override
////                public void onResponse(Call<List<List<Float>>> call, Response<List<List<Float>>> response) {
////
////                    if (response.code() == 200 && response.body() != null) {
////
////
////                        for (List<Float> doubles : response.body()) {
////
////                            candleEntries.add(new CandleEntry(doubles.get(0), doubles.get(1), doubles.get(2), doubles.get(3), doubles.get(4)));
////
////                        }
////                        Description description = new Description();
////                        description.setEnabled(false);
////
////                        holder.binding.chart.setDescription(description);
//////
////
////                        LineDataSet candleDataSet = new LineDataSet(candleEntries, market.getName() + "");
////                        candleDataSet.setDrawCircles(false);
////                        candleDataSet.setCircleRadius(0f);
////                        candleDataSet.setCubicIntensity(20f);
////                        candleDataSet.setLineWidth(1f);
////                        candleDataSet.setDrawValues(false);
////                        candleDataSet.setValueTextSize(0f);
////                        candleDataSet.disableDashedLine();
////                        candleDataSet.setFillAlpha(110);
////                        candleDataSet.setHighLightColor(R.color.green);
////                        candleDataSet.setCubicIntensity(0);
////
////                        LineData cd = new LineData(candleDataSet);
////
////                        Legend legend = holder.binding.chart.getLegend();
////                        legend.setEnabled(false);
////
////                        holder.binding.chart.setData(cd);
////                        holder.binding.chart.getXAxis().setDrawGridLines(false);
////                        holder.binding.chart.setDrawBorders(false);
////                        holder.binding.chart.getAxisLeft().setDrawGridLines(false);
////                        holder.binding.chart.getXAxis().setDrawGridLines(false);
////                        holder.binding.chart.getAxisRight().setDrawGridLines(false);
////                        holder.binding.chart.setScaleEnabled(false);
////                        holder.binding.chart.invalidate();
////
////                        XAxis xAxis = holder.binding.chart.getXAxis();
////                        xAxis.setEnabled(false);
////
////                        YAxis yAxis = holder.binding.chart.getAxisLeft();
////                        yAxis.setGridLineWidth(0f);
////                        yAxis.setAxisLineColor(Color.parseColor("#FFFFFF"));
////                        yAxis.setEnabled(false);
////
////                        YAxis yAxis2 = holder.binding.chart.getAxisRight();
////                        yAxis2.setGridLineWidth(0f);
////                        yAxis2.setAxisLineColor(Color.parseColor("#FFFFFF"));
////                        yAxis2.setEnabled(false);
////
////                        xAxis.setValueFormatter(new ValueFormatter() {
////
////
////                            @Override
////                            public String getFormattedValue(float value) {
////
////
////                                return "";
////                            }
////                        });
////                    } else {
////                        Error error = new Error("Request OK but noting was returned");
////                    }
////                }
////
////                @Override
////                public void onFailure(Call<List<List<Float>>> call, Throwable t) {
////                    Error error = new Error(t.getMessage());
////
////                    ApiResult<Error> errorApiResult = new ApiResult<>(error);
////
//////                Log.d(TAG, "onFailure: " + t.getMessage());
////                }
////            });
//
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.error(R.color.light_gray);
//            requestOptions.placeholder(R.color.light_gray);
//
//            Glide.with(context).load(market.getImage()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.coinLogo);
//
//            DecimalFormat decimalFormatRank = new DecimalFormat("#");
//
//            holder.binding.rank.setText(decimalFormatRank.format(market.getMarket_cap_rank()));
//
//            holder.binding.coinName.setText(market.getName());
//
//            holder.binding.coinSymbol.setText(market.getSymbol());
//            NumberFormat formatter = NumberFormat.getCurrencyInstance();
//            formatter.setMaximumFractionDigits(8);
//
//            holder.binding.coinPrice.setText(formatter.format(market.getCurrent_price()));
//
//            if (market.getPrice_change_percentage_24h() > 0) {
//                holder.binding.coinUpOrDown.setImageResource(R.drawable.ic_up);
//                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
//                decimalFormat.setRoundingMode(RoundingMode.UP);
//                String volume = decimalFormat.format(market.getPrice_change_percentage_24h());
//                holder.binding.coinVolume.setText(volume + "%");
//            } else {
//                holder.binding.coinUpOrDown.setImageResource(R.drawable.ic_down);
//                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
//                decimalFormat.setRoundingMode(RoundingMode.UP);
//                String volume = decimalFormat.format(market.getPrice_change_percentage_24h());
//                holder.binding.coinVolume.setText(volume + "%");
//            }
//
//            DecimalFormat decimalFormat = new DecimalFormat("###,###");
//            decimalFormat.setRoundingMode(RoundingMode.UP);
//
//            String mCap = formatter.format(market.getMarket_cap());
//            String newMCap = mCap.substring(0, 6);
//
//            String capCheck = String.valueOf(market.getMarket_cap());
//
//            if (market.getMarket_cap() >= 1000000000) {
//                holder.binding.coinMaketCap.setText("MCap " + newMCap + " Bn");
//            } else {
//                holder.binding.coinMaketCap.setText("MCap " + newMCap + " Mn");
//            }
//
//        }


    class MarketViewHolder extends RecyclerView.ViewHolder {

        MarketInfoItemLayoutBinding binding;

        public MarketViewHolder(MarketInfoItemLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}