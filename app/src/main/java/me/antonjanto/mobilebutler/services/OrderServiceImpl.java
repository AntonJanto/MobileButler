package me.antonjanto.mobilebutler.services;

import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.model.Product;
import me.antonjanto.mobilebutler.repository.OrderRepository;
import me.antonjanto.mobilebutler.repository.OrderRepositoryImpl;

public class OrderServiceImpl implements OrderService
{
     private final OrderRepository orderRepository;

     public OrderServiceImpl()
     {
          this.orderRepository = OrderRepositoryImpl.getInstance();
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
     public void addProductToOrder(Order order, Product product, double quantity)
     {
          OrderItem orderItem = new OrderItem(order.getOrderId(), product, quantity);
          order.addItem(orderItem);
          orderRepository.updateOrder(order);
     }

     @Override
     public void closeOrder(Order order)
     {
          order.close();
          orderRepository.closeOrder(order);
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
