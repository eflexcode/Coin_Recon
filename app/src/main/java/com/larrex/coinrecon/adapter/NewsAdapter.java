package com.larrex.coinrecon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.MarketInfoItemLayoutBinding;
import com.larrex.coinrecon.databinding.NewsItemLayoutBinding;
import com.larrex.coinrecon.model.Article;
import com.larrex.coinrecon.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<Article> articleList;

    public NewsAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        NewsItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.news_item_layout, parent, false);

        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = articleList.get(position);
        holder.binding.setArticle(article);

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        NewsItemLayoutBinding binding;

        public NewsViewHolder(NewsItemLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }
}
