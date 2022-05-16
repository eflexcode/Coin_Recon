package com.larrex.coinrecon.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.TrendingAdapter;
import com.larrex.coinrecon.databinding.FragmentTrendingBinding;
import com.larrex.coinrecon.model.Item;
import com.larrex.coinrecon.model.Trending;
import com.larrex.coinrecon.viewmodel.TrendingViewModel;


public class TrendingFragment extends Fragment {

   FragmentTrendingBinding binding;

   TrendingViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_trending, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        viewModel = new ViewModelProvider(getActivity()).get(TrendingViewModel.class);

        binding.swipe.setColorSchemeResources(R.color.dark_turquoise);
        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.trendingRecyclerView.showShimmer();
                viewModel.getTrendingCoins();
//                binding.trendingRecyclerView.setLayoutManager(null);
//
//                binding.trendingRecyclerView.setAdapter(null);
            }
        });

        binding.trendingRecyclerView.showShimmer();

        viewModel.getTrendingCoins();

        viewModel.observeTrending().observe(getViewLifecycleOwner(), new Observer<Trending>() {
            @Override
            public void onChanged(Trending trending) {
                binding.swipe.setRefreshing(false);
                binding.trendingRecyclerView.hideShimmer();

                TrendingAdapter trendingAdapter = new TrendingAdapter(getContext(),trending.getCoins());
                binding.trendingRecyclerView.setAdapter(trendingAdapter);
                binding.trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }
        });

        return binding.getRoot();
    }
}