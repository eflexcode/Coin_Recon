package com.larrex.coinrecon.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.ExchangeLayoutBinding;
import com.larrex.coinrecon.databinding.MarketInfoItemLayoutBinding;
import com.larrex.coinrecon.fragment.CoinMarketDetailsFragment;
import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.Market;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.MarketViewHolder> {

    Context context;
    FragmentManager fragmentManager;
    List<Exchange> exchanges = new ArrayList<>();

    public ExchangeAdapter(Context context, FragmentManager fragmentManager) {

        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
//
        ExchangeLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.exchange_layout, parent, false);

        return new MarketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        Exchange exchange = exchanges.get(position);

        if (exchange != null) {

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CoinMarketDetailsFragment fragment = new CoinMarketDetailsFragment();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("coinData", market);
//
//                    fragment.setArguments(bundle);
//                    fragment.show(fragmentManager,"coindata");
//                }
//            });

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.color.light_gray);
            requestOptions.placeholder(R.color.light_gray);

            Glide.with(holder.binding.exchangeLogo).load(exchange.getImage()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.exchangeLogo);

            DecimalFormat decimalFormatRank = new DecimalFormat("#");

            holder.binding.rank.setText(decimalFormatRank.format(exchange.getTrust_score_rank()));

            holder.binding.exchangeName.setText(exchange.getName());


                DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
                decimalFormat.setRoundingMode(RoundingMode.UP);
                String volume = decimalFormat.format(exchange.getTrade_volume_24h_btc_normalized());
                holder.binding.exchangeVol.setText("BTC "+volume);


        }
    }


    @Override
    public int getItemCount() {
        return exchanges.size();
    }

    public void setMarkets(List<Exchange> exchanges) {
        this.exchanges.addAll(exchanges);
//        notifyItemInserted(this.markets.size() + 1);

    }

    public void clearAllData() {
        exchanges.clear();
    }

    class MarketViewHolder extends RecyclerView.ViewHolder {

        ExchangeLayoutBinding  binding;

        public MarketViewHolder(ExchangeLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}