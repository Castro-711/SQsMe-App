package com.squeuesme.core;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is to represent customers that will
 * be attending venues to order drinks.
 *
 * The customer places and order and the OrderBoard
 * gets update by the OrdersManager interface.
 * Created by castro on 27/01/18.
 */

public class Customer extends User
{
    private Venue activeVenue;
    private Order activeOrder;
    private Order lastOrder;
    private ArrayList<String> favourites;
    private ArrayList<Order> orderHistory;

    public Customer(){
        super(UUID.randomUUID().toString());
        init();
    }

    public Customer(String _uniqueId, Venue _activeVenue){
        super(_uniqueId);
        activeVenue = _activeVenue;
    }

    public Customer(String _uniqueId, String _username, String _DOB,
                    Venue _activeVenue){
        super(_uniqueId, _username, _DOB);
        activeVenue = _activeVenue;
    }

    public void init(){
        activeOrder = new Order();
        lastOrder = new Order();
        favourites = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    public Order getCurrentOrder()
    {
        return activeOrder;
    }

    public void setCurrentOrder(Order _order)
    {
        activeOrder = _order;
    }

    public ArrayList<String> getFavourites()
    {
        return favourites;
    }

    public void setFavourites(ArrayList<String> _favs)
    {
        favourites = _favs;
    }

    public ArrayList<Order> getOrderHistory()
    {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Order> _orderHistory){
        orderHistory = _orderHistory;
    }

    public Order getLastOrder(){
        return lastOrder;
    }

    public void setLastOrder(Order _lastOrder)
    {
        lastOrder = _lastOrder;
    }

    public Venue getActiveVenue()
    {
        return activeVenue;
    }

    public void setActiveVenue(Venue _activeVenue)
    {
        activeVenue = _activeVenue;
    }

    public void placeOrder(Order _order) {
        activeVenue.addCustomerOrderToActiveQueue(_order);
    }

    public void cancelOrder(Order _order){
        activeVenue.removeCustomerOrderFromActiveQueue(_order);
    }

//    public OrdersBoard getOrdersBoard(){
//        return activeVenue.getCurrentOrdersBoard();
//    }
}
