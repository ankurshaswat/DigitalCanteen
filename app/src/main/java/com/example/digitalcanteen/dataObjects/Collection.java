package com.example.digitalcanteen.dataObjects;

/**
 * Created by ankurshaswat on 22/6/17.
 */

public class Collection {

    private String date;
    private Integer id;
    private Double collection;

    public Collection(String date, Integer id, Double collection) {
        this.date = date;
        this.id = id;
        this.collection = collection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCollection() {
        return collection;
    }

    public void setCollection(Double collection) {
        this.collection = collection;
    }
}
