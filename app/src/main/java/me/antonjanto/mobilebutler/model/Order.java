package me.antonjanto.mobilebutler.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.antonjanto.mobilebutler.repository.sqlite.converters.DateConverter;

@Entity(tableName = "order")
public class Order implements Serializable
{
     @PrimaryKey(autoGenerate = true)
     private long orderId;
     private double totalPrice;
     private boolean closed;
     @TypeConverters(DateConverter.class)
     private LocalDateTime closedTime;
     @Ignore
     private List<OrderItem> items;
     private long tableId;

     public Order()
     {
          this.closed = false;
          this.totalPrice = 0;
          this.tableId = 0;
          this.items = new ArrayList<>();
          this.closedTime = null;
     }

     public long getTableId()
     {
          return tableId;
     }

     public void setTableId(long tableId)
     {
          this.tableId = tableId;
     }

     public long getOrderId()
     {
          return orderId;
     }

     public void setOrderId(long orderId)
     {
          this.orderId = orderId;
     }

     public double getTotalPrice()
     {
          calculateTotalPrice();
          return totalPrice;
     }

     public void setTotalPrice(double totalPrice)
     {
          this.totalPrice = totalPrice;
     }

     public LocalDateTime getClosedTime()
     {
          return closedTime;
     }

     public void setClosedTime(LocalDateTime closedTime)
     {
          this.closedTime = closedTime;
     }

     public List<OrderItem> getItems()
     {
          return items;
     }

     public void setItems(List<OrderItem> items)
     {
          this.items = items;
     }

     public boolean isClosed()
     {
          return closed;
     }

     public void setClosed(boolean closed)
     {
          this.closed = closed;
     }

     public void calculateTotalPrice()
     {
          double total = 0;
          for (OrderItem item : items) {
               total += item.getPrice();
          }
          setTotalPrice(total);
     }

     public void addItem(OrderItem orderItem)
     {
          boolean contains = false;
          for (OrderItem item : items) {
               if (orderItem.getProductId() == item.getProductId())
               {
                    contains = true;
                    item.setQuantity(item.getQuantity() + orderItem.getQuantity());
                    break;
               }
          }
          if (!contains)
               items.add(orderItem);
          calculateTotalPrice();
     }
}
