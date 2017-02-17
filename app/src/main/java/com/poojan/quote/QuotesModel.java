package com.poojan.quote;

/**
 * Created by VNurtureTechnologies on 17/02/17.
 */

public class QuotesModel {

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    String quotes;

    public int getCatId() {
        return catId;
    }

    public int getId() {
        return id;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setId(int id) {
        this.id = id;
    }

    int catId,id;

}
