package me.antonjanto.mobilebutler.ui.orders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.repository.OrderRepository;
import me.antonjanto.mobilebutler.repository.OrderRepositoryImpl;
import me.antonjanto.mobilebutler.services.OrderService;
import me.antonjanto.mobilebutler.services.OrderServiceImpl;

public class OrdersViewModel extends AndroidViewModel
{
     private LiveData<List<Order>> orders;
     private OrderService orderService;

     public OrdersViewModel(Application application)
     {
          super(application);
          OrderRepository repo = OrderRepositoryImpl.getInstance();
          orderService = new OrderServiceImpl(repo);
          orders = orderService.getOpenOrders();
     }

     public LiveData<List<Order>> getOrders()
     {
          return orders;
     }

     public void createOrder()
     {
          orderService.openOrder(null);
     }
}