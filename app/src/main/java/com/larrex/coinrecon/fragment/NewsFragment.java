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

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.NewsAdapter;
import com.larrex.coinrecon.databinding.FragmentNewsBinding;
import com.larrex.coinrecon.model.Article;
import com.larrex.coinrecon.model.News;
import com.larrex.coinrecon.viewmodel.NewsViewModel;

import java.util.List;


public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;

    NewsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(NewsViewModel.class);

        init();

        return binding.getRoot();
    }

    private void init() {

        binding.newsRecyclerView.showShimmer();

        binding.swipe.setColorSchemeResources(R.color.dark_turquoise);

        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.newsRecyclerView.setVisibility(View.VISIBLE);
                binding.showError.setVisibility(View.GONE);
                binding.newsRecyclerView.showShimmer();
                viewModel.getNewsData();
                binding.swipe.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);

        viewModel.getNewsData();

        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<News>() {
            @Override
            public void onChanged(News news) {

                List<Article> articles = news.getArticles();
                binding.newsRecyclerView.setVisibility(View.VISIBLE);
                NewsAdapter newsAdapter = new NewsAdapter(articles);
                binding.newsRecyclerView.setLayoutManager(linearLayoutManager);
                binding.newsRecyclerView.setAdapter(newsAdapter);

            }
        });

        viewModel.getErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.newsRecyclerView.hideShimmer();
                binding.errorMessage.setText(s);
                binding.newsRecyclerView.setVisibility(View.INVISIBLE);
                binding.showError.setVisibility(View.VISIBLE);
            }
        });

    }
}