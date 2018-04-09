package com.squeuesme.core;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is to represent an order
 * that will be placed by a customer and end up
 * on the OrdersBoard of the given venue.
 */

public class Order
{
    private Timestamp timestamp;
    private String orderId;
    private String venueId;
    private String customerId;
    private Map<String, Integer> drinks;

    /* CONSTRUCTORS */

    /**
     * On reflection, I think when creating an order
     * a customer unique Id must be sent to the constructor.
     * From there the drinks will be added one at a time.
     */

    public Order(){
        drinks = new HashMap<>();
    }

    public Order(String _customerId, String _venueId){
        timestamp = new Timestamp(System.currentTimeMillis());
        customerId = _customerId;
        venueId = _venueId;
        generateOrderId();
        drinks = new HashMap<>();
    }

    public void generateOrderId(){
        orderId = UUID.randomUUID().toString();
        orderId += "**" + customerId + "**" + venueId;
    }

    public String getOrderId(){
        return orderId;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String _customerId){
        customerId = _customerId;
    }

    public Map<String, Integer> getDrinks(){
        return drinks;
    }

    public void addDrinkAndQuantityToOrder(String _drink, int _quantity) {
        drinks.put(_drink, _quantity);

    }

    public void decreaseQuantityOfDrink(String _drink){
        if(drinks.get(_drink) > 1)
            drinks.put(_drink, drinks.get(_drink) - 1);
        else
        {
            drinks.remove(_drink);
        }
    }

    public String toJsonString()
    {
        Gson gson = new Gson();
        String x = gson.toJson(this, Order.class);

        return x;
    }

}
