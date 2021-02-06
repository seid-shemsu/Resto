package com.izhar.resto.ui.objects;

import java.util.List;
import java.util.Map;

public class Request {
    List<Food> foods;
    String total, dateTime;
    Map<Food, Integer> foodIntegerMap;

    public Request(String total, String dateTime, Map<Food, Integer> foodIntegerMap) {
        this.total = total;
        this.dateTime = dateTime;
        this.foodIntegerMap = foodIntegerMap;
    }

    public Map<Food, Integer> getFoodIntegerMap() {
        return foodIntegerMap;
    }

    public void setFoodIntegerMap(Map<Food, Integer> foodIntegerMap) {
        this.foodIntegerMap = foodIntegerMap;
    }

    public Request(List<Food> foods, String total, String dateTime) {
        this.foods = foods;
        this.total = total;
        this.dateTime = dateTime;
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
