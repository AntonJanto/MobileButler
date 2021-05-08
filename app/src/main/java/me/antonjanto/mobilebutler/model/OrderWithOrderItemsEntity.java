package me.antonjanto.mobilebutler.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class OrderWithOrderItemsEntity
{
     @Embedded
     public Order order;
     @Relation(
          parentColumn = "orderId",
          entityColumn = "orderId"
     )
     public List<OrderItem> orderItem;
}
