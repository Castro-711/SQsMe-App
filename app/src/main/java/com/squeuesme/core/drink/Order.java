package com.squeuesme.core.drink;

import android.util.Log;

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
    private static int orderCount;
    private int currentOrderNum;
    private String orderId;
    private String venueId;
    private String customerId;
    private ArrayList<String> currentOrder;
    private Map<String, Integer> orderMap;

    /* CONSTRUCTORS */

    /**
     * On reflection, I think when creating an order
     * a customer unique Id must be sent to the constructor.
     * From there the drinks will be added one at a time.
     */

    public Order(){
        currentOrder = new ArrayList<String>();
        orderMap = new HashMap<>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId){
        customerId = _customerId;
        currentOrder = new ArrayList<String>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId, String _venueId, ArrayList<String> _currentOrder){
        customerId = _customerId;
        currentOrder = _currentOrder;
        currentOrderNum = orderCount++;
    }

    /* GETTERS and SETTERS */

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
        return orderMap;
    }

    public void addDrinkToOrder(String _drink) {
        currentOrder.add(_drink.toString());
        Log.i("Index of current order", "" + currentOrder.indexOf(_drink));
    }

    public void addDrinkAndQuantityToOrder(String _drink, int _quantity) {
        orderMap.put(_drink, _quantity);
        currentOrder.add(_drink.toString());
        Log.i("Index of current order", "" + currentOrder.indexOf(_drink));
    }

    public void decreaseQuantityOfDrink(String _drink){
        if(orderMap.get(_drink) > 0)
            orderMap.put(_drink, orderMap.get(_drink) - 1);
        else
        {
            orderMap.remove(_drink);
            currentOrder.remove(_drink);
        }
    }

//    public void increaseQuantityOfDrinkOnOrder(Drink _drink, int quantity){
//        currentOrder.get(currentOrder.indexOf(_drink)).setQuantity(quantity);
//    }

    public void removeDrinkFromOrder(Drink _drink){
        currentOrder.remove(_drink);
    }

    public ArrayList<String> getOrderAsStringArrayList(){
        ArrayList<String> order = new ArrayList<>();

        for(String d: currentOrder)
            order.add(d.toString());

        return order;
    }

    public String[] orderAsAnArray(){
        String[] array = new String[20];
        int i = 0;

        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            array[i] = key + "\t\tx. " + value;
            i++;
        }
        return array;
    }

    public String toJsonString()
    {
        String toReturn = "Order " + (currentOrderNum + 1) + ": \n";

        toReturn += "[\n";

        // traverse the order map
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            toReturn += "\t\"" + key + "\"" + " : " + value + "\n";
        }

        toReturn += "]\n";

        return toReturn;
    }

}
