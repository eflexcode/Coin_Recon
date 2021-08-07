package com.larrex.coinrecon.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.larrex.coinrecon.MainActivity;
import com.larrex.coinrecon.R;
import com.larrex.coinrecon.adapter.MarketAdapterLogin;
import com.larrex.coinrecon.databinding.FragmentIntroBinding;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.viewmodel.LoginViewModel;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class IntroFragment extends Fragment {

    FragmentIntroBinding binding;

    LoginViewModel viewModel;

    public static int loadCount = 0;

    NavController navController;

    private final int internetRequestCode = 23;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false);
//        navController = findNavController(binding.getRoot());
        init();

        return binding.getRoot();
    }

    private void init() {

        if (loadCount == 0){
            binding.loginRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.loginRecyclerView.showShimmer();

            binding.swipe.setColorSchemeResources(R.color.dark_turquoise);
            binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    binding.showError.setVisibility(View.GONE);
                    viewModel.getLoginMarketData();
                    binding.swipe.setRefreshing(false);
                    binding.loginRecyclerView.showShimmer();
                }
            });

            viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET}, internetRequestCode);
            } else {
                viewModel.getLoginMarketData();
            }

            if (!checkInternetConnection()) {

                Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NetworkAvailability networkAvailability = new NetworkAvailability();
                networkAvailability.check();

            }

            MarketAdapterLogin marketAdapterLogin = new MarketAdapterLogin(getContext());

            Handler handler = new Handler();

            binding.skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            viewModel.resultMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ApiResult<List<Market>>>() {
                @Override
                public void onChanged(ApiResult<List<Market>> listApiResult) {

                    if (listApiResult.getResult().size() > 0 && listApiResult != null) {
                        binding.loginRecyclerView.setAdapter(marketAdapterLogin);
                        binding.loginRecyclerView.hideShimmer();
                        marketAdapterLogin.submitList(listApiResult.getResult());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 10000);


                    }
                }
            });

            viewModel.errorMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ApiResult<Error>>() {
                @Override
                public void onChanged(ApiResult<Error> errorApiResult) {
                    binding.errorMessage.setText(errorApiResult.getResult().getErrorMessage());
                    binding.loginRecyclerView.hideShimmer();
                    marketAdapterLogin.submitList(null);
                    binding.showError.setVisibility(View.VISIBLE);
                }
            });

            viewModel.marketMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Market>() {
                @Override
                public void onChanged(Market market) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coinData", market);
                    try {
                        findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_coinMarketDetailsFragment, bundle);

                    } catch (Exception e) {

                    }

                }
            });

            binding.signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_signupFragment);
//                    navController.navigate(R.id.action_loginFragment_to_signupFragment);
                    } catch (Exception e) {

                    }
                }
            });

            binding.loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_loginFragment2);
//                    navController.navigate(R.id.action_loginFragment_to_loginFragment2);
                    } catch (Exception e) {

                    }
                }
            });
        }else {
            loadCount = 1;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    class NetworkAvailability extends ConnectivityManager.NetworkCallback {

        private boolean isFirstCheck = true;

        public void check() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(this);
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

            if (isFirstCheck) {

                isFirstCheck = false;

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.showError.setVisibility(View.GONE);
                        viewModel.getLoginMarketData();
                        binding.loginRecyclerView.showShimmer();
                        Snackbar.make(binding.getRoot(), "Internet connection established", Snackbar.LENGTH_LONG).show();
                    }
                });
            }

        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            isFirstCheck = false;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            isFirstCheck = false;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean checkInternetConnection() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.getLoginMarketData();
        }

    }
}