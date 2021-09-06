package com.larrex.coinrecon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.SearchedHistoryItemLayoutBinding;
import com.larrex.coinrecon.room.SearchHistoryEntity;
import com.larrex.coinrecon.viewmodel.SearchMarketViewModel;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder> {

    List<SearchHistoryEntity> entityList;

    Context context;

    public SearchHistoryAdapter(List<SearchHistoryEntity> entityList,Context context) {
        this.entityList = entityList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        SearchedHistoryItemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.searched_history_item_layout,parent,false);

        return new SearchHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHolder holder, int position) {

        SearchHistoryEntity entity = entityList.get(position);

        SearchMarketViewModel searchMarketViewModel;
        searchMarketViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(SearchMarketViewModel.class);
        holder.binding.setEntity(entity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchMarketViewModel.isAdapterLoading.setValue(true);
                searchMarketViewModel.getMarketData("usd",entity.getSearchedItem());

            }
        });


    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class SearchHistoryViewHolder extends RecyclerView.ViewHolder {

        SearchedHistoryItemLayoutBinding binding;

        public SearchHistoryViewHolder(@NonNull SearchedHistoryItemLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

}
