package com.larrex.coinrecon.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.SearchHistoryAdapter;
import com.larrex.coinrecon.databinding.FragmentSearchBinding;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.room.SearchHistoryEntity;
import com.larrex.coinrecon.room.SearchedHistoryViewModel;
import com.larrex.coinrecon.viewmodel.SearchMarketViewModel;

import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;

    SearchedHistoryViewModel viewModel;

    SearchMarketViewModel searchMarketViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(SearchedHistoryViewModel.class);

        searchMarketViewModel = new ViewModelProvider(getActivity()).get(SearchMarketViewModel.class);

        viewModel.getEntityList().observe(getViewLifecycleOwner(), new Observer<List<SearchHistoryEntity>>() {
            @Override
            public void onChanged(List<SearchHistoryEntity> searchHistoryEntities) {
                if (searchHistoryEntities.isEmpty()) {

                    binding.searchHistoryRecyclerView.setVisibility(View.INVISIBLE);
                    binding.showError.setVisibility(View.VISIBLE);
                    binding.errorMessage.setText("No search history found");

                } else {
                    binding.searchHistoryRecyclerView.setVisibility(View.VISIBLE);
                    binding.showError.setVisibility(View.INVISIBLE);

                    SearchHistoryAdapter adapter = new SearchHistoryAdapter(searchHistoryEntities,getActivity());

                    binding.searchHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    binding.searchHistoryRecyclerView.setAdapter(adapter);

                }
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Marking query");

        binding.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_GO) {
                    String searchedItem = textView.getText().toString();

                    if (!searchedItem.trim().isEmpty()) {

                        Toast.makeText(getContext(), "You can also search with contract address", Toast.LENGTH_SHORT).show();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

                        Date date = new Date();

                        SearchHistoryEntity entity = new SearchHistoryEntity(dateFormat.format(date), searchedItem.toLowerCase());

                        viewModel.insert(entity);

                        searchMarketViewModel.getMarketData("usd", searchedItem.toLowerCase());

                        progressDialog.show();

                        return true;
                    }
                }

                return false;
            }
        });


        searchMarketViewModel.getIsAdapterLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean){
                    progressDialog.show();
                }else {
                    progressDialog.dismiss();
                }

            }
        });

        binding.cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.search.setText("");
            }
        });

        searchMarketViewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ApiResult<Market>>() {
            @Override
            public void onChanged(ApiResult<Market> marketApiResult) {

                progressDialog.dismiss();

                Market market = marketApiResult.getResult();

                CoinMarketDetailsFragment fragment = new CoinMarketDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("coinData", market);

                fragment.setArguments(bundle);
                fragment.show(getChildFragmentManager(),"coindata");
                searchMarketViewModel.isAdapterLoading.setValue(false);

            }
        });

        searchMarketViewModel.getErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ApiResult<Error>>() {
            @Override
            public void onChanged(ApiResult<Error> errorApiResult) {
                progressDialog.dismiss();
                searchMarketViewModel.isAdapterLoading.setValue(false);
                Toast.makeText(getContext(), errorApiResult.getResult().getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}