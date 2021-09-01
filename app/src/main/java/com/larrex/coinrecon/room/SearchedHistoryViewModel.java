package com.larrex.coinrecon.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.larrex.coinrecon.room.SearchHistoryRepository;

import java.util.List;

public class SearchedHistoryViewModel extends AndroidViewModel {

    SearchHistoryRepository repository;

    public SearchedHistoryViewModel(@NonNull Application application) {
        super(application);

         repository = new SearchHistoryRepository(application);
    }


    public void insert(SearchHistoryEntity entity) {
      repository.insert(entity);
    }

    public void update(SearchHistoryEntity entity) {
       repository.update(entity);
    }

    public void delete(SearchHistoryEntity entity) {
        repository.delete(entity);
    }

    public LiveData<List<SearchHistoryEntity>> getEntityList() {
        return repository.getEntityList();
    }

    public void deleteAll() {
       repository.deleteAll();
    }
}
