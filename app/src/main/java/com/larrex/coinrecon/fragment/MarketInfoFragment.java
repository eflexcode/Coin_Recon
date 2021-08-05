package com.larrex.coinrecon.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentMartekInfoBinding;


public class MarketInfoFragment extends Fragment {


    FragmentMartekInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_martek_info,container,false);


        binding.marketInfoRecyclerView.showShimmer();

        return binding.getRoot();
    }
}