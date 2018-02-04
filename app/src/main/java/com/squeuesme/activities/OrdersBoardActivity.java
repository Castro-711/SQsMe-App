package com.squeuesme.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

import java.util.ArrayList;

public class OrdersBoardActivity extends AppCompatActivity {

    private Venue venue;
    private OrdersBoard ordersBoard;

    private ListView listView;
    private ArrayList<String> ordersList;
    private ArrayAdapter<String> adapter;

    private Button goPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_board);

        Intent i = getIntent();
        ordersBoard = new OrdersBoard();
        //ordersBoard = (OrdersBoard) i.getSerializableExtra("OrdersBoard");
        // try this again by just passing venue


        listView = findViewById(R.id.lvOrdersView);
        ordersList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.activity_list_item,
                ordersList);


        listView.setAdapter(adapter);

        goPlaceOrder = findViewById(R.id.btnGoPlaceOrder);

        goPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrdersBoardActivity.this, PlaceOrder.class);
                startActivity(i);
            }
        });

        ordersList = ordersBoard.getActiveOrdersStringList();
        adapter.notifyDataSetChanged();

    }
}
