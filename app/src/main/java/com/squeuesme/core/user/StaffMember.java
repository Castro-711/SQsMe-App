package com.squeuesme.core.user;

import com.squeuesme.core.drink.Order;
import com.squeuesme.core.venue.OrdersBoard;
import com.squeuesme.core.venue.Venue;

/**
 * Created by castro on 04/02/18.
 */

public class StaffMember extends User
{
    private Venue activeVenue;

    public StaffMember(String _uniqueId, Venue _activeVenue){
        super(_uniqueId);
        activeVenue = _activeVenue;
    }

    public StaffMember(String _uniqueId, String _username, String _DOB,
                       Venue _activeVenue)
    {
        super(_uniqueId, _username, _DOB);
        activeVenue = _activeVenue;
    }

    public void confirmOrderCompleted(Order _order)
    {
        getOrdersBoard().confirmOrderComplete(_order);
    }

    public OrdersBoard getOrdersBoard()
    {
        return activeVenue.getCurrentOrdersBoard();
    }
}
