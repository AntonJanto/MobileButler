package me.antonjanto.mobilebutler.model;


import androidx.room.Ignore;

import java.io.Serializable;

public class Product implements Serializable
{
     @Ignore
     private long productId;
     private String name;
     private double unitPrice;
     private String unit;
     private String category;
     private String subcategory;

     public Product()
     {
     }

     public long getProductId()
     {
          return productId;
     }

     public void setProductId(long productId)
     {
          this.productId = productId;
     }

     public String getName()
     {
          return name;
     }

     public void setName(String name)
     {
          this.name = name;
     }

     public double getUnitPrice()
     {
          return unitPrice;
     }

     public void setUnitPrice(double unitPrice)
     {
          this.unitPrice = unitPrice;
     }

     public String getUnit()
     {
          return unit;
     }

     public void setUnit(String unit)
     {
          this.unit = unit;
     }

     public String getCategory()
     {
          return category;
     }

     public void setCategory(String category)
     {
          this.category = category;
     }

     public String getSubcategory()
     {
          return subcategory;
     }

     public void setSubcategory(String subcategory)
     {
          this.subcategory = subcategory;
     }
}
