package com.larrex.coinrecon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.ExchangeAdapter;
import com.larrex.coinrecon.adapter.MarketInfoAdapter;
import com.larrex.coinrecon.databinding.FragmentExchangeBinding;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.ExchangeViewModel;

import java.util.List;


public class ExchangeFragment extends Fragment {

    FragmentExchangeBinding binding;
    ExchangeAdapter adapter;

    ExchangeViewModel viewModel;

    boolean isLoading = false;

    int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exchange, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {

        viewModel = new ViewModelProvider(getActivity()).get(ExchangeViewModel.class);

        binding.exchangeRecyclerView.showShimmer();

        binding.swipe.setColorSchemeResources(R.color.dark_turquoise);
        adapter = new ExchangeAdapter(getActivity(), getChildFragmentManager());

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.exchangeRecyclerView.showShimmer();
                adapter.clearAllData();
                viewModel.getExchangeList( 100,1);
                binding.swipe.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        binding.exchangeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!binding.nested.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    isLoading = true;
                    page++;

                    viewModel.getExchangeList( 100,page);
                }

            }


        });

        viewModel.getExchangeList(100,1);

        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Exchange>>() {
            @Override
            public void onChanged(List<Exchange> listApiResult) {

                if (listApiResult.size() > 0 && listApiResult != null) {
                    binding.showError.setVisibility(View.GONE);
                    binding.exchangeRecyclerView.setVisibility(View.VISIBLE);

                    adapter.setMarkets(listApiResult);
                    binding.exchangeRecyclerView.setLayoutManager(linearLayoutManager);
                    binding.exchangeRecyclerView.setAdapter(adapter);
                    binding.exchangeRecyclerView.hideShimmer();
                    isLoading = false;


                }
            }
        });

        viewModel.getErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String >() {
            @Override
            public void onChanged(String error) {
                binding.errorMessage.setText(error);
                binding.exchangeRecyclerView.hideShimmer();
                binding.exchangeRecyclerView.setVisibility(View.INVISIBLE);
                binding.showError.setVisibility(View.VISIBLE);
            }
        });

    }
}