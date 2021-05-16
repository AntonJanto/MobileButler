package me.antonjanto.mobilebutler.repository.sqlite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.model.OrderWithOrderItemsEntity;

@Dao
public interface OrderDao
{
     @Transaction
     @Query("SELECT * FROM `order` WHERE orderId = :id")
     LiveData<OrderWithOrderItemsEntity> getOrder(long id);

     @Insert
     void insertOrder(Order order);

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insertOrderItems(List<OrderItem> orderItems);

     @Update
     void updateOrder(Order order);

     @Transaction
     @Update
     void updateOrderItems(List<OrderItem> orderItems);

     @Transaction
     @Query("SELECT * FROM `order` WHERE closed = 0")
     LiveData<List<OrderWithOrderItemsEntity>> getOpenedOrders();


     @Transaction
     @Query("SELECT * FROM `order`")
     LiveData<List<OrderWithOrderItemsEntity>> getAllOrders();
}
