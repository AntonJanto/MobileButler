package me.antonjanto.mobilebutler.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;

public interface OrderRepository
{
     LiveData<List<Order>> getOpenOrders();
     void insertNewOrder(Order order);
     LiveData<Order> getOrder(long orderId);
     void updateOrder(Order order);
     void closeOrder(Order order);
     void deleteOrder(long orderId);
}
