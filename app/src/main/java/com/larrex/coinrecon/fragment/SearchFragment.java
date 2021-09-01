package com.larrex.coinrecon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {


    FragmentSearchBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);



        return binding.getRoot();
    }
}