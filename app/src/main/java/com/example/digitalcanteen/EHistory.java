package com.example.digitalcanteen;

/**
 * Created by Saransh Verma on 01-06-2017.
 */

public class EHistory {
    private String name;
    private double cpi;
    private int  quantity;
    private String date;
    private int Id;
    private String employeeCode;
    private double total;

    public EHistory(String name, double cpi, int quantity, String date, int id, String employeeCode, double total) {
        this.name = name;
        this.cpi = cpi;
        this.quantity = quantity;
        this.date = date;
        Id = id;
        this.employeeCode = employeeCode;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCpi() {
        return cpi;
    }

    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    //    private int quantity;



}
