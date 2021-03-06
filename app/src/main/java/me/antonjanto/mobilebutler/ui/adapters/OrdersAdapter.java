package me.antonjanto.mobilebutler.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.ui.Converter;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>
{
     private List<Order> orders;

     public void setOrders(List<Order> orders)
     {
          this.orders = orders;
     }

     public long getOrderId(int position)
     {
          if (orders != null)
               if (orders.get(position) != null)
                    return orders.get(position).getOrderId();
          return 0;
     }

     @NonNull
     @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
     {
          LayoutInflater inflater = LayoutInflater.from(parent.getContext());
          View view = inflater.inflate(R.layout.list_item_order, parent, false);
          return new ViewHolder(view);
     }

     @Override
     public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position)
     {
          Order order = orders.get(position);
          holder.orderId.setText(Converter.toInteger(order.getOrderId()));
          holder.orderPrice.setText(Converter.toDecimal(order.getTotalPrice()));
     }

     @Override
     public int getItemCount()
     {
          if (orders == null)
               return 0;
          return orders.size();
     }

     protected class ViewHolder extends RecyclerView.ViewHolder
     {
          TextView orderId;
          TextView orderPrice;

          ViewHolder(View itemView)
          {
               super(itemView);
               orderId = itemView.findViewById(R.id.order_id);
               orderPrice = itemView.findViewById(R.id.order_price);
          }
     }
}
