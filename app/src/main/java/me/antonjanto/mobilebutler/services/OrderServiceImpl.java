package me.antonjanto.mobilebutler.services;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.repository.OrderRepository;

public class OrderServiceImpl implements OrderService
{
     private final OrderRepository orderRepository;

     public OrderServiceImpl(OrderRepository orderRepository)
     {
          this.orderRepository = orderRepository;
     }

     @Override
     public void openOrder(Long tableNumber)
     {
          Order order = new Order();
          if (tableNumber != null)
               order.setTableId(tableNumber);
          orderRepository.insertNewOrder(order);
     }

     @Override
     public LiveData<List<Order>> getOpenOrders()
     {
          return orderRepository.getOpenOrders();
     }

     @Override
     public LiveData<Order> getOrder(long orderId)
     {
          return orderRepository.getOrder(orderId);
     }
}
