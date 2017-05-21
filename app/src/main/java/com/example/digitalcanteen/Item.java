package com.example.digitalcanteen;

/**
 * Created by ankurshaswat on 21/5/17.
 *
 * This class is for objects of the menu.
 * They have an id number , a name and the cost per item(cpi)
 */

public class Item {
    private int id;
    private String name;
    private Double cpi;// cpi is cost per item.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCpi() {
        return cpi;
    }

    public void setCpi(Double cpi) {
        this.cpi = cpi;
    }
}
