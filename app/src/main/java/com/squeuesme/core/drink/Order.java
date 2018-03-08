package com.squeuesme.core.drink;

import android.util.Log;

import java.util.ArrayList;

/**
 * This class is to represent an order
 * that will be placed by a customer and end up
 * on the OrdersBoard of the given venue.
 */

public class Order
{
    private static int orderCount;
    private int currentOrderNum;
    private String current;
    private String orderId;
    private String customerId;
    private ArrayList<String> currentOrder;
    private ArrayList<Integer> quantity;

    /* CONSTRUCTORS */

    /**
     * On reflection, I think when creating an order
     * a customer unique Id must be sent to the constructor.
     * From there the drinks will be added one at a time.
     */

    public Order(){
        currentOrder = new ArrayList<String>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId){
        customerId = _customerId;
        currentOrder = new ArrayList<String>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId, ArrayList<String> _currentOrder){
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

    public ArrayList<Integer> getQuantityOfDrinks() {
        return quantity;
    }

    public void setQuantityOfDrinks(ArrayList<Integer> _quantity){
        quantity = _quantity;
    }

    public static int getNumberOfOrders(){
        return orderCount;
    }

    public void addDrinkToOrder(String _drink) {
        currentOrder.add(_drink.toString());
        Log.i("Index of current order", "" + currentOrder.indexOf(_drink));
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

    @Override
    public String toString()
    {
        String toReturn = "Order " + (currentOrderNum + 1) + ": \n";
        for(String d: currentOrder){
            toReturn += d.toString();
        }


        return toReturn;
    }

}
