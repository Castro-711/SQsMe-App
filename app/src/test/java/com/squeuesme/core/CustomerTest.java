package com.squeuesme.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by castro on 27/01/18.
 */
public class CustomerTest {

    private Customer one;
    private Venue venue;


    @Before
    public void setUp() throws Exception {
        one = new Customer();
        venue = new Venue("Boomers");

        // add boomers to ones active venue
        one.setActiveVenue(venue);

        Order oneOrder = new Order();
        oneOrder.addDrinkAndQuantityToOrder("Chieftain", 2);
        oneOrder.addDrinkAndQuantityToOrder("Jameson", 2);

        one.placeOrder(oneOrder);
        one.addDrinkToFavourites("Chieftain");
        one.addDrinkToFavourites("Jameson");
        one.addDrinkToFavourites("Coke");
    }

    @Test
    public void init() throws Exception {
        assertNotNull(one.getUniqueId());
        assertNotNull(one.getFavourites());
        assertNotNull(one.getLastOrder());
        assertNotNull(one.getOrderHistory());
    }

    @Test
    public void getCurrentOrder() throws Exception {
        assertEquals(one.getCurrentOrder().toJsonString(),
                "Order 6:\n[\n\t\"Jameson\" " + ": 2\n\t\"Chieftain\" " + ": 2\n]\n");
    }

    @Test
    public void setCurrentOrder() throws Exception {
        Order twoOrder = new Order();
        twoOrder.addDrinkAndQuantityToOrder("Wine", 5);
        one.setCurrentOrder(twoOrder);
        one.placeOrder(twoOrder);

        assertEquals(one.getCurrentOrder().toJsonString(),
                "Order 11:\n[\n\t\"Wine\" " + ": 5\n]\n");
    }

    @Test
    public void getFavourites() throws Exception {
        assertEquals(one.getFavourites().toString(), "[Chieftain, Jameson, Coke]");
    }

    @Test
    public void getOrderHistory() throws Exception {
        assertEquals(one.getOrderHistory().get(0).toJsonString(),
                "Order 8:\n[\n\t\"Jameson\" : 2\n\t\"Chieftain\" : 2\n]\n");
    }

    @Test
    public void getLastOrder() throws Exception {
        assertEquals(one.getLastOrder().toJsonString(),
                "Order 22:\n[\n\t\"Jameson\" : 2\n\t\"Chieftain\" : 2\n]\n");
    }

    @Test
    public void getActiveVenue() throws Exception {
        assertEquals(one.getActiveVenue().getName(), "Boomers");
    }

    @Test
    public void setActiveVenue() throws Exception {
        Venue sine = new Venue("Sine");
        one.setActiveVenue(sine);
        assertEquals(one.getActiveVenue().getName(), "Sine");
    }

    @Test
    public void placeOrder() throws Exception {
        Order newOrder = new Order();
        newOrder.addDrinkAndQuantityToOrder("Heineken", 7);
        one.placeOrder(newOrder);

        assertEquals(one.getCurrentOrder().toJsonString(),
                "Order 14:\n[\n\t\"Heineken\" : 7\n]\n");
    }

    @Test
    public void cancelOrder() throws Exception {
    }

}