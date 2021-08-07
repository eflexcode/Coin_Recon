package com.larrex.coinrecon.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.MarketInfoAdapter;
import com.larrex.coinrecon.databinding.FragmentMartekInfoBinding;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.MarketInfoViewModel;

import java.util.List;


public class MarketInfoFragment extends Fragment {


    FragmentMartekInfoBinding binding;

    MarketInfoViewModel viewModel;

    MarketInfoAdapter adapter;

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

        viewModel = new ViewModelProvider(getActivity()).get(MarketInfoViewModel.class);

        adapter = new MarketInfoAdapter(getContext());


        viewModel.getMarketData("USD", 100, 1);

        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ApiResult<List<Market>>>() {
            @Override
            public void onChanged(ApiResult<List<Market>> listApiResult) {

                if (listApiResult.getResult().size() > 0 && listApiResult != null) {
                    adapter.submitList(listApiResult.getResult());
                    binding.marketInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.marketInfoRecyclerView.setAdapter(adapter);
                    binding.marketInfoRecyclerView.hideShimmer();

                }
            }
        });
    }
}