package com.squeuesme.activities.order;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

/**
 * Created by GIGAMOLE on 28.03.2016.
 */
public class HorizontalNtbActivity extends Activity {

    private ImageView topImg;

    private Map<String, Integer> orderContents;

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        orderContents = new HashMap<>();

        topImg = findViewById(R.id.topImage);
        initUI();
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



                    final TextView txtPage = view.findViewById(R.id.txt_vp_item_page);
                    if(position == 2)
                        txtPage.setText(String.format("start new activity"));

                    container.addView(view);
                    return view;

                }
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = findViewById(R.id.ntb_horizontal);
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
                        .title("Favourites")
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

    public void addDrinkToOrder(String name, TextView quantity){
        int current = Integer.parseInt(quantity.getText().toString().substring(3) + "");
        quantity.setText(".x " + (current + 1));

        orderContents.put(name, current);

        Log.i("Order Contents", name + " -> " + String.valueOf(orderContents.get(name)));
    }

    public void removeDrinkFromOrder(String name, TextView quantity){
        int current = Integer.parseInt(quantity.getText().toString().substring(3)  + "");

        if(current > 0){
            quantity.setText(".x " + (current - 1));
            orderContents.put(name, current);
            Log.i("Order Contents", String.valueOf(orderContents.get(name)));
        }

    }

}
