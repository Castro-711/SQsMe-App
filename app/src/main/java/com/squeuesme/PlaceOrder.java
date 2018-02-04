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

    private Button placeOrder;
    private EditText drinkName;
    private String drinkNameString;
    private EditText drinkQuantity;
    private int drinkQuantityInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        placeOrder = findViewById(R.id.placeOrder);

        drinkName = findViewById(R.id.drinkName);
        drinkQuantity = findViewById(R.id.drinkQuantity);




        ordersBoard = new OrdersBoard();
        venue = new Venue("Boomers", "Woodford", ordersBoard);
        getActiveCustomer(null);

        customer.setActiveVenue(venue);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("drink name", "" + drinkName.getText());
                Log.i("drink quantity", "" + drinkQuantity.getText());

                drinkNameString = String.valueOf(drinkName.getText());
                drinkQuantityInt = Integer.parseInt(String.valueOf(drinkQuantity.getText()));

                Drink current = new Drink(drinkNameString, drinkQuantityInt);

                Order order = new Order(current);

                customer.placeOrder(order);
                Log.i("Customer id", customer.getUniqueId());
                Log.i("Customer order",
                        customer.getOrdersBoard().getActiveOrders() + "");
                Log.i("Venue ordersBoard",
                        venue.getCurrentOrdersBoard().getActiveOrders() + "");
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
