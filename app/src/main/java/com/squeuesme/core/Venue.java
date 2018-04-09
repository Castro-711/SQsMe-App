package com.squeuesme.core;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by castro on 16/02/18.
 */

public class Venue
{
    private String name;
    private LatLng latLng;
    private ArrayList<Customer> activeCustomers;
    private ArrayList<Order> activeOrders;
    private ArrayList<Order> completedOrders;
    private ArrayList<String> venueMenu;

    public Venue(String _name){
        setName(_name);
        setupArrays();
    }

    public Venue(LatLng latLng){
        this.latLng = latLng;
        setupArrays();
    }


    /**
     * This method adds a new Customer to the Venues list
     * of active customers. While adding the customer it also
     * sets the customers OrderBoard to that of the Venues and
     * register them as an observer.
     * Now to add the venue to the customers active venue
     * so they can access their available drinks.
     * @param _customer
     */

    public void addActiveCustomer(Customer _customer){
        activeCustomers.add(_customer);
    }

    public void setupArrays(){
        activeCustomers = new ArrayList<>();
        activeOrders = new ArrayList<>();
        completedOrders = new ArrayList<>();
    }

    /**
     * This method removes the Customer from the Venues
     * list of active customers. While setting the Customers
     * OrderBoard to null and removing them from being an observer.
     * @param _customer
     */

    public void removeActiveCustomer(Customer _customer){
        activeCustomers.remove(_customer);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Customer> getActiveCustomers() {
        return activeCustomers;
    }

    public void setActiveCustomers(ArrayList<Customer> activeCustomers) {
        this.activeCustomers = activeCustomers;
    }

    public ArrayList<Order> getActiveOrders() {
        return activeOrders;
    }

    public void setActiveOrders(ArrayList<Order> activeOrders) {
        this.activeOrders = activeOrders;
    }

    public ArrayList<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(ArrayList<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }

    public void addCustomerOrderToActiveQueue(Order _order){
        this.activeOrders.add(_order);
    }

    public void confirmCustomerOrderComplete(Order _order){
        removeCustomerOrderFromActiveQueue(_order);
        this.completedOrders.add(_order);
    }

    public void removeCustomerOrderFromActiveQueue(Order _order){
        this.activeOrders.remove(_order);
    }

    /**
     * The following two methods are to add an remove drinks
     * from the venues menu. The customer chooses from this
     * menu when ordering a drink.
     * Only VenueAdmin for the given venue can have the
     * ability to add and remove drinks from the venueMenu.
     * @return
     */
    public void addDrinkToMenu(String _drink)
    {
        venueMenu.add(_drink);
    }

    public void removeDrinkFromMenu(String _drink)
    {
        venueMenu.remove(_drink);
    }
}
