package com.squeuesme.core;

/**
 * This class is where all my concrete users will
 * be inherited from.
 * In future if we add users for venue staff, they
 * too will be a subclass of user.
 * Created by castro on 27/01/18.
 */

public abstract class User
{
    private String uniqueId;
    private String username;
    private String DOB;
    private String city;
    private String country;


    public User(String _uniqueId){
        uniqueId = _uniqueId;
    }

    public User(String _uniqueId, String _username, String _DOB) {
        setUniqueId(_uniqueId);
        setUsername(_username);
        setDOB(_DOB);
    }

    public void setUniqueId(String _uniqueId){
        uniqueId = _uniqueId;
    }

    public String getUniqueId(){
        return uniqueId;
    }

    public void setUsername(String _username) {
        // username can only be set once in the beginning
        if(username == null)
            username = _username;
        else
            System.out.println("Username is already set to " + username);
    }

    public String getUsername()
    {
        return username;
    }

    public void setDOB(String _DOB) {
        // DOB can only be set once when creating profile
        DOB = _DOB;
    }

    public String getDOB() {
        return DOB;
    }

    public void setCity(String _city) {
        city = _city;
    }

    public String getCity() { return city; }

    public void setCountry(String _country) {country = _country; }

    public String getCountry() { return country; }

    public String toString()
    {
        return  "\n" + getUniqueId() +
                "\n" + getUsername() +
                "\n" + getDOB() + "\n";
    }
}