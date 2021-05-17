package me.antonjanto.mobilebutler.ui.products;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.Product;
import me.antonjanto.mobilebutler.repository.ProductRepository;
import me.antonjanto.mobilebutler.repository.ProductRepositoryImpl;
import me.antonjanto.mobilebutler.services.OrderService;
import me.antonjanto.mobilebutler.services.OrderServiceImpl;

public class ProductsViewModel extends AndroidViewModel
{
     private MutableLiveData<Order> order;
     private LiveData<List<Product>> products;
     private ProductRepository productRepository;
     private OrderService orderService;

     public ProductsViewModel(Application application)
     {
          super(application);
          productRepository = ProductRepositoryImpl.getInstance();
          orderService = new OrderServiceImpl();
     }

     public void init(Order order)
     {
          products = productRepository.getAllProducts();
          this.order = new MutableLiveData<>(order);
     }

     public LiveData<List<Product>> getProducts()
     {
          return products;
     }

     public LiveData<Order> getOrder()
     {
          return order;
     }

     public void addProductToOrder(long productId, double quantity)
     {
          Product product = products.getValue()
               .stream()
               .filter(p-> p.getProductId() == productId)
               .findFirst()
               .orElse(null);
          orderService.addProductToOrder(order.getValue(), product, quantity);
     }
}