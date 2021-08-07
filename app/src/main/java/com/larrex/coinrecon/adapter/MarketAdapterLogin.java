package com.larrex.coinrecon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.LoginMarkekLayoutBinding;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.LoginViewModel;

public class MarketAdapterLogin extends ListAdapter<Market, MarketAdapterLogin.MarketViewHolder> {

    Context context;

    public MarketAdapterLogin(Context context) {
        super(marketItemCallback);

        this.context = context;
    }

    static DiffUtil.ItemCallback<Market> marketItemCallback = new DiffUtil.ItemCallback<Market>() {
        @Override
        public boolean areItemsTheSame(@NonNull Market oldItem, @NonNull Market newItem) {
            return oldItem.getClass().equals(newItem.getClass());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Market oldItem, @NonNull Market newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        LoginMarkekLayoutBinding loginMarkekLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.login_markek_layout, parent, false);

        return new MarketViewHolder(loginMarkekLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {

        LoginViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(LoginViewModel.class);

        if (getItem(position) != null) {
            holder.binding.setMarket(getItem(position));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.marketMutableLiveData.setValue(getItem(position));
            }
        });

    }

    class MarketViewHolder extends RecyclerView.ViewHolder {

        LoginMarkekLayoutBinding binding;

        public MarketViewHolder(@NonNull LoginMarkekLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
