package me.antonjanto.mobilebutler.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.OrderItem;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>
{
     private List<OrderItem> orderItems;

     public void setOrderItems(List<OrderItem> orderItems)
     {
          this.orderItems = orderItems;
     }

     @Override
     @NonNull
     public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
     {
          LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
          View view = layoutInflater.inflate(R.layout.list_item_single_order_item, parent, false);
          return new ViewHolder(view);
     }

     @Override
     public void onBindViewHolder(OrderItemAdapter.ViewHolder holder, int position)
     {
          OrderItem orderItem = orderItems.get(position);
          holder.orderItemNameTextView.setText(orderItem.getProductName());
          String orderQuantity = stringValueBasedOnDecimal(orderItem.getQuantity());
          holder.orderItemQuantityView.setText(orderQuantity);
          holder.orderItemPriceView.setText(String.valueOf(orderItem.getPrice()));
     }

     private String stringValueBasedOnDecimal(double quantity)
     {
          return Math.floor(quantity) == quantity ? String.valueOf(((int)quantity)) : String.valueOf(quantity);
     }

     @Override
     public int getItemCount()
     {
          if (orderItems == null)
               return 0;
          return orderItems.size();
     }

     protected class ViewHolder extends RecyclerView.ViewHolder
     {
          TextView orderItemNameTextView;
          TextView orderItemQuantityView;
          TextView orderItemPriceView;

          ViewHolder(View itemView)
          {
               super(itemView);
               orderItemNameTextView = itemView.findViewById(R.id.single_order_item_name);
               orderItemQuantityView = itemView.findViewById(R.id.single_order_item_quantity);
               orderItemPriceView = itemView.findViewById(R.id.single_order_item_price);
          }
     }
}
