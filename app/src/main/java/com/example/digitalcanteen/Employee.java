package com.example.digitalcanteen;

/**
 * Created by ankurshaswat on 21/5/17.
 * <p>
 * This class is for type employee
 * <p>
 * Has very short use
 */

public class Employee {
    private String employee_id;
    private String employee_name;
    private Double balance;
    private Integer id;

    public Employee(String employee_id, String employee_name, Double balance, Integer id) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.balance = balance;
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
