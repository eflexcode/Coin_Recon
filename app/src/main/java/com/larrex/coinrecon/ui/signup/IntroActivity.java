package com.larrex.coinrecon.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.larrex.coinrecon.R;
import com.larrex.coinrecon.databinding.ActivityLoginBinding;
import com.larrex.coinrecon.viewmodel.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

import static androidx.navigation.Navigation.findNavController;

@AndroidEntryPoint
public class IntroActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    LoginViewModel viewModel;


    private final int internetRequestCode = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.login_nav_host);

//        NavController navController = navHostFragment.getNavController();//Navigation.findNavController(this, R.id.login_nav_host);
//        navController.navigate(R.id.action_loginFragment_to_signupFragment);

//        init();
    }

//    private void init() {
//
//
//        binding.loginRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        binding.loginRecyclerView.showShimmer();
//
//        binding.swipe.setColorSchemeResources(R.color.dark_turquoise);
//        binding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.showError.setVisibility(View.GONE);
//                viewModel.getLoginMarketData();
//                binding.swipe.setRefreshing(false);
//                binding.loginRecyclerView.showShimmer();
//            }
//        });
//
//        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, internetRequestCode);
//        } else {
//            viewModel.getLoginMarketData();
//        }
//
//        if (!checkInternetConnection()) {
//
//            Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();
//
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            NetworkAvailability networkAvailability = new NetworkAvailability();
//            networkAvailability.check();
//
//        }
//
//        MarketAdapter marketAdapter = new MarketAdapter(this);
//
//        Handler handler = new Handler();
//
//        viewModel.resultMutableLiveData.observe(this, new Observer<ApiResult<List<Market>>>() {
//            @Override
//            public void onChanged(ApiResult<List<Market>> listApiResult) {
//
//                if (listApiResult.getResult().size() > 0 && listApiResult != null) {
//                    binding.loginRecyclerView.setAdapter(marketAdapter);
//                    binding.loginRecyclerView.hideShimmer();
//                    marketAdapter.submitList(listApiResult.getResult());
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    }, 10000);
//
//
//                }
//            }
//        });
//
//        viewModel.errorMutableLiveData.observe(this, new Observer<ApiResult<Error>>() {
//            @Override
//            public void onChanged(ApiResult<Error> errorApiResult) {
//                binding.errorMessage.setText(errorApiResult.getResult().getErrorMessage());
//                binding.loginRecyclerView.hideShimmer();
//                marketAdapter.submitList(null);
//                binding.showError.setVisibility(View.VISIBLE);
//            }
//        });
//
//        viewModel.marketMutableLiveData.observe(this, new Observer<Market>() {
//            @Override
//            public void onChanged(Market market) {
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.coinMarketDetailsFragment);
//            }
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    class NetworkAvailability extends ConnectivityManager.NetworkCallback {
//
//        private boolean isFirstCheck = true;
//
//        public void check() {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            connectivityManager.registerDefaultNetworkCallback(this);
//        }
//
//        @Override
//        public void onAvailable(@NonNull Network network) {
//            super.onAvailable(network);
//
//            if (isFirstCheck) {
//
//                isFirstCheck = false;
//
//            } else {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        binding.showError.setVisibility(View.GONE);
//                        viewModel.getLoginMarketData();
//                        binding.loginRecyclerView.showShimmer();
//                        Snackbar.make(binding.getRoot(), "Internet connection established", Snackbar.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//        }
//
//        @Override
//        public void onLost(@NonNull Network network) {
//            super.onLost(network);
//            isFirstCheck = false;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();
//                }
//            });
//        }
//
//        @Override
//        public void onUnavailable() {
//            super.onUnavailable();
//            isFirstCheck = false;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Snackbar.make(binding.getRoot(), "No Internet connection", Snackbar.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//
//    private boolean checkInternetConnection() {
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            viewModel.getLoginMarketData();
//        }
//
//    }
}