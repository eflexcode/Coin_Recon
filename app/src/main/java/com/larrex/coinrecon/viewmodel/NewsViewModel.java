package com.larrex.coinrecon.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.larrex.coinrecon.model.Exchange;
import com.larrex.coinrecon.model.News;
import com.larrex.coinrecon.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NewsViewModel extends AndroidViewModel {

    public MutableLiveData<News> resultMutableLiveData = new MutableLiveData<News>();
    public MutableLiveData<String> errorMutableLiveData = new MutableLiveData<>();


    @Inject
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    @Inject
    NewsRepository repository;

    public void getNewsData() {
        repository.getNewsData();
    }

    public LiveData<News> getResultMutableLiveData() {

        resultMutableLiveData = repository.resultMutableLiveData;

        return resultMutableLiveData;
    }

    public MutableLiveData<String> getErrorMutableLiveData() {
        errorMutableLiveData = repository.errorMutableLiveData;
        return errorMutableLiveData;
    }
}
