package com.squeuesme.core.venue;

import android.os.Parcel;
import android.os.Parcelable;

import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.observer.Observer;
import com.squeuesme.core.observer.Subject;

import java.io.Serializable;
import java.util.ArrayList;

public class OrdersBoard implements Subject, Serializable
{
    private ArrayList<Observer> observers;
    private ArrayList<Order> activeOrders;
    private ArrayList<Order> completedOrders;

    private String boardId;

    private static int boardCount = 0;

    /**
     * OrdersBoard
     * need to subscribe staff at the beginning
     * of the day and customers as they come and go
     */

    public OrdersBoard() {
        observers = new ArrayList<>();
        activeOrders = new ArrayList<>();
        completedOrders = new ArrayList<>();

        boardCount++;
        boardId = "Boomers " + boardCount;
    }

    /**
     * This function gets called when
     * a customer orders a new drink.
     *
     * @param _order
     */

    public void registerNewOrder(Order _order) {
        activeOrders.add(_order);

        notifyObservers();
    }

    /**
     * This function gets called when
     * staff complete an active order.
     *
     * @param _order
     */

    public void confirmOrderComplete(Order _order) {
        // move to completed orders list
        completedOrders.add(_order);

        // move remove from active orders list
        activeOrders.remove(_order);

        notifyObservers();
    }

    @Override
    public void registerObserver(Observer _observer) {
        observers.add(_observer);
    }

    @Override
    public void removeObserver(Observer _observer) {
        observers.remove(_observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers)
            o.update(this);
    }

    public void printActiveOrders() {
        for (Order o : activeOrders)
            System.out.println(o.toString());
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public ArrayList<Order> getActiveOrders() {
        return activeOrders;
    }

    public ArrayList<String> getActiveOrdersStringList(){
        ArrayList<String> toReturn = new ArrayList<>();

        for(Order o: activeOrders)
            toReturn.add(o.toString());

        return toReturn;
    }

    public ArrayList<Order> getCompletedOrders() {
        return completedOrders;
    }

    public String getBoardId() {
        return boardId;
    }

}