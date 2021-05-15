package me.antonjanto.mobilebutler.ui.orders.single;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.repository.OrderRepository;
import me.antonjanto.mobilebutler.repository.OrderRepositoryImpl;
import me.antonjanto.mobilebutler.services.OrderService;
import me.antonjanto.mobilebutler.services.OrderServiceImpl;

public class SingleOrderViewModel extends AndroidViewModel
{
     private LiveData<Order> order;
     private OrderService orderService;


     public SingleOrderViewModel(Application application)
     {
          super(application);
          orderService = new OrderServiceImpl();
     }

     public void init()
     {

     }

     public void init(long orderId)
     {
          fetchOrder(orderId);
     }

     public void fetchOrder(long orderId)
     {
          this.order = orderService.getOrder(orderId);
     }

     public LiveData<Order> getOrder()
     {
          return order;
     }

     public void createOrder()
     {
          orderService.openOrder(null);
     }
}