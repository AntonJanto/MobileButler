package me.antonjanto.mobilebutler.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;

public interface OrderRepository
{
     LiveData<List<Order>> getAllOrders();
     LiveData<List<Order>> getOpenOrders();
     void insertNewOrder(Order order);
}
