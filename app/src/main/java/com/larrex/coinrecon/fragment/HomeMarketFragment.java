package com.larrex.coinrecon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.HomeAdapter;
import com.larrex.coinrecon.databinding.FragmentHomeMarketBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeMarketFragment extends Fragment {

    FragmentHomeMarketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_market, container, false);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MarketInfoFragment());
        fragments.add(new TrendingFragment());


        List<String> strings = new ArrayList<>();
        strings.add("Cryptocurrency");
        strings.add("Trending");

        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager(), fragments, strings);
        binding.viewpager.setAdapter(homeAdapter);
        binding.viewpagertab.setupWithViewPager(binding.viewpager);

        return binding.getRoot();
    }
}