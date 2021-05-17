package me.antonjanto.mobilebutler.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "orderItem", primaryKeys = {"orderId", "productId"})
public class OrderItem implements Serializable
{
     @Embedded
     private Product product;
     private double quantity;
     private long orderId;
     private long productId;

     public OrderItem()
     {
     }

     @Ignore
     public OrderItem(long orderId, Product product, double quantity)
     {
          this.orderId = orderId;
          this.quantity = quantity;
          setProduct(product);
     }

     public long getProductId()
     {
          return productId;
     }

     public void setProductId(long productId)
     {
          this.productId = productId;
     }

     public long getOrderId()
     {
          return orderId;
     }

     public void setOrderId(long orderId)
     {
          this.orderId = orderId;
     }

     public double getQuantity()
     {
          return quantity;
     }

     public void setQuantity(double quantity)
     {
          this.quantity = quantity;
     }

     public Product getProduct()
     {
          product.setProductId(productId);
          return product;
     }

     public void setProduct(Product product)
     {
          this.product = product;
          if (productId == 0)
               setProductId(product.getProductId());
     }

     public double getPrice()
     {
          return product.getUnitPrice() * quantity;
     }

     public double getUnitPrice()
     {
          return product.getUnitPrice();
     }

     public String getProductName()
     {
          return product.getName();
     }

     public Map<String, Object> toMap()
     {
          HashMap<String, Object> result = new HashMap<>();
          result.put("productId", productId);
          result.put("quantity", quantity);
          return result;
     }
}
