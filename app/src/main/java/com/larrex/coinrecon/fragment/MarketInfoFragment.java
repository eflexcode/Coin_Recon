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
import android.widget.Toast;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.MarketInfoAdapter;
import com.larrex.coinrecon.databinding.FragmentMartekInfoBinding;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.MarketInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MarketInfoFragment extends Fragment {


    FragmentMartekInfoBinding binding;

    MarketInfoViewModel viewModel;

    MarketInfoAdapter adapter;

    boolean isLoading = false;

    int page = 1;

    List<Market> markets = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_martek_info, container, false);

        binding.marketInfoRecyclerView.showShimmer();

        init();

        return binding.getRoot();
    }

    private void init() {

        binding.swipe.setColorSchemeResources(R.color.dark_turquoise);
        adapter = new MarketInfoAdapter(getContext(), getChildFragmentManager());

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.marketInfoRecyclerView.showShimmer();
                adapter.clearAllData();
                viewModel.getMarketData("USD", 100, 1);
                binding.swipe.setRefreshing(false);
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(MarketInfoViewModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        binding.marketInfoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!binding.nested.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    page++;
                    isLoading = true;

                    viewModel.getMarketData("USD", 100, page);
//                    Toast.makeText(getActivity(), String.valueOf(linearLayoutManager.findLastCompletelyVisibleItemPosition()), Toast.LENGTH_SHORT).show();
                }

            }


        });

        viewModel.getMarketData("USD", 100, 1);

        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ApiResult<List<Market>>>() {
            @Override
            public void onChanged(ApiResult<List<Market>> listApiResult) {

                if (listApiResult.getResult().size() > 0 && listApiResult != null) {
                    binding.showError.setVisibility(View.GONE);
                    binding.marketInfoRecyclerView.setVisibility(View.VISIBLE);

                    adapter.setMarkets(listApiResult.getResult());
                    markets.addAll(listApiResult.getResult());
                    binding.marketInfoRecyclerView.setLayoutManager(linearLayoutManager);
                    binding.marketInfoRecyclerView.setAdapter(adapter);
                    binding.marketInfoRecyclerView.hideShimmer();
                    isLoading = false;


                }
            }
        });

        viewModel.getErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ApiResult<Error>>() {
            @Override
            public void onChanged(ApiResult<Error> errorApiResult) {
                binding.errorMessage.setText(errorApiResult.getResult().getErrorMessage());
                binding.marketInfoRecyclerView.hideShimmer();
                binding.marketInfoRecyclerView.setVisibility(View.INVISIBLE);
                binding.showError.setVisibility(View.VISIBLE);
            }
        });


    }
}

//    class CustomScroll extends RecyclerView.OnScrollListener {
//
//    }