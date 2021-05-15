package me.antonjanto.mobilebutler.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
{
     private List<Product> products;

     public void setProducts(List<Product> products)
     {
          this.products = products;
     }

     @NonNull
     @NotNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
     {
          LayoutInflater inflater = LayoutInflater.from(parent.getContext());
          View view = inflater.inflate(R.layout.list_item_product, parent, false);
          return new ViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position)
     {
          Product product = products.get(position);
          holder.productNameTextView.setText(product.getName());
          holder.productPriceTextView.setText(String.valueOf(product.getUnitPrice()));
          holder.productIdTextView.setText(String.valueOf(product.getProductId()));
     }

     @Override
     public int getItemCount()
     {
          if (products == null)
               return 0;
          return products.size();
     }

     protected class ViewHolder extends RecyclerView.ViewHolder
     {
          TextView productNameTextView;
          TextView productPriceTextView;
          TextView productIdTextView;

          public ViewHolder(View itemView)
          {
               super(itemView);
               productIdTextView = itemView.findViewById(R.id.product_id);
               productNameTextView = itemView.findViewById(R.id.product_name);
               productPriceTextView = itemView.findViewById(R.id.product_price);
          }
     }
}
