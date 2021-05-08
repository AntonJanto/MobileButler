package me.antonjanto.mobilebutler.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.repository.OrderRepository;
import me.antonjanto.mobilebutler.repository.OrderRepositoryImpl;
import me.antonjanto.mobilebutler.services.OrderService;
import me.antonjanto.mobilebutler.services.OrderServiceImpl;
import me.antonjanto.mobilebutler.ui.adapters.OrderAdapter;

public class HomeViewModel extends AndroidViewModel
{
     private LiveData<List<Order>> orders;
     private OrderService orderService;

     public HomeViewModel(Application application)
     {
          super(application);
          OrderRepository repo = new OrderRepositoryImpl(application);
          orderService = new OrderServiceImpl(repo);
          orders = orderService.getOpenOrders();
     }

     public LiveData<List<Order>> getOrders()
     {
          return orders;
     }
}