package com.squeuesme.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.squeuesme.activities.R;
import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

import java.util.ArrayList;
import java.util.Random;

public class PlaceOrder extends AppCompatActivity {

    private Customer customer;
    private Venue venue;
    private OrdersBoard ordersBoard;
    private Order currentOrder;

    private Button placeOrder;
    private Button addToOrder;
    private Button removeLastDrink;
    private Button goOrdersBoard;

    private EditText drinkName;
    private String drinkNameString;
    private TextView nameWarning;
    private EditText drinkQuantity;
    private int drinkQuantityInt;
    private TextView quantityWarning;

    private ListView listView;
    private ArrayList<String> listItems;
    private ArrayAdapter adapter;

    private boolean isPlaced;

    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        placeOrder = findViewById(R.id.btnPlaceOrder);
        addToOrder = findViewById(R.id.btnAddToOrder);
        removeLastDrink = findViewById(R.id.btnRemoveLastDrink);
        goOrdersBoard = findViewById(R.id.btnOrdersBoard);

        drinkName = findViewById(R.id.edtDrinkName);
        nameWarning = findViewById(R.id.tvWarningName);
        drinkQuantity = findViewById(R.id.edtDrinkQuantity);
        quantityWarning = findViewById(R.id.tvWarningQuantity);

        // dynamically adding data to listView
        listView = findViewById(R.id.lvCurrentOrder);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                listItems);
        listView.setAdapter(adapter);

        // edit orders board to get date and unique id
        ordersBoard = new OrdersBoard(venue);
        currentOrder = new Order();

        venue = new Venue("Boomers", "Woodford", ordersBoard);

        customer = new Customer("12345");

        venue.addNewActiveCustomer(customer);

        customer.setActiveVenue(venue);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customer.placeOrder(currentOrder);

                listView.setBackgroundColor(Color.GREEN);

                // need to reset currentOrders values
                currentOrder = new Order();

                Log.i("activeOrders", ordersBoard.getActiveOrders().size() + "");

                isPlaced = true;
                removeLastDrink.setText("Cancel Order");
                removeLastDrink.setTextColor(Color.RED);

            }
        });

        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDrinkName();
                getDrinkQuantity();

                if(nameWarning.getVisibility() == View.GONE
                        && quantityWarning.getVisibility() == View.GONE){

                    listView.setVisibility(View.VISIBLE);

                    // create and add drink to order
                    Drink current = new Drink(drinkNameString, drinkQuantityInt);
                    currentOrder.addDrinkToOrder(current);
                    // add it to listItems, so reflected on listView
                    listItems.add(current.toString());
                    adapter.notifyDataSetChanged();

                }

                // clean up after drink entered
                wipeEditTextValues();
                drinkName.requestFocus();

                Log.i("activeOrders", ordersBoard.getActiveOrders().size() + "");
            }
        });

        removeLastDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isPlaced = false) // last drink is being removed
                {
                    int listSize = listItems.size();

                    if(listSize == 1)
                        listView.setVisibility(View.GONE);

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
                else // order is getting cancelled
                {
                    listItems.removeAll(listItems);
                    adapter.notifyDataSetChanged();
                    listView.setVisibility(View.GONE);

                    currentOrder = new Order();
                    removeLastDrink.setText("Remove Last Drink");
                    isPlaced = false;
                }

            }
        });

        rand = new Random(123);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> adapterView, View view, int i, long l) {

                int next = rand.nextInt(12345);

                Log.i("NEXT", next+"");

                if(next % 2 == 0)
                    view.setBackgroundColor(Color.BLUE);
                else if (next % 3 == 0)
                    view.setBackgroundColor(Color.YELLOW);
                else
                    view.setBackgroundColor(Color.MAGENTA);

                return false;
            }
        });

        goOrdersBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        PlaceOrder.this, OrdersBoardActivity.class);
                i.putStringArrayListExtra(
                        "ordersBoard", ordersBoard.getActiveOrdersStringList());
                startActivity(i);
            }
        });
    }

    // maybe create a method where you can long press on the
    // text view in the listView and then you can remove it
    // from the order that would be slick - ish

    public void wipeEditTextValues(){
        drinkName.setText("");
        drinkQuantity.setText("");
    }

    public boolean isEmpty(EditText _editText){
        if(_editText.getText().toString().equals(""))
            return true;

        return false;
    }

    public void getDrinkName(){
        if(isEmpty(drinkName))
            nameWarning.setVisibility(View.VISIBLE);
        else
        {
            drinkNameString = drinkName.getText().toString();
            nameWarning.setVisibility(View.GONE);
        }

    }

    public void getDrinkQuantity(){
        if(isEmpty(drinkQuantity))
            quantityWarning.setVisibility(View.VISIBLE);
        else
        {
            drinkQuantityInt = Integer.parseInt(drinkQuantity.getText().toString());
            quantityWarning.setVisibility(View.GONE);
        }
    }

    public Customer getActiveCustomer(Customer _customer){
        if(_customer == null)
            customer = new Customer("12345");
        else
            customer = _customer;

        return customer;
    }
}
