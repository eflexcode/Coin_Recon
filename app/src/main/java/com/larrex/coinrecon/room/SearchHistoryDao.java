package com.larrex.coinrecon.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SearchHistoryDao {

    @Insert
    void insertSearchedItem(SearchHistoryEntity entity);

    @Delete
    void deleteSearchedItem(SearchHistoryEntity entity);

    @Update
    void updateSearchedItem(SearchHistoryEntity entity);

    @Query("DELETE FROM SEARCH_HISTORY_TABLE")
    void deleteAllSearchedItem();

    @Query("SELECT * FROM SEARCH_HISTORY_TABLE ORDER BY ID DESC")
    LiveData<List<SearchHistoryEntity>> getAllSearchedItem();

}
