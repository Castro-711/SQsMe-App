package com.squeuesme.core.drink;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is to represent an order
 * that will be placed by a customer and end up
 * on the OrdersBoard of the given venue.
 */

public class Order implements Serializable
{
    private static int orderCount;
    private int currentOrderNum;
    private String orderId;
    private String customerId;
    private ArrayList<Drink> currentOrder;
    private ArrayList<Integer> quantity;

    /* CONSTRUCTORS */

    /**
     * On reflection, I think when creating an order
     * a customer unique Id must be sent to the constructor.
     * From there the drinks will be added one at a time.
     */

    public Order(){
        currentOrder = new ArrayList<>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId){
        customerId = _customerId;
        currentOrder = new ArrayList<>();
        currentOrderNum = orderCount++;
    }

    public Order(String _customerId, ArrayList<Drink> _currentOrder){
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

    public ArrayList<Drink> getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(ArrayList<Drink> currentOrder) {
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

    public void addDrinkToOrder(Drink _drink){
        currentOrder.add(_drink);
    }

    public void removeDrinkFromOrder(Drink _drink){
        currentOrder.remove(_drink);
    }

    public ArrayList<String> getOrderAsStringArrayList(){
        ArrayList<String> order = new ArrayList<>();

        for(Drink d: currentOrder)
            order.add(d.toString());

        return order;
    }

    @Override
    public String toString()
    {
        String toReturn = "Order " + (currentOrderNum + 1) + ": \n";
        for(Drink d: currentOrder)
            toReturn += d.toString();

        return toReturn;
    }

}
