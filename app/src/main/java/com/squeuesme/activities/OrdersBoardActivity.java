package com.squeuesme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

import java.util.ArrayList;

public class OrdersBoardActivity extends AppCompatActivity {

    private Venue venue;
    private OrdersBoard ordersBoard;

    private ListView listView;
    private ArrayList<Order> ordersList;
    private ArrayAdapter<String> adapter;

    private Button goPlaceOrder;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_board);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // end of URL
        myRef = database.getReferenceFromUrl(
                "https://sqsme-funknebula.firebaseio.com/ ");

        ordersList = new ArrayList<>();
        listView = findViewById(R.id.lvOrdersView);

//        //(3) In ViewingInfo.java page, to view lists***
//
//        final DatabaseReference ref1 = database.getReferenceFromUrl(
//                "https://androidapplms-database.firebaseio.com/Leagues/");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()) {


                     FirebaseListAdapter<Order> firebaseListAdapter =
                             new FirebaseListAdapter<Order>(
                             OrdersBoardActivity.this,
                             Order.class,
                             android.R.layout.simple_list_item_1,
                             myRef) {


                         @Override
                         //*Displays a list on the app, of leagues currently stored in database in realtime*
                         protected void populateView(View v, Order model, int position) {


//                             TextView textView = v.findViewById(android.R.id.text1);
//                             textView.setText("Order: " + model.getOrderId() + "\n\n");
                             //RelativeLayout layoutRoot=(RelativeLayout)findViewById(R.id.content_leagues);
                              ListView listViewRoot = findViewById(R.id.lvOrdersView);
//                            v.setBackgroundResource(R.drawable.gold_flowers_test1);

                         }
                     };

                     listView.setAdapter(firebaseListAdapter);

                     firebaseListAdapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }

         });
//        ordersList = ordersBoard.getActiveOrdersStringList();
//
//        listView = findViewById(R.id.lvOrdersView);
//        ordersList = new ArrayList<>();
//        adapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.activity_list_item,
//                ordersList);
//
//
//        listView.setAdapter(adapter);
//
//        ordersList = ordersBoard.getActiveOrdersStringList();
//
//        adapter.notifyDataSetChanged();

//
//                    goPlaceOrder = findViewById(R.id.btnGoPlaceOrder);
//
//                    goPlaceOrder.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent i = new Intent(OrdersBoardActivity.this, PlaceOrder.class);
//                            startActivity(i);
//                        }
//                    });
//
//
//                    adapter.notifyDataSetChanged();

        }

    }


