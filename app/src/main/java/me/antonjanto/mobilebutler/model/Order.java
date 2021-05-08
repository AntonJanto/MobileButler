package me.antonjanto.mobilebutler.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order
{
     private long id;
     private double totalPrice;
     private LocalDateTime closed;
     private List<OrderItem> items;

     public Order()
     {
          items = new ArrayList<>();
     }

     public long getId()
     {
          return id;
     }

     public void setId(long id)
     {
          this.id = id;
     }

     public double getTotalPrice()
     {
          return totalPrice;
     }

     public void setTotalPrice(double totalPrice)
     {
          this.totalPrice = totalPrice;
     }

     public LocalDateTime getClosed()
     {
          return closed;
     }

     public void setClosed(LocalDateTime closed)
     {
          this.closed = closed;
     }

     public List<OrderItem> getItems()
     {
          return items;
     }

     public void setItems(List<OrderItem> items)
     {
          this.items = items;
     }
}
