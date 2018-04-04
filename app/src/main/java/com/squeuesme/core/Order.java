package com.squeuesme.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is to represent an order
 * that will be placed by a customer and end up
 * on the OrdersBoard of the given venue.
 */

public class Order
{
    /**
     * remove orderCount, currentOrderNum, currentOrder soon to
     * match it up with the thesis.
     */

    private static int orderCount;
    private int currentOrderNum;
    private String orderStatus;
    private String orderId;
    private String venueId;
    private String customerId;
    private ArrayList<String> currentOrder;
    private Map<String, Integer> drinks;

    /* CONSTRUCTORS */

    /**
     * On reflection, I think when creating an order
     * a customer unique Id must be sent to the constructor.
     * From there the drinks will be added one at a time.
     */

    public Order(){
        currentOrder = new ArrayList<>();
        drinks = new HashMap<>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId){
        customerId = _customerId;
        currentOrder = new ArrayList<>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId, String _venueId, ArrayList<String> _currentOrder){
        customerId = _customerId;
        venueId = _venueId;
        currentOrder = _currentOrder;
        currentOrderNum = orderCount++;
    }

    /* GETTERS and SETTERS */

    public void updateOrderStatus(String _status){
        orderStatus = _status;
    }

    public String getOrderStatus(){
        return orderStatus;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setOrderId(String _orderId){
        orderId = _orderId;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String _customerId){
        customerId = _customerId;
    }

    public ArrayList<String> getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(ArrayList<String> currentOrder) {
        this.currentOrder = currentOrder;
    }

    public static int getNumberOfOrders(){
        return orderCount;
    }

    public Map<String, Integer> getDrinks(){
        return drinks;
    }

    public void addDrinkToOrder(String _drink) {
        currentOrder.add(_drink.toString());
    }

    public void addDrinkAndQuantityToOrder(String _drink, int _quantity) {
        drinks.put(_drink, _quantity);
        currentOrder.add(_drink.toString());
    }

    public void decreaseQuantityOfDrink(String _drink){
        if(drinks.get(_drink) > 0)
            drinks.put(_drink, drinks.get(_drink) - 1);
        else
        {
            drinks.remove(_drink);
            currentOrder.remove(_drink);
        }
    }

//    public void increaseQuantityOfDrinkOnOrder(Drink _drink, int quantity){
//        currentOrder.get(currentOrder.indexOf(_drink)).setQuantity(quantity);
//    }

    public void removeDrinkFromOrder(String _drink){
        currentOrder.remove(_drink);
    }

    public ArrayList<String> getOrderAsStringArrayList(){
        ArrayList<String> order = new ArrayList<>();

        for(String d: currentOrder)
            order.add(d.toString());

        return order;
    }

    public String[] getOrderAsArray(){
        String[] array = new String[20];
        int i = 0;

        for (Map.Entry<String, Integer> entry : drinks.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            array[i] = key + "\t\tx. " + value;
            i++;
        }
        return array;
    }

    public String toJsonString()
    {
        String toReturn = "Order " + (currentOrderNum + 1) + ":\n";

        toReturn += "[\n";

        // traverse the order map
        for (Map.Entry<String, Integer> entry : drinks.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            toReturn += "\t\"" + key + "\"" + " : " + value + "\n";
        }

        toReturn += "]\n";

        return toReturn;
    }

}
