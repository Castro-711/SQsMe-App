package com.squeuesme.activities.order;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squeuesme.core.Order;
import com.squeuesme.core.Customer;
import com.squeuesme.core.Venue;

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
    private String[] orderArray;
    private int currentDrinkNum;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean positionFourInflated;

    // Beers
    private TextView cheiftainQuantity, coorsQuantity, heinekenQuantity, tuborgQuantity;

    private Button addCheiftain, removeCheiftain;
    private Button addCoors, removeCoors;
    private Button addHeineken, removeHeineken;
    private Button addTuborg, removeTuborg;

    // Spirits
    private TextView jamesonQ, smirnoffQ, jdQ, captainsQ;

    private Button addJameson, removeJameson;
    private Button addSmirnoff, removeSmrinoff;
    private Button addJd, removeJd;
    private Button addCaptains, removeCaptains;

    // Wines
    private TextView nobiloQ, yellowQ, ningxiaQ, irvineQ;

    private Button addNobilo, removeNobilo;
    private Button addYellow, removeYellow;
    private Button addNingxia, removeNingxia;
    private Button addIrvine,  removeIrvine;

    // Minerals
    private TextView cokeQ, sevenupQ, cluborangeQ, schwepsQ;

    private Button addCoke, removeCoke;
    private Button add7up, remove7up;
    private Button addClubOrange, removeClubOrange;
    private Button addSchweps, removeSchweps;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        setupDBForOrders();
        setupDBReferenceListeners();

        orderContents = new HashMap<>();
        order = new Order();
        orderArray = new String[16];
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

                if(orderArray[0] != null)
                    for(int i = 0; i < orderArray.length; i++)
                        if(orderContents.containsKey(orderArray[i]))
                            cokeQ.setText("x. " + orderContents.get(orderArray[i]) + 1);

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

                    // specify an adapter
                    orderString = "";
                    mRecyclerView.setAdapter(mAdapter);

                    Button saveOrder = view.findViewById(R.id.btnSaveOrder);

                        saveOrder.setOnClickListener(new View.OnClickListener() {
                                    @Override
                        public void onClick(View v) {
                                        Log.i("Saving the order:", order.toJsonString());
                            saveOrder("First", order.toJsonString());


                        }
                    });

                    Button placeOrder =  view.findViewById(R.id.btnPlaceOrder);

                    placeOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            placeCurrentOrder();
                            Log.i("SQL get order: ", getOrder());
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
                    /**
                     * SETTING UP THE NAVIGATION BAR
                     * much of this code was taken from the example
                     * shown on the dev.light website and then further
                     * edited by castro to reflect the needs of SQsMe Application
                     */

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);

        // edit some UI aspects of the navigation bar
        navigationTabBar.setBgColor(Color.BLACK); // nav bar background
        navigationTabBar.setInactiveColor(Color.parseColor("#404040"));
        navigationTabBar.setActiveColor(Color.WHITE);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        // add the models to the nav bar
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_wine),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_wine))
                        .title("Wines")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_spirits_small),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_spirits_small))
                        .title("Spirits")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_beer),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_beer))
                        .title("Beers")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_softdrink),
                        Color.parseColor(colors[3]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_softdrink))
                        .title("Soft Drinks")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_cashreg),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.mipmap.ic_cashreg))
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

                        /**
                         * SETTING UP BUTTON LISTENERS
                         * FOR THE DIFFERENT CATEGORIES OF DRINK
                         */

    /**
     * Sets up the button listeners for the wine category
     * @param view
     */

    private void setupButtonListenersForWines(View view) {

        addNobilo = view.findViewById(R.id.addNobilo);
        removeNobilo = view.findViewById(R.id.removeNobilo);

        nobiloQ = view.findViewById(R.id.nobilo_quantity);

        if(order.getDrinks().containsKey("Nobilo Icon"))
            nobiloQ.setText("x. " + ((int) order.getDrinks().get("Nobilo Icon")));

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

        if(order.getDrinks().containsKey("Yellow Tail"))
            yellowQ.setText("x. " + ((int) order.getDrinks().get("Yellow Tail")));

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

        if(order.getDrinks().containsKey("Ningxia Wine"))
            ningxiaQ.setText("x. " + ((int) order.getDrinks().get("Ningxia Wine")));

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

        if(order.getDrinks().containsKey("Irvine Wine"))
            irvineQ.setText("x. " + ((int) order.getDrinks().get("Irvine Wine")));

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

    /**
     * Sets up button listeners for the Spirits category
     * @param view
     */

    private void setupButtonListenersForSpirits(View view) {

        addJameson = view.findViewById(R.id.addJameson);
        removeJameson = view.findViewById(R.id.removeJameson);

        jamesonQ = view.findViewById(R.id.jameson_quantity);

        if(order.getDrinks().containsKey("Jameson"))
            jamesonQ.setText("x. " + ((int) order.getDrinks().get("Jameson")));

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

        if(order.getDrinks().containsKey("Smirnoff"))
            smirnoffQ.setText("x. " + ((int) order.getDrinks().get("Smirnoff")));

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

        if(order.getDrinks().containsKey("Jack Daniels"))
            jdQ.setText("x. " + ((int) order.getDrinks().get("Jack Daniels")));

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

        if(order.getDrinks().containsKey("Captain Morgans"))
            captainsQ.setText("x. " + ((int) order.getDrinks().get("Captain Morgans")));

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

    /**
     * Sets up button listeners for the Beers category.
     * @param view
     */

    private void setupButtonListenersForBeers(View view) {

        // because these elements are in other layouts
        // we must refer to the layout first
        addCheiftain = view.findViewById(R.id.addCheiftain);
        removeCheiftain = view.findViewById(R.id.removeCheiftain);

        cheiftainQuantity = view.findViewById(R.id.cheiftain_quantity);

        if(order.getDrinks().containsKey("Cheiftain"))
            cheiftainQuantity.setText("x. " + ((int) order.getDrinks().get("Cheiftain")));

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

        if(order.getDrinks().containsKey("Coors"))
            coorsQuantity.setText("x. " + ((int) order.getDrinks().get("Coors")));

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

        if(order.getDrinks().containsKey("Heineken"))
            heinekenQuantity.setText("x. " + ((int) order.getDrinks().get("Heineken")));

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

        if(order.getDrinks().containsKey("Tuborg"))
            tuborgQuantity.setText("x. " + ((int) order.getDrinks().get("Tuborg")));

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

    /**
     * Sets up button listeners for the Minerals category
     * @param view
     */

    private void setupButtonsListenersForMinerals(View view) {

        // because these elements are in other layouts
        // we must refer to the layout first
        addCoke = view.findViewById(R.id.addCoke);
        removeCoke = view.findViewById(R.id.removeCoke);

        cokeQ = view.findViewById(R.id.coke_quantity);

        // this is used to make sure that the quantity is updated when the
        // user navigates back to this category layout after being here before moving.

        if(order.getDrinks().containsKey("Coke"))
            cokeQ.setText("x. " + ((int) order.getDrinks().get("Coke")));


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

        if(order.getDrinks().containsKey("7up"))
            sevenupQ.setText("x. " + ((int) order.getDrinks().get("7up")));

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

        if(order.getDrinks().containsKey("Club Orange"))
            cluborangeQ.setText("x. " + ((int) order.getDrinks().get("Club Orange")));

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

        if(order.getDrinks().containsKey("Schweppes"))
            schwepsQ.setText("x. " + (int) order.getDrinks().get("Schweppes"));

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

        order.addDrinkAndQuantityToOrder(name, (current + 1));

        // orderArray is used to populate the list at the final tabbed page
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


        Log.i("Order Contents", name + " -> " +
                String.valueOf(order.getDrinks().get(name)));
    }

    public void removeDrinkFromOrder(String name, TextView quantity){
        int current = Integer.parseInt(quantity.getText().toString().substring(3)  + "");

        if(current == 1)
        { // this is to remove the x.0 from the list
            for(int i = 0; i < currentDrinkNum; i++)
                if(orderArray[i].contains(name)){
                    // swap last drink with one removed
                    orderArray[i] = orderArray[currentDrinkNum - 1]; // move last in
                    orderArray[currentDrinkNum - 1] = ""; // reset the last drink
                    --currentDrinkNum; // reduce count
                    quantity.setText(".x " + (current - 1));
                    mAdapter.notifyDataSetChanged();
                }
        }

        else if(current > 0)
        {
            quantity.setText(".x " + (current - 1));
            order.getDrinks().put(name, current);
            order.decreaseQuantityOfDrink(name);

            for(int i = 0; i < currentDrinkNum; i++)
                if(orderArray[i].contains(name)){
                    orderArray[i] = name + "\t\t" + ".x " + (current - 1);
                    mAdapter.notifyDataSetChanged();
                }
        }

    }

    /**
     * This method is just used to set the navigation bar to black
     */

    public void setupPhoneUI(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
    }

                    /**
                     * DATABASE METHODS
                     */

    /**
     * This method is to save a customers order that they can later
     * quickly retrieve and place it again from the home screen.
     * @param _order
     * @param _name
     */

    public void saveOrder(String _name, String _order){

        try{

            SQLiteDatabase db = this.openOrCreateDatabase("orders", MODE_PRIVATE, null);

            // create a table
            // android studio does not parse the sql, so errors only appear at runtime
            db.execSQL("CREATE TABLE IF NOT EXISTS favourites (name VARCHAR, id INT(3), contents VARCHAR)");

            db.execSQL("INSERT INTO favourites (name, id, contents) " +
                    "VALUES (" + "'" + _name + "'" + ", 187," + "'" + _order + "'" + " )");



        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is used to gather saved orders from the local database
     * on the customers device. In future releases this will be encrypted
     * and made more secure for customers wanting that.
     * @return
     */

    public String getOrder(){

        // to get the data out of the db
        // a cursor allows us to loop through all the data

        String orderContents = "";

        // can use WHERE, AND, LIKE, LIMIT to improve query types
        // WHERE name = Ryan
        // WHERE name = Ryan AND age < 20
        // WHERE name LIKE "R%" - results names beginning with R
        // LIMIT will limit the number of results returned
        // LIMIT is very useful when using delete statements
        SQLiteDatabase db = this.openOrCreateDatabase("orders", MODE_PRIVATE, null);

        // DELETE FROM users WHERE name = 'Eric' LIMIT 1
        // UPDATE users SET age = 20 WHERE name = 'Ryan'
        Cursor c = db.rawQuery("SELECT * FROM favourites", null);

        // get the column indexes - different to mysql
        int nameIndex = c.getColumnIndex("name");
        int idIndex = c.getColumnIndex("id");
        int contentIndex = c.getColumnIndex("contents");

        ArrayList<Order> orders = new ArrayList<>();

        // move to first result
        if(c != null && c.moveToFirst()){
            do{
                    Log.i("Name", c.getString(nameIndex));
                    Log.i("Id", c.getString(idIndex) + "");
                    Log.i("Contents", c.getString(contentIndex) + "");

                orders.add(new Order(c.getString(idIndex)));

                // move to next result
                c.moveToNext();
            }
            while(c.moveToNext());
        }


        return orderContents;
    }

                /**
                 * SETTING UP FIREBASE METHODS
                 */

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
        activeRef.push().setValue(order.toJsonString());
    }

}
