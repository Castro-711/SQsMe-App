package com.squeuesme.core.observer;

/**
 * Created by castro on 04/02/18.
 */

public interface Subject
{
    void registerObserver(Observer _observer);
    void removeObserver(Observer _observer);
    void notifyObservers();
}