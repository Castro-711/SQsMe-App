package com.squeuesme.core.drink;

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
    private ArrayList<Drink> currentOrder;
    private ArrayList<Integer> quantity;

    /* CONSTRUCTORS */

    public Order(){
        currentOrder = new ArrayList<>();
        currentOrderNum = orderCount++;
    }

    public Order(Drink _drink)
    {
        currentOrder = new ArrayList<>();
        currentOrder.add(_drink);
        currentOrderNum = orderCount++;
    }

    public Order(ArrayList<Drink> _currentOrder)
    {
        currentOrder = _currentOrder;
        currentOrderNum = orderCount++;
    }

    /* GETTERS and SETTERS */

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

    @Override
    public String toString()
    {
        String toReturn = "Order " + (currentOrderNum + 1) + ": \n";
        for(Drink d: currentOrder)
            toReturn += d.toString();

        return toReturn;
    }

}
