package com.izhar.resto.ui.objects;

import java.util.List;
import java.util.Map;

public class Request {
    List<Food> foods;
    String id, total, dateTime, table_number;


    public Request(List<Food> foods, String total, String dateTime, String table_number) {
        this.foods = foods;
        this.total = total;
        this.dateTime = dateTime;
        this.table_number = table_number;
    }

    public Request(List<Food> foods, String total, String dateTime) {
        this.foods = foods;
        this.total = total;
        this.dateTime = dateTime;
    }

    public Request(String id, String total, String dateTime, String table_number) {
        this.id = id;
        this.total = total;
        this.dateTime = dateTime;
        this.table_number = table_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
