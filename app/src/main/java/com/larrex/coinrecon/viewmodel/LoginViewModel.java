package com.larrex.coinrecon.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.larrex.coinrecon.api.ApiClients;
import com.larrex.coinrecon.model.ApiResult;
import com.larrex.coinrecon.model.Error;
import com.larrex.coinrecon.model.Market;
import com.larrex.coinrecon.repository.LoginRepository;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class LoginViewModel extends AndroidViewModel {

    //    @Named("getLoginMarket")

    @Named("loginMarket")
    @Inject
    ApiClients getLoginMarketDataApiClients;

    @Named("chartData")
    @Inject
    ApiClients getChartDataApiClients;

    private static final String TAG = "LoginViewModel";

    @Inject
    LoginRepository loginRepository;

    public MutableLiveData<ApiResult<List<Market>>> resultMutableLiveData = new MutableLiveData<ApiResult<List<Market>>>();
    public MutableLiveData<ApiResult<Error>> errorMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResult<Error>> errorChartMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Market> marketMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<List<Float>>> chartDataMutableLiveData = new MutableLiveData<>();

    //login
    public MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRegisterSuccessful = new MutableLiveData<>();
    public MutableLiveData<Boolean> isGoogleSuccessful = new MutableLiveData<>();

    Context context;

    @Inject
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public void getLoginMarketData() {

        Map<String, Object> map = new HashMap<>();
        map.put("vs_currency", "USD");
        map.put("order", "market_cap_desc");
        map.put("per_page", 120);
        map.put("page", "1");
        map.put("sparkline", false);
        map.put("price_change_percentage", "24h");

        getLoginMarketDataApiClients.getLoginMarketData(map).enqueue(new Callback<List<Market>>() {
            @Override
            public void onResponse(@NotNull Call<List<Market>> call, @NotNull Response<List<Market>> response) {

                Log.d(TAG, "onResponse: " + response.body().get(0).getName());

                if (response.code() == 404) {
                    Error error = new Error("Request Not Found");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 400) {
                    Error error = new Error("Bad Request");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 403) {
                    Error error = new Error("Request Forbidden");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 408) {
                    Error error = new Error("Request Timeout");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 409) {
                    Error error = new Error("Conflict");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 429) {
                    Error error = new Error("Too Many Requests");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 500) {
                    Error error = new Error("Internal Server Error");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                }

                if (response.code() == 200 && response.body() != null) {

                    ApiResult<List<Market>> marketApiResult = new ApiResult<>(response.body());

                    resultMutableLiveData.setValue(marketApiResult);
                } else {
                    Error error = new Error("Request OK but noting was returned");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorMutableLiveData.setValue(errorApiResult);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Market>> call, @NotNull Throwable t) {

                Error error = new Error(t.getMessage());

                ApiResult<Error> errorApiResult = new ApiResult<>(error);

                errorMutableLiveData.setValue(errorApiResult);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    public void getChartData(String coinName, String currency, int days) {

        Map<String, Object> map = new HashMap<>();
        map.put("vs_currency", currency);
        map.put("days", days);

        getChartDataApiClients.getChartData(coinName, map).enqueue(new Callback<List<List<Float>>>() {
            @Override
            public void onResponse(Call<List<List<Float>>> call, Response<List<List<Float>>> response) {
                if (response.code() == 404) {
                    Error error = new Error("Request Not Found");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 400) {
                    Error error = new Error("Bad Request");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 403) {
                    Error error = new Error("Request Forbidden");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 408) {
                    Error error = new Error("Request Timeout");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 409) {
                    Error error = new Error("Conflict");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 429) {
                    Error error = new Error("Too Many Requests");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                } else if (response.code() == 500) {
                    Error error = new Error("Internal Server Error");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                }

                if (response.code() == 200 && response.body() != null) {

                    chartDataMutableLiveData.setValue(response.body());
                } else {
                    Error error = new Error("Request OK but noting was returned");

                    ApiResult<Error> errorApiResult = new ApiResult<>(error);

                    errorChartMutableLiveData.setValue(errorApiResult);
                }
            }

            @Override
            public void onFailure(Call<List<List<Float>>> call, Throwable t) {
                Error error = new Error(t.getMessage());

                ApiResult<Error> errorApiResult = new ApiResult<>(error);

                errorChartMutableLiveData.setValue(errorApiResult);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    public void doLogin(String email, String password) {
        loginRepository.doLoginWhitEmailAndPassword(email, password);
    }

    public void doRegister(String email, String password, String name) {
        loginRepository.doRegisterEmailAndPassword(email, password, name);
    }

    public LiveData<Boolean> observeIsRegisterSuccessful() {

        isRegisterSuccessful = loginRepository.isRegisterSuccessful;

        return isRegisterSuccessful;

    }

    public LiveData<Boolean> observeIsLoginSuccessful() {

        isLoginSuccessful = loginRepository.isLoginSuccessful;

        return isLoginSuccessful;

    }

    public LiveData<Boolean> observeIsGoogleSuccessful() {

        isGoogleSuccessful = loginRepository.isGoogleSuccessful;

        return isGoogleSuccessful;

    }

    public void doGoogleSignIn(AuthCredential authCredential) {
        loginRepository.doGoogleSignIn(authCredential);
    }
}
