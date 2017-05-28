package com.example.digitalcanteen;

//package saransh.menutesting;

import java.util.List;

/**
 * Created by Saransh Verma on 22-05-2017.
 */

public class menuItem {
    private String name;
    private String price;
    public int id;
//    private int quantity;

    public menuItem(String name, String price) {
        this.name = name;
        this.price = price;
        this.id = -1;
//        this.quantity = 0;
    }

    public menuItem(String name, String price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//   / public menuItem CopyItem{
//
//        return new menuItem()
//    }

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
}

