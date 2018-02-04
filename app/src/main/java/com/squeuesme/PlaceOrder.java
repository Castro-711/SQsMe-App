package com.squeuesme;

import android.annotation.SuppressLint;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squeuesme.activities.R;
import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

public class PlaceOrder extends AppCompatActivity {

    private Customer customer;
    private Venue venue;
    private OrdersBoard ordersBoard;
    private Order currentOrder;

    private Button placeOrder;
    private Button addToOrder;

    private EditText drinkName;
    private String drinkNameString;
    private EditText drinkQuantity;
    private int drinkQuantityInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        placeOrder = findViewById(R.id.btnPlaceOrder);
        addToOrder = findViewById(R.id.btnAddToOrder);

        drinkName = findViewById(R.id.edtDrinkName);
        drinkQuantity = findViewById(R.id.edtDrinkQuantity);

        ordersBoard = new OrdersBoard(); // edit orders board to get date and unique id
        currentOrder = new Order();

        venue = new Venue("Boomers", "Woodford", ordersBoard);
        getActiveCustomer(null);

        customer.setActiveVenue(venue);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customer.placeOrder(currentOrder);

                Log.i("Customer id", customer.getUniqueId());
                Log.i("Customer order",
                        customer.getOrdersBoard().getActiveOrders() + "");
                Log.i("Venue ordersBoard",
                        venue.getCurrentOrdersBoard().getActiveOrders() + "");

                // need to reset currentOrders values
                currentOrder = new Order();
            }
        });

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drinkNameString = String.valueOf(drinkName.getText());
                drinkQuantityInt = Integer.parseInt(String.valueOf(drinkQuantity.getText()));

                Log.i("drink name", "" + drinkName.getText());
                Log.i("drink quantity", "" + drinkQuantity.getText());

                Drink current = new Drink(drinkNameString, drinkQuantityInt);

                currentOrder.addDrinkToOrder(current);

                Log.i("Order", currentOrder.toString());
            }
        });


    }

    public void getActiveCustomer(Customer _customer){
        if(_customer == null)
            customer = new Customer("12345");
        else
            customer = _customer;
    }
}