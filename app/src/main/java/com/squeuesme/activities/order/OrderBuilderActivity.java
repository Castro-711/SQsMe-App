package com.squeuesme.activities.order;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import devlight.io.library.ntb.NavigationTabBar;

import com.squeuesme.activities.R;
import com.squeuesme.core.drink.Drink;
import com.squeuesme.core.drink.Order;

/**
 * Created by GIGAMOLE on 28.03.2016.
 * Extended by Eric Cassells for sqsme app
 *
 * This class will contain the ui and func
 * for the users to build, place, save orders
 */

public class OrderBuilderActivity extends Activity {

    private ImageView topImg;

    private Map<String, Integer> orderContents;

    private Order order;
    private ArrayList<String> orderString;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


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

        orderContents = new HashMap<>();
        order = new Order();
//        order.

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

//
//                    mRecyclerView = findViewById(R.id.orderListView);
//
//                    // use this setting to improve performance if you know that changes
//                    // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//                    // use a linear layout manager
//                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                    mRecyclerView.setLayoutManager(mLayoutManager);
//
////         specify an adapter (see also next example)
//                    orderString = new ArrayList<>();
//                    mAdapter = new MyAdapter((String[]) orderString.toArray());
//                    mRecyclerView.setAdapter(mAdapter);

                    container.addView(view);
                    return view;
                }

                if(position == 3){

                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(
                            R.layout.activity_vp_minerals, null, false);

                        setupButtonsListenersForMinerals(view);

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

        orderContents.put(name, current);
        Drink latestDrink = new Drink(name, current);
        order.addDrinkToOrder(latestDrink);

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

}
