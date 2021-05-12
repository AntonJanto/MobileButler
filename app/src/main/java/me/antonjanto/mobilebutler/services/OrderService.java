package me.antonjanto.mobilebutler.services;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;

public interface OrderService
{
     void openOrder(Long tableNumber);
     LiveData<List<Order>> getOpenOrders();
     LiveData<Order> getOrder(long orderId);
}
