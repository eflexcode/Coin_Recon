package com.larrex.coinrecon.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SearchHistoryRepository {

    private Application application;

    SearchHistoryDao dao;

    LiveData<List<SearchHistoryEntity>> entityList;

    public SearchHistoryRepository(Application application) {
        this.application = application;

        SearchHistoryDatabase database = SearchHistoryDatabase.getInstance(application);

        dao = database.dao();

        entityList = dao.getAllSearchedItem();

    }

    public void insert(SearchHistoryEntity entity) {
        new InsertSearchedItem(dao).execute(entity);
    }

    public void update(SearchHistoryEntity entity) {
        new UpdateSearchedItem(dao).execute(entity);
    }

    public void delete(SearchHistoryEntity entity) {
        new DeleteSearchedItem(dao).execute(entity);
    }

    public LiveData<List<SearchHistoryEntity>> getEntityList() {
        return entityList;
    }

    public void deleteAll() {
        new DeleteAll(dao).execute();
    }

    class InsertSearchedItem extends AsyncTask<SearchHistoryEntity, Void, Void> {

        SearchHistoryDao dao;

        public InsertSearchedItem(SearchHistoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SearchHistoryEntity... searchHistoryEntities) {

            dao.insertSearchedItem(searchHistoryEntities[0]);

            return null;
        }
    }

    class UpdateSearchedItem extends AsyncTask<SearchHistoryEntity, Void, Void> {

        SearchHistoryDao dao;

        public UpdateSearchedItem(SearchHistoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SearchHistoryEntity... searchHistoryEntities) {

            dao.updateSearchedItem(searchHistoryEntities[0]);

            return null;
        }
    }

    class DeleteSearchedItem extends AsyncTask<SearchHistoryEntity, Void, Void> {

        SearchHistoryDao dao;

        public DeleteSearchedItem(SearchHistoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SearchHistoryEntity... searchHistoryEntities) {

            dao.updateSearchedItem(searchHistoryEntities[0]);

            return null;
        }
    }

    class DeleteAll extends AsyncTask<Void, Void, Void> {

        SearchHistoryDao dao;

        public DeleteAll(SearchHistoryDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.deleteAllSearchedItem();

            return null;
        }
    }

}
