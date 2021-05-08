package me.antonjanto.mobilebutler.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "table")
public class Table
{
     @PrimaryKey
     private long number;
     @Ignore
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
