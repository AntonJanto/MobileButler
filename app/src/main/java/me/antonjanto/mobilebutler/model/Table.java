package me.antonjanto.mobilebutler.model;

import java.util.ArrayList;
import java.util.List;

public class Table
{
     private long number;
     private List<Order> orders;

     public Table()
     {
          orders = new ArrayList<>();
     }

     public long getNumber()
     {
          return number;
     }

     public void setNumber(long number)
     {
          this.number = number;
     }

     public List<Order> getOrders()
     {
          return orders;
     }

     public void setOrders(List<Order> orders)
     {
          this.orders = orders;
     }
}
