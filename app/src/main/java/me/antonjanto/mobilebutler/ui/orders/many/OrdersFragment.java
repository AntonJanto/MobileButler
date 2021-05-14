package me.antonjanto.mobilebutler.ui.orders.many;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.ui.adapters.OrderAdapter;
import me.antonjanto.mobilebutler.ui.adapters.RecyclerTouchListener;

public class OrdersFragment extends Fragment
{
     private OrdersViewModel ordersViewModel;
     private RecyclerView recyclerViewOrders;
     private FloatingActionButton fab;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     {
          ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
          View root = inflater.inflate(R.layout.fragment_orders, container, false);

          fab = root.findViewById(R.id.fab);
          fab.setOnClickListener((view) -> fabClicked(view));
          recyclerViewOrders = root.findViewById(R.id.recyclerView_orders);

          setupRecyclerView(root);
          return root;
     }

     private void setupRecyclerView(View root)
     {
          recyclerViewOrders.setLayoutManager(new LinearLayoutManager(root.getContext()));

          OrderAdapter orderAdapter = new OrderAdapter();
          recyclerViewOrders.setAdapter(orderAdapter);
          orderAdapter.setOrders(ordersViewModel.getOrders().getValue());
          ordersViewModel.getOrders().observe(getViewLifecycleOwner(), new Observer<List<Order>>()
          {
               @Override
               public void onChanged(List<Order> orders)
               {
                    orderAdapter.setOrders(orders);
                    orderAdapter.notifyDataSetChanged();
               }
          });

          recyclerViewOrders.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
               recyclerViewOrders, new RecyclerTouchListener.ClickListener()
          {
               @Override
               public void onClick(View view, int position)
               {
                    long orderId = ((OrderAdapter)recyclerViewOrders.getAdapter()).getOrderId(position);
                    navigateToSingleOrder(orderId);
               }

               @Override
               public void onLongClick(View view, int position)
               {

               }
          }));
     }

     private void navigateToSingleOrder(long orderId)
     {
          Bundle bundle = new Bundle();
          bundle.putLong("orderId", orderId);
          bundle.putString("title", String.valueOf(orderId));
          NavHostFragment.findNavController(this).navigate(R.id.action_nav_orders_to_nav_single_order, bundle);
     }

     private void fabClicked(View view)
     {
          ordersViewModel.createOrder();
     }
}