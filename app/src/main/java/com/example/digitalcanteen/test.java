package com.example.digitalcanteen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ankurshaswat on 21/5/17.
 */

public class test {
    public static void main(String[] args) {
        final Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String abc = sdf.format(date);
        System.out.println(abc);
    }
}
