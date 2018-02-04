package com.squeuesme;

import android.annotation.SuppressLint;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squeuesme.activities.R;
import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

import java.util.ArrayList;

public class PlaceOrder extends AppCompatActivity {

    private Customer customer;
    private Venue venue;
    private OrdersBoard ordersBoard;
    private Order currentOrder;

    private Button placeOrder;
    private Button addToOrder;
    private Button removeLastDrink;

    private EditText drinkName;
    private String drinkNameString;
    private EditText drinkQuantity;
    private int drinkQuantityInt;

    private ListView listView;
    private ArrayList<String> listItems;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        placeOrder = findViewById(R.id.btnPlaceOrder);
        addToOrder = findViewById(R.id.btnAddToOrder);
        removeLastDrink = findViewById(R.id.btnRemoveLastDrink);

        drinkName = findViewById(R.id.edtDrinkName);
        drinkQuantity = findViewById(R.id.edtDrinkQuantity);

        // edit orders board to get date and unique id
        ordersBoard = new OrdersBoard();
        currentOrder = new Order();

        // dyamically adding data to listView
        listView = findViewById(R.id.lvCurrentOrder);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listItems);
        listView.setAdapter(adapter);

        venue = new Venue("Boomers", "Woodford", ordersBoard);
        getActiveCustomer(null);

        customer.setActiveVenue(venue);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customer.placeOrder(currentOrder);

                listView.setBackgroundColor(345);

                // need to reset currentOrders values
                currentOrder = new Order();
            }
        });

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drinkNameString = String.valueOf(drinkName.getText());
                getQuantity();

                // create and add drink to order
                Drink current = new Drink(drinkNameString, drinkQuantityInt);
                currentOrder.addDrinkToOrder(current);
                // add it to listItems, so reflected on listView
                listItems.add(current.toString());
                adapter.notifyDataSetChanged();

                // clean up after drink entered
                wipeEditTextValues();
                drinkName.requestFocus();
            }
        });

        removeLastDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int listSize = listItems.size();

                if(listSize > 0)
                {
                    listItems.remove(listSize - 1);
                    adapter.notifyDataSetChanged();
                    currentOrder.getCurrentOrder().remove(listSize - 1);
                }

                for(String s: listItems)
                    Log.i("listItems", "" + s);


                Log.i("Oder", "" + currentOrder.toString());

            }
        });
    }

    public void wipeEditTextValues(){
        drinkName.setText("");
        drinkQuantity.setText("");
    }

    public void getQuantity(){
        if(drinkQuantity.getText().toString().equals(""))
            drinkQuantityInt = 0;
        else
            drinkQuantityInt = Integer.parseInt(drinkQuantity.getText().toString());
    }

    public void getActiveCustomer(Customer _customer){
        if(_customer == null)
            customer = new Customer("12345");
        else
            customer = _customer;
    }
}
