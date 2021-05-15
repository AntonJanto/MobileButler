package me.antonjanto.mobilebutler.services;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.Product;

public interface OrderService
{
     void openOrder(Long tableNumber);
     LiveData<List<Order>> getOpenOrders();
     LiveData<Order> getOrder(long orderId);
     void addProductToOrder(Order order, Product product, double quantity);
}
