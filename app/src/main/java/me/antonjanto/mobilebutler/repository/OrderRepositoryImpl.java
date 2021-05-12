package me.antonjanto.mobilebutler.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderWithOrderItemsEntity;
import me.antonjanto.mobilebutler.repository.sqlite.MobileButlerDatabase;
import me.antonjanto.mobilebutler.repository.sqlite.OrderDao;

public class OrderRepositoryImpl implements OrderRepository
{
     private static OrderRepository instance;

     private final OrderDao orderDao;

     private final LiveData<List<Order>> allOrders;
     private final LiveData<List<Order>> openedOrders;

     private OrderRepositoryImpl(Application application)
     {
          orderDao = MobileButlerDatabase
               .getInstance(application.getApplicationContext())
               .orderDao();
          openedOrders = mapOrders(orderDao.getOpenedOrders());
          allOrders = mapOrders(orderDao.getAllOrders());
     }

     public static OrderRepository getInstance(Application application)
     {
          if (instance == null)
               instance = new OrderRepositoryImpl(application);
          return instance;
     }

     public static OrderRepository getInstance()
     {
          return instance;
     }

     @NotNull
     private LiveData<List<Order>> mapOrders(LiveData<List<OrderWithOrderItemsEntity>> openedOrders)
     {
          return Transformations.map(openedOrders, orders -> orders
               .stream()
               .map(order -> {
                    order.order.setItems(order.orderItem);
                    return order.order;
          }).collect(Collectors.toList()));
     }

     private LiveData<Order> mapOrder(LiveData<OrderWithOrderItemsEntity> order)
     {
          return Transformations.map(order, or -> {
               or.order.setItems(or.orderItem);
               return or.order;
          });
     }

     @Override
     public LiveData<List<Order>> getAllOrders()
     {
          return allOrders;
     }

     @Override
     public LiveData<List<Order>> getOpenOrders()
     {
          return openedOrders;
     }

     @Override
     public void insertNewOrder(Order order)
     {
          new InsertOrderAsync(orderDao).execute(order);
     }

     @Override
     public LiveData<Order> getOrder(long orderId)
     {
          return mapOrder(orderDao.getOrder(orderId));
     }

     private static class InsertOrderAsync extends AsyncTask<Order, Void, Void>
     {
          private OrderDao orderDao;

          private InsertOrderAsync(OrderDao orderDao)
          {
               this.orderDao = orderDao;
          }

          @Override
          protected Void doInBackground(Order... orders)
          {
               orderDao.insertOrder(orders[0]);
               return null;
          }
     }
}
