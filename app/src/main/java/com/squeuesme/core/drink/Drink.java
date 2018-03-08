package com.squeuesme.core.drink;

import java.io.Serializable;

/**
 * This class is to represent the abstract drink.
 * It will be the super class to all of the different
 * types of drinks.
 * Created by castro on 27/01/18.
 */

public class Drink
{
    private String name;
    private int quantity;

    private String brewer;
    private String type;
    private double price;
    private double volumeMillis;
    private double abv; // alcohol by volume
    private boolean isAlcoholic;

    /* CONSTRUCTORS */

    public Drink(String _name){
        name = _name;
    }

    public Drink(String _name, int _quantity){
        setName(_name);
        setQuantity(_quantity);
    }

    public Drink(String _name, String _brewer, String _type,
                 double _abv, boolean _isAlcoholic){
        setName(_name);
        setBrewer(_brewer);
        setType(_type);
        setAbv(_abv);
        setAlcoholic(_isAlcoholic);
    }

    public Drink(String _name, double _abv, boolean _isAlcoholic){
        setName(_name);
        setAbv(_abv);
        setAlcoholic(_isAlcoholic);
    }

    public Drink(String _name, double _price, double _volumeMillis){
        setName(_name);
        setPrice(_price);
        setVolumeMillis(_volumeMillis);
    }

    /* GETTERS and SETTERS */

    public String getName() { return name; }

    public void setName(String _name) { name = _name; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int _quantity){ quantity = _quantity; }

    public String getBrewer() { return brewer; }

    public void setBrewer(String _brewer) { brewer = _brewer; }

    public String getType() { return type; }

    public void setType(String _type) { type = _type; }

    public double getAbv() { return abv; }

    public void setAbv(double _abv) { abv = _abv; }

    public boolean setIsAlcoholic() { return isAlcoholic; }

    public void setAlcoholic(boolean _alcoholic) { isAlcoholic = _alcoholic; }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double _price)
    {
        price = _price;
    }

    public double getVolumeMillis()
    {
        return volumeMillis;
    }

    public void setVolumeMillis(double _volumeMillis)
    {
        volumeMillis = _volumeMillis;
    }

    @Override
    public String toString()
    {
        return this.getName() + "\t"
                + this.getQuantity() + "\n";
    }

}
