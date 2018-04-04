package com.squeuesme.core;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class is to represent customers that will
 * be attending venues to order drinks.
 *
 * The customer places the order and the OrderBoard
 * gets updated by the OrdersManager interface.
 * Created by castro on 27/01/18.
 */

public class Customer
{
    private String uniqueId;
    private Venue activeVenue;
    private Order activeOrder;
    private Order lastOrder;
    private ArrayList<String> favourites;
    private ArrayList<Order> orderHistory;

    /**
     * Default constructor that generates a new random UUID and
     * call the init() method.
     */

    public Customer(){
        uniqueId = UUID.randomUUID().toString();
        init();
    }

    /**
     * A single parameter constructor. It takes a venue as its param.
     * Within the constructor it then generates a new uniqueId and calls the
     * init() method. It sets the venue passed as a param to the customers
     * activeVenue.
     * @param _activeVenue
     */

    public Customer(Venue _activeVenue){
        uniqueId = UUID.randomUUID().toString();
        activeVenue = _activeVenue;
        init();
    }

    /**
     * This method initializes some of the objects used within the
     * Customer class. It is both for cleaner code and to avoid nullPointer exceptions.
     */

    public void init(){
        lastOrder = new Order();
        favourites = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    /**
     * This method returns the Customers UniqueId. Each time a customer
     * registers as customer at new venue they receive a new UniqueId.
     * This is to help keep their data a little safer as they will never
     * use the same Id twice.
     * @return uniqueId
     */

    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * This method takes an Order as a parameter and it sets the
     * customers activeOrder to the parameter order. This is used
     * when a customer wants to update an order etc.
     * @param _order
     */

    public void setCurrentOrder(Order _order){
        activeOrder = _order;
    }

    /**
     * This method returns an object of type Order. It returns the
     * customers current activeOrder.
     * @return activeOrder
     */

    public Order getCurrentOrder()
    {
        return activeOrder;
    }

    /**
     * This method returns an ArrayList of type String. This is the customers
     * list of favourite drinks. This data is not as sensitive in the sense that
     * it only contains their favourite drinks, it has not information on when they
     * may have ordered it or how many they may have ordered.
     * @return favourites
     */

    public ArrayList<String> getFavourites()
    {
        return favourites;
    }

    /**
     * This method is used to add drinks to the customers favourites list.
     * @param _drink
     */

    public void addDrinkToFavourites(String _drink){
        favourites.add(_drink);
    }

    /**
     * This method returns an ArrayList of Orders. The orders are only the orders
     * for the given day at the venue and they are not stored anywhere. This is just
     * to keep a log if something has happened with an order and the customer needs
     * to verify what orders it has placed with the venue.
     * @return
     */

    public ArrayList<Order> getOrderHistory()
    {
        return orderHistory;
    }

    /**
     * This method returns the last order placed by the customer.
     * @return lastOrder
     */

    public Order getLastOrder(){
        return orderHistory.get(orderHistory.size() - 1);
    }

    /**
     * This method returns the customers activeVenue.
     * @return activeVenue
     */

    public Venue getActiveVenue()
    {
        return activeVenue;
    }

    /**
     * This method takes a Venue as a parameter and it sets that venue to
     * the customers activeVenue.
     * @param _activeVenue
     */

    public void setActiveVenue(Venue _activeVenue)
    {
        activeVenue = _activeVenue;
    }

    /**
     * Customer places their order. They pass a created order as a parameter.
     * Once the order is placed it gets added to the current Venues Active Order
     * Queue, where it will stay until staff can tend to it.
     * At this point activeOrder is now set to the order passed as a param.
     * @param _order
     */

    public void placeOrder(Order _order) {
        activeVenue.addCustomerOrderToActiveQueue(_order);
        orderHistory.add(_order);
        activeOrder = _order;
    }

    /**
     * Can only cancel an active order. It must be done before
     * the order reaches the top of the venues Active Orders Queue.
     * Otherwise staff will be tending to the order.
     * This method also removes the activeOrder from the order
     */

    public void cancelOrder(){
        activeVenue.removeCustomerOrderFromActiveQueue(activeOrder);
        activeOrder.updateOrderStatus("Cancelled");
    }
}
