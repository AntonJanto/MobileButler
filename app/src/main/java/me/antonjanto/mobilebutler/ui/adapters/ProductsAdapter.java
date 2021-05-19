package me.antonjanto.mobilebutler.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Product;
import me.antonjanto.mobilebutler.ui.Converter;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
     implements Filterable
{
     private List<Product> products;
     private List<Product> filteredProducts;
     private ProductsFilter productsFilter;

     public ProductsAdapter()
     {
          this.productsFilter = new ProductsFilter();
          setHasStableIds(true);
     }

     public void setProducts(List<Product> products)
     {
          this.products = products;
          this.filteredProducts = products;
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
          Product product = filteredProducts.get(position);
          holder.productNameTextView.setText(product.getName());
          holder.productPriceTextView.setText(Converter.toDecimal(product.getUnitPrice()));
          holder.productIdTextView.setText(Converter.toInteger(product.getProductId()));
     }

     @Override
     public long getItemId(int position)
     {
          return filteredProducts.get(position).getProductId();
     }

     @Override
     public int getItemCount()
     {
          if (filteredProducts == null)
               return 0;
          return filteredProducts.size();
     }

     @Override
     public Filter getFilter()
     {
          return productsFilter;
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

     private class ProductsFilter extends Filter
     {
          @Override
          protected FilterResults performFiltering(CharSequence constraint)
          {
               String filterString = constraint.toString().toLowerCase();

               FilterResults results = new FilterResults();

               final List<Product> productList = products;

               final List<Product> filteredProductList = new ArrayList<>();

               for (int i = 0; i < productList.size(); i++) {
                    Product product = productList.get(i);
                    try {
                         long queryAsId = Long.parseLong(filterString);
                         if (product.getProductId() == queryAsId
                              || product.getName().toLowerCase().contains(filterString)
                              || product.getCategory().toLowerCase().contains(filterString)
                              || product.getSubcategory().toLowerCase().contains(filterString)) {
                              filteredProductList.add(product);
                         }
                    }
                    catch (NumberFormatException e) {
                         if (product.getName().toLowerCase().contains(filterString)
                              || product.getCategory().toLowerCase().contains(filterString)
                              || product.getSubcategory().toLowerCase().contains(filterString)) {
                              filteredProductList.add(product);
                         }
                    }

               }

               results.values = filteredProductList;
               results.count = filteredProductList.size();

               return results;
          }

          @Override
          protected void publishResults(CharSequence constraint, FilterResults results)
          {
               filteredProducts = (List<Product>) results.values;
               notifyDataSetChanged();
          }
     }
}
