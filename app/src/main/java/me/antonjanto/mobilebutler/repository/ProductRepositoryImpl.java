package me.antonjanto.mobilebutler.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.antonjanto.mobilebutler.model.Product;

public class ProductRepositoryImpl implements ProductRepository
{
     private static ProductRepositoryImpl instance;

     private DatabaseReference productsReference;
     private final MutableLiveData<List<Product>> products;

     public static synchronized ProductRepository getInstance()
     {
          if (instance == null)
               instance = new ProductRepositoryImpl();
          return instance;
     }

     private ProductRepositoryImpl()
     {
          productsReference = FirebaseDatabase.getInstance().getReference().child("products");
          productsReference.addValueEventListener(new ProductsValueEventListener());
          products = new MutableLiveData<>(new ArrayList<>());
//          productsReference.get().addOnCompleteListener(task -> {
//               if (task.isSuccessful())
//               {
//                    if (task.getResult().getValue() != null)
//                         products.postValue(task.getResult().getValue());
//                    else
//                         products.postValue(new ArrayList<>());
//               }
//          });
     }

     @Override
     public LiveData<List<Product>> getAllProducts()
     {
          return products;
     }

     private class ProductsValueEventListener implements ValueEventListener
     {

          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
          {
               List<Product> productList = new ArrayList<>();
               for (DataSnapshot child : snapshot.getChildren())
               {
                    Product product = child.getValue(Product.class);
                    productList.add(product);
               }
               products.postValue(productList);
          }

          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error)
          {

          }
     }

     private class ProductsEventListener implements ChildEventListener
     {
          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot,
               @Nullable String previousChildName)
          {
               Product product = snapshot.getValue(Product.class);
               if (snapshot.getKey() != null) {
                    product.setProductId(Long.getLong(snapshot.getKey()));
               }
               products.getValue().add(product);
          }

          @Override
          public void onChildChanged(@NonNull DataSnapshot snapshot,
               @Nullable String previousChildName)
          {
               Product product = snapshot.getValue(Product.class);
               if (snapshot.getKey() != null) {
                    long productKey = Long.getLong(snapshot.getKey());
                    product.setProductId(productKey);
                    products.getValue().stream()
                         .filter(p -> p.getProductId() == productKey)
                         .findFirst()
                         .map(p -> p = product);
               }
          }

          @Override
          public void onChildRemoved(@NonNull DataSnapshot snapshot)
          {
               if (snapshot.getKey() != null) {
                    long productKey = Long.getLong(snapshot.getKey());
                    products.getValue().remove(
                         products.getValue().stream()
                              .filter(p -> p.getProductId() == productKey)
                              .findFirst()
                              .orElse(null));
               }
          }

          @Override
          public void onChildMoved(@NonNull DataSnapshot snapshot,
               @Nullable String previousChildName)
          {

          }

          @Override
          public void onCancelled(@NonNull DatabaseError error)
          {
               Log.w("Products", "products cancelled", error.toException());
          }
     }
}
