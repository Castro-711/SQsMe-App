package com.squeuesme.activities.order;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import devlight.io.library.ntb.NavigationTabBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squeuesme.activities.R;
import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;
import com.squeuesme.core.user.Customer;
import com.squeuesme.core.venue.Venue;

/**
 * Created by GIGAMOLE on 28.03.2016.
 * Extended by Eric Cassells for sqsme app
 *
 * This class will contain the ui and func
 * for the users to build, place, save orders
 */

public class OrderBuilderActivity extends Activity {

    private FirebaseDatabase db;
    private DatabaseReference activeRef;
    private DatabaseReference completeRef;

    private Customer customer;
    private Venue venue; // get venue by location
    private String orderString;

    private EditText drinkName;
    private EditText drinkQuan;
    private Button addTo;
    private Button makeOrder;

    private LinearLayout colour;
    private TextView textView;

    private ImageView topImg;

    private Map<String, Integer> orderContents;

    private Order order;

    private ListView orderList;
    private String[] orderArray;
    private int currentDrinkNum;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean positionFourInflated;

    // Beers

    private TextView cheiftainQuantity;
    private TextView coorsQuantity;
    private TextView heinekenQuantity;
    private TextView tuborgQuantity;

    private Button addCheiftain;
    private Button removeCheiftain;

    private Button addCoors;
    private Button removeCoors;

    private Button addHeineken;
    private Button removeHeineken;

    private Button addTuborg;
    private Button removeTuborg;

    // Spirits

    private TextView jamesonQ;
    private TextView smirnoffQ;
    private TextView jdQ;
    private TextView captainsQ;

    private Button addJameson;
    private Button removeJameson;

    private Button addSmirnoff;
    private Button removeSmrinoff;

    private Button addJd;
    private Button removeJd;

    private Button addCaptains;
    private Button removeCaptains;

    // Wines

    private TextView nobiloQ;
    private TextView yellowQ;
    private TextView ningxiaQ;
    private TextView irvineQ;

    private Button addNobilo;
    private Button removeNobilo;

    private Button addYellow;
    private Button removeYellow;

    private Button addNingxia;
    private Button removeNingxia;

    private Button addIrvine;
    private Button removeIrvine;

    // Minerals

    private TextView cokeQ;
    private TextView sevenupQ;
    private TextView cluborangeQ;
    private TextView schwepsQ;

    private Button addCoke;
    private Button removeCoke;

    private Button add7up;
    private Button remove7up;

    private Button addClubOrange;
    private Button removeClubOrange;

    private Button addSchweps;
    private Button removeSchweps;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        setupDBForOrders();
        setupDBReferenceListeners();

        orderContents = new HashMap<>();
        order = new Order();
        orderArray = new String[15];
        mAdapter = new MyAdapter(orderArray);

        topImg = findViewById(R.id.topImage);
        initUI();
        setupPhoneUI();

    }

    private void initUI() {
        final ViewPager viewPager = findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {

                if(position == 4){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                            R.layout.complete_order, null, false);


                    mRecyclerView = view.findViewById(R.id.orderListView);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    mRecyclerView.setHasFixedSize(true);

                    // use a linear layout manager
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);

                    // specify an adapter (see also next example)
                    orderString = "";
                    mRecyclerView.setAdapter(mAdapter);

                    Button saveOrder = view.findViewById(R.id.btnSaveOrder);

                        saveOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                        public void onClick(View v) {
                            saveOrder(order.toString(), "first");
                            Log.i("Saving the order:", order.toString());
                        }
                    });

                    Button placeOrder =  view.findViewById(R.id.btnPlaceOrder);

                    placeOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            placeCurrentOrder();
                        }
                    });



                    container.addView(view);
                    return view;
                }

                if(position == 3){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                            R.layout.activity_vp_minerals, null, false);

                        setupButtonsListenersForMinerals(view);

                    if(orderContents.containsKey("coke"))
                        cokeQ.setText( orderContents.get("Coke"));


                    positionFourInflated = true;

                    container.addView(view);
                    return view;
                }

                if(position == 2){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                                    R.layout.item_vp_beers, null, false);

                    setupButtonListenersForBeers(view);


                    container.addView(view);
                    return view;
                }
                else if(position == 1){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                                    R.layout.item_vp_spirits, null, false);

                    setupButtonListenersForSpirits(view);

                    container.addView(view);
                    return view;
                }
                else if(position == 0){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                                    R.layout.item_vp_wines, null, false);

                    setupButtonListenersForWines(view);

                    container.addView(view);
                    return view;
                }


                else
                {

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                                    R.layout.item_vp, null, false);

                    container.addView(view);
                    return view;

                }
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        Log.i("Colour + ", " -> " + colors[0]);
        Log.i("Colour + ", " -> " + colors[1]);
        Log.i("Colour + ", " -> " + colors[2]);
        Log.i("Colour + ", " -> " + colors[3]);
        Log.i("Colour + ", " -> " + colors[4]);


        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
        navigationTabBar.setBgColor(Color.BLACK); // nav bar background
        navigationTabBar.setInactiveColor(Color.parseColor("#404040"));
        navigationTabBar.setActiveColor(Color.WHITE);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_eighth),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Wines")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title("Spirits")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Beers")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_eighth),
                        Color.parseColor(colors[3]))
                        .title("Soft Drinks")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_euro))
                        .title("Order")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(
                    final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.hideBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    private void setupButtonListenersForWines(View view) {

        addNobilo = view.findViewById(R.id.addNobilo);
        removeNobilo = view.findViewById(R.id.removeNobilo);

        nobiloQ = view.findViewById(R.id.nobilo_quantity);

        addNobilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Nobilo Icon", nobiloQ);
            }
        });

        removeNobilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Nobilo Icon", nobiloQ);
            }
        });

        addYellow = view.findViewById(R.id.addYellow);
        removeYellow = view.findViewById(R.id.removeYellow);

        yellowQ = view.findViewById(R.id.yellow_quantity);

        addYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Yellow Tail", yellowQ);
            }
        });

        removeYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Yellow Tail", yellowQ);
            }
        });

        addNingxia = view.findViewById(R.id.addNingxia);
        removeNingxia = view.findViewById(R.id.removeNingxia);

        ningxiaQ = view.findViewById(R.id.ningxia_quantity);

        addNingxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Ningxia Wine", ningxiaQ);
            }
        });

        removeNingxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Ningxia Wine", ningxiaQ);
            }
        });

        addIrvine = view.findViewById(R.id.addIrvine);
        removeIrvine = view.findViewById(R.id.removeIrvine);

        irvineQ = view.findViewById(R.id.irvine_quantity);

        addIrvine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Irvine Wine", irvineQ);
            }
        });

        removeIrvine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Irvine Wine", irvineQ);
            }
        });
    }

    private void setupButtonListenersForSpirits(View view) {

        addJameson = view.findViewById(R.id.addJameson);
        removeJameson = view.findViewById(R.id.removeJameson);

        jamesonQ = view.findViewById(R.id.jameson_quantity);

        addJameson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Jameson", jamesonQ);
            }
        });

        removeJameson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Jameson", jamesonQ);
            }
        });

        addSmirnoff = view.findViewById(R.id.addSmirnoff);
        removeSmrinoff = view.findViewById(R.id.removeSmirnoff);

        smirnoffQ = view.findViewById(R.id.smirnoff_quantity);

        addSmirnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Smirnoff", smirnoffQ);
            }
        });

        removeSmrinoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Smirnoff", smirnoffQ);
            }
        });

        addJd = view.findViewById(R.id.addJd);
        removeJd = view.findViewById(R.id.removeJd);

        jdQ = view.findViewById(R.id.jd_quantity);

        addJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Jack Daniels", jdQ);
            }
        });

        removeJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Jack Daniels", jdQ);
            }
        });

        addCaptains = view.findViewById(R.id.addCaptains);
        removeCaptains = view.findViewById(R.id.removeCaptains);

        captainsQ = view.findViewById(R.id.captains_quantity);

        addCaptains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Captain Morgans", captainsQ);
            }
        });

        removeCaptains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Captain Morgans", captainsQ);
            }
        });
    }

    private void setupButtonListenersForBeers(View view) {

        // because these elements are in other layouts
        // we must refer to the layout first
        addCheiftain = view.findViewById(R.id.addCheiftain);
        removeCheiftain = view.findViewById(R.id.removeCheiftain);

        cheiftainQuantity = view.findViewById(R.id.cheiftain_quantity);

        addCheiftain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Cheiftain", cheiftainQuantity);
            }
        });

        removeCheiftain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Cheiftain", cheiftainQuantity);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        addCoors = view.findViewById(R.id.addCoors);
        removeCoors = view.findViewById(R.id.removeCoors);

        coorsQuantity= view.findViewById(R.id.coors_quantity);

        addCoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Coors", coorsQuantity);
            }
        });

        removeCoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Coors", coorsQuantity);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        addHeineken = view.findViewById(R.id.addHeineken);
        removeHeineken = view.findViewById(R.id.removeHeineken);

        heinekenQuantity = view.findViewById(R.id.heineken_quantity);

        addHeineken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Heineken", heinekenQuantity);
            }
        });

        removeHeineken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Heineken", heinekenQuantity);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        addTuborg = view.findViewById(R.id.addTuborg);
        removeTuborg = view.findViewById(R.id.removeTuborg);

        tuborgQuantity = view.findViewById(R.id.tuborg_quantity);

        addTuborg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Tuborg", tuborgQuantity);
            }
        });

        removeTuborg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Tuborg", tuborgQuantity);
            }
        });
    }

    private void setupButtonsListenersForMinerals(View view) {

        // because these elements are in other layouts
        // we must refer to the layout first
        addCoke = view.findViewById(R.id.addCoke);
        removeCoke = view.findViewById(R.id.removeCoke);

        cokeQ = view.findViewById(R.id.coke_quantity);

        for(int i = 0; i < orderArray.length; i++)
            if(orderArray[i] != null && orderArray[i].contains("Coke"))
                cokeQ.setText(orderArray[i].substring(orderArray[i].length() - 2));

        addCoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Coke", cokeQ);
            }
        });

        removeCoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Coke", cokeQ);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        add7up = view.findViewById(R.id.add7up);
        remove7up = view.findViewById(R.id.remove7up);

        sevenupQ = view.findViewById(R.id.seven_up_quantity);

        add7up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("7up", sevenupQ);
            }
        });

        remove7up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("7up", sevenupQ);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        addClubOrange = view.findViewById(R.id.addClubOrange);
        removeClubOrange = view.findViewById(R.id.removeClubOrange);

        cluborangeQ = view.findViewById(R.id.club_orange_quantity);

        addClubOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Club Orange", cluborangeQ);
            }
        });

        removeClubOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Club Orange", cluborangeQ);
            }
        });


        // because these elements are in other layouts
        // we must refer to the layout first
        addSchweps = view.findViewById(R.id.addSchweps);
        removeSchweps = view.findViewById(R.id.removeSchweps);

        schwepsQ = view.findViewById(R.id.schweps_quantity);

        addSchweps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrinkToOrder("Schweppes", schwepsQ);
            }
        });

        removeSchweps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDrinkFromOrder("Schweppes", schwepsQ);
            }
        });
    }

    public void addDrinkToOrder(String name, TextView quantity){
        int current = Integer.parseInt(quantity.getText().toString().substring(3) + "");
        quantity.setText(".x " + (current + 1));

        if(current == 0){
            orderArray[currentDrinkNum] = name + "\t\t" + ".x " + (current + 1);
            mAdapter.notifyDataSetChanged();
            currentDrinkNum++;
        }
        else{
            for(int i = 0; i < currentDrinkNum; i++)
                if(orderArray[i].contains(name)){
                    orderArray[i] = name + "\t\t" + ".x " + (current + 1);
                    mAdapter.notifyDataSetChanged();
                }
        }

        orderContents.put(name, current);

        if(orderContents.containsKey(name))
            Log.i("Contains", name);

//            order.increaseQuantityOfDrinkOnOrder(new Drink(name), current);

        Log.i("Order Contents", name + " -> " + String.valueOf(orderContents.get(name)));
    }

    public void removeDrinkFromOrder(String name, TextView quantity){
        int current = Integer.parseInt(quantity.getText().toString().substring(3)  + "");

        if(current > 0){
            quantity.setText(".x " + (current - 1));
            orderContents.put(name, current);
            Log.i("Order Contents", String.valueOf(orderContents.get(name)));
            Drink latestDrink = new Drink(name, current);
            order.removeDrinkFromOrder(latestDrink);
        }

    }

    public void setupPhoneUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

    /**
     * This method is to save a customers order that they can later
     * quickly retrieve and place it again from the home screen.
     * @param _order
     * @param _name
     */

    public void saveOrder(String _order, String _name){

        try{

            SQLiteDatabase db = this.openOrCreateDatabase("orders", MODE_PRIVATE, null);

            // create a table
            // android studio does not parse the sql, so errors only appear at runtime
            db.execSQL("CREATE TABLE IF NOT EXISTS orders (name VARCHAR, id INT(3), contents VARCHAR)");

            db.execSQL("INSERT INTO orders (name, id, contents) " +
                    "VALUES ('Favourite 1', 123, '[\n\t{ name:jameson \n\t}\n\t] ')");


            Log.i("SQL: ", getOrder());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getOrder(){

        // to get the data out of the db
        // a cursor allows us to loop through all the data

        // can use WHERE, AND, LIKE, LIMIT to improve query types
        // WHERE name = Ryan
        // WHERE name = Ryan AND age < 20
        // WHERE name LIKE "R%" - results names beginning with R
        // LIMIT will limit the number of results returned
        // LIMIT is very useful when using delete statements
        SQLiteDatabase db = this.openOrCreateDatabase("orders", MODE_PRIVATE, null);

        // DELETE FROM users WHERE name = 'Eric' LIMIT 1
        // UPDATE users SET age = 20 WHERE name = 'Ryan'
        Cursor c = db.rawQuery("SELECT * FROM orders", null);

        // get the column indexes - different to mysql
        int nameIndex = c.getColumnIndex("name");
        int idIndex = c.getColumnIndex("id");
        int contentIndex = c.getColumnIndex("contents");

        ArrayList<Order> orders = new ArrayList<>();

        // move to first result
        c.moveToFirst();
        while(c != null){

            Log.i("Name", c.getString(nameIndex));
            Log.i("Id", c.getString(idIndex) + "");
            Log.i("Contents", c.getString(contentIndex) + "");

            orders.add(new Order(c.getString(idIndex)));

            // move to next result
            c.moveToNext();
        }

        return orders.toString();
    }

    public void setupDBForOrders(){
        db = FirebaseDatabase.getInstance();
        activeRef = db.getReference("ActiveOrders");
        completeRef = db.getReference("CompletedOrders");
    }

    public void setupDBReferenceListeners(){

        completeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String order = (String) dataSnapshot.getValue();

                if(getCustomerId(order).equals(customer.getUniqueId()))
                    textView.setText(order);

                Log.i("Customer Id", getCustomerId(order));
                Log.i("Unique Id", customer.getUniqueId());

                Log.i("Completed it", dataSnapshot.toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getCustomerId(String _string){
        String[] split = _string.split("\t");

        return split[1];
    }

    public void placeCurrentOrder() {
        activeRef.push().setValue(order.toString());
    }

}
