package com.squeuesme.core.venue;

import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.observer.Observer;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.user.StaffMember;

import java.util.ArrayList;

/**
 * This class is the super class to all of the
 * concrete venues that will be created in this
 * project.
 * Created by castro on 27/01/18.
 */

public class Venue implements Observer
{

    private String name;
    private String address;

    private OrdersBoard currentOrdersBoard;

    private ArrayList<Customer> activeCustomers;
    private ArrayList<StaffMember> activeStaffMembers;

    private ArrayList<Drink> venueMenu;

    // type will be know at run time as it will be
    // subclasses that are instantiated with their
    // given type

    /* CONSTRUCTORS */

    public Venue(String _name, String _address, OrdersBoard _ordersBoard){
        // set arguments
        setName(_name);
        setAddress(_address);
        setCurrentOrdersBoard(_ordersBoard);

        // instantiate
        activeCustomers = new ArrayList<>();
        activeStaffMembers = new ArrayList<>();
    }

    /**
     * Design and implement a method to build an OrderBoard
     * so that each day it can get called and gets given a name.
     * maybe the date and the unique name of the venue.
     */

    /**
     * This method adds a new Customer to the Venues list
     * of active customers. While adding the customer it also
     * sets the customers OrderBoard to that of the Venues and
     * register them as an observer.
     * Now to add the venue to the customers active venue
     * so they can access their available drinks.
     * @param _customer
     */

    public void addNewActiveCustomer(Customer _customer){
        activeCustomers.add(_customer);
    }

    /**
     * This method removes the Customer from the Venues
     * list of active customers. While setting the Customers
     * OrderBoard to null and removing them from being an observer.
     * @param _customer
     */

    public void makeCustomerInactive(Customer _customer) {
        activeCustomers.remove(_customer);
    }

    /**
     * Adds a new StaffMember to the Venues list of active
     * StaffMembers. While adding the new StaffMember it
     * also sets the staffMembers OrderBoard to that of the
     * Venue and adds them as an observer.
     * @param _staffMember
     */

    public void addNewActiveStaffMember(StaffMember _staffMember){
        activeStaffMembers.add(_staffMember);
    }

    /**
     * Removes the StaffMember from the Venues list of active
     * StaffMembers. While setting their OrderBoard to null and
     * removing them from being an observer.
     * @param _staffMember
     */

    public void makeStaffMemberInactive(StaffMember _staffMember){
        activeStaffMembers.remove(_staffMember);
    }

    /**
     * The following two methods are to add an remove drinks
     * from the venues menu. The customer chooses from this
     * menu when ordering a drink.
     * Only VenueAdmin for the given venue can have the
     * ability to add and remove drinks from the venueMenu.
     * @return
     */

    public void registerCustomerOrder(String _customerId, Order _order){
        currentOrdersBoard.getActiveOrders().add(_order);
    }

    public void addDrinkToMenu(Drink _drink)
    {
        venueMenu.add(_drink);
    }

    public void removeDrinkFromMenu(Drink _drink)
    {
        venueMenu.remove(_drink);
    }

    /* GETTERS and SETTERS */

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String _address) {
        address = _address;
    }

    public OrdersBoard getCurrentOrdersBoard()
    {
        return currentOrdersBoard;
    }

    public void setCurrentOrdersBoard(OrdersBoard _ordersBoard)
    {
        currentOrdersBoard = _ordersBoard;
        currentOrdersBoard.registerObserver(this);
    }

    public ArrayList<Customer> getActiveCustomers()
    {
        return activeCustomers;
    }

    public ArrayList<StaffMember> getActiveStaffMembers()
    {
        return activeStaffMembers;
    }

    @Override
    public void update(OrdersBoard _ordersBoard) {
        currentOrdersBoard = _ordersBoard;
    }
}
