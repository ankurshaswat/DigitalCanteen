package com.example.digitalcanteen.dataObjects;

/**
 * Created by ankurshaswat on 21/5/17.
 *
 *This class is used to temporarily store the items selected by user
 * to be later on stored in the profile of the logged in user.
 */


public class Sale {

    private String name;
    private Double cpi;//cost per item
    private Integer quan;
    private Double total;

    public Sale(String name, Double cpi, Integer quan, Double total) {
        this.name = name;
        this.cpi = cpi;
        this.quan = quan;
        this.total = total;

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

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    public void incr() {
        //This function will be used with + sign to increase the quantity of the item
        this.quan++;
        //Notify adapter after update
    }

    @Override
    public String toString() {
//        return super.toString();
        String sale = "" + getName() + " " + getTotal() + " " + getCpi() + " " + getQuan() + "";
        return sale;
    }

    public boolean decr() {
        //This function will be used with - sign and it will check for greater than 0 automatically
        if (this.quan > 1) {
            this.quan--;
            return true;
        }
        return false;


        //This function returns false when the sale has gone empty.
        //so when returned empty remove item from sales array and notify adapter to update list.
    }
}
