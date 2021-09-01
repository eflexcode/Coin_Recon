package com.larrex.coinrecon.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SEARCH_HISTORY_TABLE")
public class SearchHistoryEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;

    private String searchedItem;

    public SearchHistoryEntity(String date, String searchedItem) {
        this.date = date;
        this.searchedItem = searchedItem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSearchedItem() {
        return searchedItem;
    }

    public void setSearchedItem(String searchedItem) {
        this.searchedItem = searchedItem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
