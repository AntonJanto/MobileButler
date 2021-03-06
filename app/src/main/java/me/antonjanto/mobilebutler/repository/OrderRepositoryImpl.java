package me.antonjanto.mobilebutler.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.model.OrderWithOrderItemsEntity;
import me.antonjanto.mobilebutler.repository.sqlite.MobileButlerDatabase;
import me.antonjanto.mobilebutler.repository.sqlite.OrderDao;
import me.antonjanto.mobilebutler.ui.Converter;

public class OrderRepositoryImpl implements OrderRepository
{
     private static OrderRepository instance;

     private final OrderDao orderDao;
     private DatabaseReference ordersReference;

     private final LiveData<List<Order>> openedOrders;

     private OrderRepositoryImpl(Application application)
     {
          orderDao = MobileButlerDatabase.getInstance(application.getApplicationContext())
               .orderDao();
          ordersReference = FirebaseDatabase.getInstance().getReference().child("orders");
          openedOrders = mapOrders(orderDao.getOpenedOrders());
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
          return Transformations.map(openedOrders, orders -> orders.stream().map(order -> {
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

     @Override
     public void updateOrder(Order order)
     {
          new InsertOrderItemsAsync(orderDao).execute(order.getItems());
          new UpdateOrderAsync(orderDao).execute(order);
     }

     @Override
     public void closeOrder(Order order)
     {
          new UpdateOrderAsync(orderDao).execute(order);
          saveClosedOrderInFirebase(order);
     }

     @Override
     public void deleteOrder(long orderId)
     {
          new DeleterOrderAsync(orderDao).execute(orderId);
     }

     private void saveClosedOrderInFirebase(Order order)
     {
          String orderId = Converter.toInteger(order.getOrderId());
          ordersReference.child(orderId).setValue(order.toMap());
          for (OrderItem item : order.getItems()) {
               String productId = Converter.toInteger(item.getProductId());
               ordersReference.child(orderId).child("items").child(productId).setValue(item.toMap());
          }
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

     private static class UpdateOrderAsync extends AsyncTask<Order, Void, Void>
     {
          private OrderDao orderDao;

          private UpdateOrderAsync(OrderDao orderDao)
          {
               this.orderDao = orderDao;
          }

          @Override
          protected Void doInBackground(Order... orders)
          {
               orderDao.updateOrder(orders[0]);
               return null;
          }
     }

     private static class UpdateOrderItemsAsync extends AsyncTask<List<OrderItem>, Void, Void>
     {
          private OrderDao orderDao;

          private UpdateOrderItemsAsync(OrderDao orderDao)
          {
               this.orderDao = orderDao;
          }

          @Override
          protected Void doInBackground(List<OrderItem>... lists)
          {
               orderDao.updateOrderItems(lists[0]);
               return null;
          }
     }

     private static class InsertOrderItemsAsync extends AsyncTask<List<OrderItem>, Void, Void>
     {
          private OrderDao orderDao;

          private InsertOrderItemsAsync(OrderDao orderDao)
          {
               this.orderDao = orderDao;
          }

          @Override
          protected Void doInBackground(List<OrderItem>... lists)
          {
               orderDao.insertOrderItems(lists[0]);
               return null;
          }
     }

     private static class DeleterOrderAsync extends AsyncTask<Long, Void, Void>
     {
          private OrderDao orderDao;

          public DeleterOrderAsync(OrderDao orderDao)
          {
               this.orderDao = orderDao;
          }

          @Override
          protected Void doInBackground(Long... longs)
          {
               orderDao.deleteOrder(longs[0]);
               orderDao.deleteOrderItems(longs[0]);
               return null;
          }
     }
}
