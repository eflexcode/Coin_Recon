package com.larrex.coinrecon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.TrendingLayoutBinding;
import com.larrex.coinrecon.model.Coin;
import com.larrex.coinrecon.model.Item;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    Context context;
    List<Coin> coins;

    public TrendingAdapter(Context context, List<Coin> coins) {
        this.context = context;
        this.coins = coins;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        TrendingLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.trending_layout, parent, false);

        return new TrendingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {
        Coin coin = coins.get(position);

        if (coin != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.color.light_gray);
            requestOptions.placeholder(R.color.light_gray);

            Glide.with(context).load(coin.getItem().getThumb()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.coinLogo);

            holder.binding.coinRank.setText("#" + coin.getItem().getMarket_cap_rank());

            holder.binding.coinName.setText(coin.getItem().getName());

            holder.binding.coinSymbol.setText(coin.getItem().getSymbol());

        }
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    class TrendingViewHolder extends RecyclerView.ViewHolder {

        TrendingLayoutBinding binding;

        public TrendingViewHolder(TrendingLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}